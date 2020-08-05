package hfuu.examination.controllor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.annotation.LogTypes;
import hfuu.examination.annotation.SystemControllerLog;
import hfuu.examination.domain.ProfessionalCourses;
import hfuu.examination.domain.User;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.Hfuu_ClassRoomService;
import hfuu.examination.service.Hfuu_CourseService;
import hfuu.examination.service.Hfuu_RebuildService;
import hfuu.examination.service.Hfuu_TeacherService;
import hfuu.examination.service.ProfessionalCoursesService;
import hfuu.examination.utils.UUIDUtils;
/**
 * 
 * @ClassName: ProfessionalCoursesControllor 
 * @Description: 专业课模块
 * @author author
 * @date 2020-04-16
 */
@Controller
public class ProfessionalCoursesControllor {
	@Resource
	private ProfessionalCoursesService professionalService;
	@Resource
	private Hfuu_CourseService courseService;
	@Resource
	private Hfuu_RebuildService rebuildService;
	@Resource
	private Hfuu_TeacherService teacherService;
	@Resource
	private Hfuu_ClassRoomService roomService;
	private static final Logger log = LoggerFactory.getLogger(ProfessionalCoursesControllor .class);
	
	@ResponseBody
	@RequestMapping("/getProfessionalCourses")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求公共课数据")
	public String getProfessionalCourses(@RequestParam(value="id",defaultValue="")String id) {
		WebResult wt = new WebResult();
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		try
		{
			List<ProfessionalCourses> data=professionalService.selProfessionalCoursesById(id);
			wt.setCode("0");
			if(!professionalService.selTeacherCidByCid(id, user.getCid())) {
				wt.setCode("2");
			}
			wt.setMsg("");
			wt.setCount(data.size());
			wt.setData(data);
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	
	
	@ResponseBody
	@RequestMapping("/getProfessionalCoursesNumber")
	public String getProfessionalCoursesNumber() {
		WebResult wt = new WebResult();
		try
		{   int all=professionalService.selCountProfessionalCourses();
		    int ok=professionalService.selCountOkProfessionalCourse();
			wt.setCode("0");
			Map<String,String> count1=new HashMap<String, String>();
			count1.put("name", "已安排专业课监考");
			count1.put("value",String.valueOf(ok));
			Map<String,String> count2=new HashMap<String, String>();
			count2.put("name", "未安排专业课监考");
			count2.put("value",String.valueOf(all-ok));
			List<Map<String, String>> data=new ArrayList<Map<String,String>>();
			data.add(count1);
			data.add(count2);
			wt.setMsg("");
			wt.setData(data);
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		
	}
	
	@ResponseBody
	@RequestMapping("/professionalList")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "专业课查询")
	public String collegeList(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult wt = new WebResult();
		try
		{
			User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
			System.out.println(user);
			List<ProfessionalCourses> data=professionalService.selProfessionalCoursesPage(user.getCid(),pageNum, pageSize);
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(professionalService.selProfessionalCourseCount());
			wt.setData(data);
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	} 
	
	/**
	 * 
	 * @Title: professionalCourseReset
	 * @Description: 重置专业课
	 * @param ids
	 * @return
	 * @author author
	 * @date 2020-05-16
	 */
	@ResponseBody
	@RequestMapping("/professionalCourseReset")
	public String professionalCourseReset(@RequestParam("id") String ids) {
		WebResult wt = new WebResult();
		try {
			professionalService.updProfessionalCoursesReset(ids);
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	@ResponseBody
	@RequestMapping("/getProfessionalRebuidNumber")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求主副监考信息及重修学生信息")
	@Transactional(propagation = Propagation.REQUIRED)
	public String getProfessionalRebuidNumber(@RequestParam("tid")String tid,@RequestParam("sid")String sid,@RequestParam("place")String place,@RequestParam("week")String week,@RequestParam("whichDay") String whichDay,@RequestParam("time") String time,@RequestParam("type")String type) {
		WebResult wt = new WebResult();
		try
		{
			wt.setCode("0");
			wt.setCount(professionalService.selCountRebuild(sid, place, week, whichDay, time));//ok
			User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
			wt.setMsg("");
			if (type.equals("1")) {
				wt.setData(professionalService.selTeacherByTime(tid, place, sid, week, whichDay, time,String.valueOf(user.getCid())));
			}else {
				wt.setData(professionalService.selOneTeacher(tid, place, sid, week, whichDay, time,String.valueOf(user.getCid())));
			}
			
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		
	}
	/**
	 * 
	 * @Title: professionalCourseWeek
	 * @Description: 获取已安排专业课周次
	 * @param type
	 * @return
	 * @author author
	 * @date 2020-05-16 07:07:16
	 */
	@ResponseBody
	@RequestMapping("/professionalCourseWeek")
	public String professionalCourseWeek(@RequestParam("type") String type) {
		WebResult wt = new WebResult();
		try {
			if(type.equals("0")) {
				
			}else if(type.equals("1")){
				List<String> dataList=professionalService.selProfessionalCoursesWeek();
				wt.setData(dataList);
				wt.setCount(dataList.size());
			}
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@ResponseBody
	@RequestMapping("/professionalCourseName")
	public String professionalCourseName(@RequestParam("type") String type,@RequestParam("week")String week) {
		WebResult wt = new WebResult();
		List<String> data=null;
		try {
			if(type.equals("0")) {
				
			}else if(type.equals("1")){
				data=professionalService.selCourseName(week);
			}
			wt.setData(data);
			wt.setCount(data.size());
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@ResponseBody
	@RequestMapping("/getArrangementInfo")
	public String getInfo(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult wt = new WebResult();
		try {
			List<Hfuu_Teacher> data = teacherService.getArrangementInfoLimit(pageNum,pageSize);
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(teacherService.selTeacherCount());
			wt.setData(data);
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	/**
	 * 
	 * @Title: arrangeProfessionalInvigilation
	 * @Description: 安排专业课监考
	 * @param courses
	 * @return
	 * @author author
	 * @date 2020-05-16
	 */
	@ResponseBody
	@RequestMapping("/arrangeProfessionalInvigilation")
	public String arrangeProfessionalInvigilation(@RequestBody ProfessionalCourses courses) {
		WebResult wt = new WebResult();
		try {
			String[] ids = courses.getId().split(",");
			List<Hfuu_Teacher> teachers = new ArrayList<Hfuu_Teacher>();
			if (courses.getFapid().equals("") && courses.getFirstAssociateProctor() != null) {
				String tid = teacherService.selTeacherIdByName(courses.getFirstAssociateProctor().getName());
				if (!tid.equals(null) && !tid.equals("")) {
					courses.setFapid(tid);
				} else {
					courses.setFapid(UUIDUtils.getUuid());
					Hfuu_Teacher teacher = new Hfuu_Teacher();
					teacher.setId(courses.getFapid());
					teacher.setName(courses.getFirstAssociateProctor().getName());
					teacher.setPwd(courses.getFapid());
					teachers.add(teacher);

				}
			}
			if (courses.getFapid().equals("") && courses.getFirstAssociateProctor() != null) {
				String tid = teacherService.selTeacherIdByName(courses.getSecondAssociateProctor().getName());
				if (!tid.equals(null) && !tid.equals("")) {
					courses.setSapid(tid);
				} else {
					courses.setSapid(UUIDUtils.getUuid());
					Hfuu_Teacher teacher = new Hfuu_Teacher();
					teacher.setId(courses.getSapid());
					teacher.setName(courses.getSecondAssociateProctor().getName());
					teacher.setPwd(courses.getSapid());
					teachers.add(teacher);
				}
			}
			if (!teachers.isEmpty()) {
				teacherService.insTeacherInfo(teachers);
			}
			String[] numbers = courses.getNumber().split("\\+");
			// 更新排考信息
			for (int i = 0; i < ids.length; i++) {
				if (i == 0) {
					if (numbers.length > ids.length) {
						professionalService.updProfessionalCourseInfo(ids[i], numbers[i] + "+" + numbers[numbers.length - 1],
								courses.getPlace(), courses.getTime(), courses.getPid(), courses.getFapid(),
								courses.getSapid());
					} else {
						professionalService.updProfessionalCourseInfo(ids[i], numbers[i], courses.getPlace(), courses.getTime(),
								courses.getPid(), courses.getFapid(), courses.getSapid());
					}
				} else {
					professionalService.updProfessionalCourseInfo(ids[i], numbers[i], courses.getPlace(), courses.getTime(),
							courses.getPid(), courses.getFapid(), courses.getSapid());
				}
			}
			// 更新主副监考次数
			teacherService.updAddChiefById(courses.getPid());
			teacherService.updAddDeputyById(courses.getFapid());
			if (!courses.getSapid().equals("")) {
				teacherService.updAddDeputyById(courses.getSapid());
			}
			// 更新教室信息
			roomService.updRoomDateAndAddrInfo(courses.getPlace(), courses.getTime());
			wt.setCode("0");
		} catch (Exception e) {
			log.error("更新异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
}
