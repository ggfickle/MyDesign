package hfuu.examination.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import hfuu.examination.domain.Hfuu_Mediation;
import hfuu.examination.domain.upload.MediationExcel;
import hfuu.examination.service.Hfuu_ClassRoomService;
import hfuu.examination.service.Hfuu_CourseService;
import hfuu.examination.service.Hfuu_MediationService;
import hfuu.examination.service.Hfuu_TeacherService;
/**
 * 
 * @ClassName: MediationListener 
 * @Description: 调停课监听处理类
 * @author llm
 * @date 2020-03-25 05:07:41
 */
public class MediationListener extends AnalysisEventListener<MediationExcel>{
	private static final Logger LOGGER = LoggerFactory.getLogger(MediationListener.class);
	/**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private Hfuu_TeacherService hfuu_teacherService;
    private Hfuu_CourseService hfuu_courseService;
    private Hfuu_MediationService hfuu_mediationService;
    private Hfuu_ClassRoomService hfuu_classRoomService;
    /**
     * 存放调停课信息
     */
    List<Hfuu_Mediation> list = new ArrayList<Hfuu_Mediation>();
    /**
     * 存放课程信息
     */
    List<Map<String, Object>> map=new ArrayList<Map<String,Object>>();
    
    public MediationListener(Hfuu_TeacherService hfuu_teacherService,Hfuu_CourseService hfuu_courseService,Hfuu_MediationService hfuu_mediationService,Hfuu_ClassRoomService hfuu_classRoomService) {
     
		this.hfuu_teacherService = hfuu_teacherService;
		this.hfuu_classRoomService = hfuu_classRoomService;
		this.hfuu_courseService = hfuu_courseService;
		this.hfuu_mediationService =hfuu_mediationService;
	}
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		
		if(list.size()>0) {
			 saveMediation();
	         list.clear();
	         map.clear();
		 }
		 int index=hfuu_classRoomService.updMediationToClassRoom();
		 while(index!=0) {
			 index=hfuu_classRoomService.updMediationToClassRoom();
		 }
		 LOGGER.info("调停课信息插入教室成功");
		 LOGGER.info("调停课数据解析完成！");
	}
	/**
	 * 解析
	 */
	@Override
	public void invoke(MediationExcel data, AnalysisContext context) {
		 LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
			for(int i=0;i<data.getTeacherName().split("/").length;i++) {
		    Hfuu_Mediation med=new Hfuu_Mediation();
		    switch(data.getMedType()) {
		    	case "调课":med.setType(0);break;
		    	case "停课":med.setType(1);break;
		    	case "补课":med.setType(2);break;
		    	default:med.setType(3);break;
		    }
		    med.setPastDate(DateConvert(data.getPastDate().trim()));
		    med.setNewDate(data.getNewDate()==null?"":DateConvert(data.getNewDate().trim()));
		    if(data.getNewTeacher()!=null&&data.getTeacherName().trim().equals(data.getNewTeacher().trim())) {
		    		med.setTid(hfuu_teacherService.selTid(data.getTeacherName().split("/")[i], data.getClassName()));	
		    		med.setNtid(med.getTid());
		    }else if(data.getNewTeacher()!=null&&!data.getTeacherName().split("/")[i].trim().equals(data.getNewTeacher().trim())) {
		    	
		    }else {
		    	med.setTid(hfuu_teacherService.selTid(data.getTeacherName().split("/")[i], data.getClassName()));	
		    	med.setNtid(med.getTid());
		    }
		    med.setPastPlace(data.getPastPlace());
		    med.setNewPlace(data.getNewPlace()==null?"":data.getNewPlace());
		    med.setNote(data.getNote());
		    map=hfuu_courseService.selByTidAndSname(med.getTid(), data.getClassName());
		    if(!map.isEmpty()) {
		    	for(Map<String,Object> m:map) {
		    		String date_addr=(String) m.get("date_addr");
		    		if(date_addr.contains(";")) {
		    			String[] date=date_addr.split(";");
		    			for(String strs:date) {
		    				String[] str=strs.split(":");
				    		if(str[3].equals(med.getPastPlace())
				    				&&str[2].equals(data.getPastDate().substring(3, 6))
				    				  &&str[1].contains(DayConvert(data.getPastDate().substring(1,2)))
				    				    &&str[0].contains(med.getPastDate().substring(0,med.getPastDate().indexOf(":")))) {
				    			med.setCid((int) m.get("id"));
				    			
				    			if(hfuu_mediationService.selIsExist(med)) {
				    				
				    				list.add(med);
				    			}	
				    		}
		    			}
		    		}
		    		
		    	}
		    }
		 }
		    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
	        if (list.size() >= BATCH_COUNT) {
	            // 存储完成清理 list
	        	saveMediation();
	        	list.clear();
	            map.clear();
	        }
		
	}
	/**
	 * 
	 * @Title: saveMediation
	 * @Description: 调停课存储
	 * @author llm
	 * @date 2020-03-25 05:01:54
	 */
	private void saveMediation() {
		int index=hfuu_mediationService.insMediationInfoBatch(list);
		
		if (index > 0) {
			LOGGER.info("部分调停课信息插入成功");
		} else {
			LOGGER.info("部分调停课信息插入失败");
		}
		
	}
	/**
	 * 
	 * @Title: DateConvert
	 * @Description: Excel时间字符串格式转换成数据库可存储时间
	 * @param date
	 * @return
	 * @author llm
	 * @date 2020-03-25 05:00:25
	 */
	public String DateConvert(String date) {
		String day=DayConvert(date.substring(1,2));
		String time=date.substring(3, date.indexOf("节"));
		String weekstart=date.substring(date.indexOf("{")+2, date.indexOf("-"));
		String weekend=date.substring(date.indexOf("-")+1, date.lastIndexOf("周"));
		StringBuffer week=new StringBuffer();
		if(!weekstart.equals(weekend)) {
			for(int i=Integer.valueOf(weekstart);i<Integer.valueOf(weekend);i++) {
				week.append(i+",");
			}
			week.deleteCharAt(week.lastIndexOf(","));
		}else {
			week.append(weekstart);
		}
		return week.toString()+":"+day+":"+time;
	}
	/**
	 * 
	 * @Title: DayConvert
	 * @Description: 星期转换
	 * @param day
	 * @return
	 * @author llm
	 * @date 2020-03-25 05:01:23
	 */
	public String DayConvert(String day) {
		switch (day) {
		case "一":
			return "1";
		case "二":
			return "2";
		case "三":
			return "3";
		case "四":
			return "4";
		case "五":
			return "5";
		case "六":
			return "6";
		case "日":
			return "7";
		default:
			return day;
		}
	}
}
