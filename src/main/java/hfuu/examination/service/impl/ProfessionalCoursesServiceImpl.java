package hfuu.examination.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.annotation.LogTypes;
import hfuu.examination.annotation.SystemServiceLog;
import hfuu.examination.dao.HCalendarDao;
import hfuu.examination.dao.Hfuu_ClassRoomDao;
import hfuu.examination.dao.Hfuu_CourseDao;
import hfuu.examination.dao.Hfuu_MediationDao;
import hfuu.examination.dao.Hfuu_RebuildDao;
import hfuu.examination.dao.Hfuu_TeacherDao;
import hfuu.examination.dao.ProfessionalCoursesDao;
import hfuu.examination.domain.Arrangement;
import hfuu.examination.domain.ExportExcel;
import hfuu.examination.domain.Hfuu_ClassRoom;
import hfuu.examination.domain.Hfuu_Mediation;
import hfuu.examination.domain.ProfessionalCourses;
import hfuu.examination.domain.upload.Hfuu_Course;
import hfuu.examination.domain.upload.Hfuu_Rebuild;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.ProfessionalCoursesService;
import hfuu.examination.utils.ExcelResolverUtils;

@Service
public class ProfessionalCoursesServiceImpl implements ProfessionalCoursesService {

	@Resource
	private Hfuu_CourseDao courseDao;
	@Resource
	private ProfessionalCoursesDao professionalDao;
	@Resource
	private Hfuu_ClassRoomDao roomDao;
	@Resource
	private Hfuu_RebuildDao rebuildDao;
	@Resource
	private Hfuu_TeacherDao teacherDao;
	@Resource
	private Hfuu_MediationDao mediationDao;
	@Resource
	private HCalendarDao calendarDao;
	@Override
	public Integer insCourseIdBatch() {
		return professionalDao.insCourseIdBatch(courseDao.selProfessionalCourses());
	}

	@Override
	public List<ProfessionalCourses> selProfessionalCoursesPage(int cid,Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ProfessionalCourses> data = professionalDao.selProfessionalCourseInfoLimit(cid);
		return data;
	}

	@Override
	public List<ProfessionalCourses> selProfessionalCoursesById(String ids) {
		List<ProfessionalCourses> data = new ArrayList<ProfessionalCourses>();
		List<Integer> idList = new ArrayList<Integer>();
		if (ids.equals("") || ids == null) {
			return data;
		}
		if (ids.contains(",")) {
			for (String id : ids.split(",")) {
				idList.add(Integer.valueOf(id));
			}
		} else {
			ProfessionalCourses courses=professionalDao.selProfessionalCoursesInfoById(ids);
			
			if(courses.getPlace()!=null&&!courses.getPlace().equals("")) {
				return professionalDao.selProfessionalCoursesByPlaceAndTime(courses);
			}
			idList.add(Integer.valueOf(ids));
		}
		return professionalDao.selProfessionalCoursesById(idList);
	}

	@Override
	public Integer selProfessionalCourseCount() {
		return professionalDao.selProfessionalCourseCount();
	}

	@Override
	@SystemServiceLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求主副监考信息及重修学生信息")
	public List<Hfuu_Teacher> selTeacherByTime(String tid, String place, String sid, String week, String whichDay,
			String time,String cid) {
		
		List<String> list = new ArrayList<String>();
		List<String> freelist = new ArrayList<String>();
		List<Hfuu_Teacher> teacher = new ArrayList<Hfuu_Teacher>();
		int count = 2;
		list.add("1,2");
		list.add("3,4");
		list.add("5,6");
		list.add("7,8");
		freelist.add("1,2");
		freelist.add("3,4");
		freelist.add("5,6");
		freelist.add("7,8");
		if (list.get(0).equals(time) || list.get(1).equals(time)) {
			list.remove(3);
			list.remove(2);
		} else {
			list.remove(0);
			list.remove(0);
		}
		
		List<Hfuu_Course> data = courseDao.selCourseByTid(tid.trim());
		List<Hfuu_Mediation> mediations=mediationDao.selMediationInfoByTid(tid);
		for (int i = 0; i < data.size(); i++) {
			String strs=data.get(i).getDate_addr();
			String strings="";
			for(Hfuu_Mediation mediation:mediations) {
				if(mediation!=null&&mediation.getCid()==data.get(i).getId()) {
					String pastDate=mediation.getPastDate();
					String newDate=mediation.getPastDate();
					strs=removeDate(strs, strings, pastDate);
					if(newDate!=null&&!newDate.equals("")) {
						strs=addDate(strs, strings, newDate);
					}
			}
			}
			freelist = ExcelResolverUtils.getRemainTime(freelist,strs, whichDay, week);
			//监考时间
			List<String> coursetime=professionalDao.selTeacherTimeByTid(tid);
			if(!coursetime.isEmpty()) {
				for(String str:coursetime) {
					freelist = ExcelResolverUtils.getRemainTime(freelist,str, whichDay, week);
				}
			}
		}
		
		int index=0;
		String pid="";
		if (!freelist.isEmpty() && freelist.contains(list.get(0)) && freelist.contains(list.get(1))) {
			teacher.add(teacherDao.selTeacherInfoById(tid));
		}else{
			while(teacher.size()<1) {
				
				Hfuu_Teacher teacher2=teacherDao.selTeacherInfoByChief(tid,index,1,cid);
				List<String> listTime = new ArrayList<String>();
				listTime.add("1,2");
				listTime.add("3,4");
				listTime.add("5,6");
				listTime.add("7,8");
				List<Hfuu_Course> datas = courseDao.selCourseByTid(teacher2.getId());
				List<Hfuu_Mediation> meds=mediationDao.selMediationInfoByTid(teacher2.getId());
				for (Hfuu_Course course:datas) {
					String strs=course.getDate_addr();
//					System.out.println(datas.size());
//					System.out.println(strs);
					String strings="";
					if(meds.size()>0) {
						for(Hfuu_Mediation med:meds) {
							if(med!=null&&med.getCid()==course.getId()) {
								String pastDate=med.getPastDate();
								String newDate=med.getPastDate();
								strs=removeDate(strs, strings, pastDate);
								if(newDate!=null&&!newDate.equals("")) {
									strs=addDate(strs, strings, newDate);
								}
							}
						}
					}
				
					listTime = ExcelResolverUtils.getRemainTime(listTime, strs, whichDay, week);
					//监考时间
					
					List<String> coursetime=professionalDao.selTeacherTimeByTid(teacher2.getId());
					if(!coursetime.isEmpty()) {
						for(String str:coursetime) {
							listTime = ExcelResolverUtils.getRemainTime(listTime,str, whichDay, week);
						}
					}
				}
				if (listTime.isEmpty()) {
					continue;
				}
				if (listTime.contains(list.get(0)) && listTime.contains(list.get(1))) {
					pid=teacher2.getId();
					teacher.add(teacherDao.selTeacherInfoById(pid));
				}else {
					datas.clear();
				}
				meds.clear();
				index++;
			}
		}
	
		if (place.endsWith("JT")) {
			count = 3;
		}
		index=0;
		while (teacher.size()<count) {
			List<Hfuu_Teacher> apTeacher = teacherDao.selTeacherInfoByDeputy(tid,pid,index,1,cid);
			for (int i = 0; i < apTeacher.size(); i++) {
				List<String> listTime = new ArrayList<String>();
				listTime.add("1,2");
				listTime.add("3,4");
				listTime.add("5,6");
				listTime.add("7,8");
				List<Hfuu_Mediation> meds=mediationDao.selMediationInfoByTid(apTeacher.get(i).getId());
				List<Hfuu_Course> datas = courseDao.selCourseByTid(apTeacher.get(i).getId());
				for (Hfuu_Course course:datas) {
					String strs=course.getDate_addr();
					String strings="";
					for(Hfuu_Mediation med:meds) {
						if(med!=null&&med.getCid()==course.getId()) {
							String pastDate=med.getPastDate();
							String newDate=med.getPastDate();
							strs=removeDate(strs, strings, pastDate);
							if(newDate!=null&&!newDate.equals("")) {
								strs=addDate(strs, strings, newDate);
							}
						}
					}
					listTime = ExcelResolverUtils.getRemainTime(listTime, course.getDate_addr(), whichDay, week);
					List<String> coursetime=professionalDao.selTeacherTimeByTid(apTeacher.get(i).getId());
					if(!coursetime.isEmpty()) {
						for(String str:coursetime) {
							listTime = ExcelResolverUtils.getRemainTime(listTime,str, whichDay, week);
						}
					}
				}
				if (listTime.isEmpty()) {
					continue;
				}
				
				if (listTime.contains(list.get(0)) && listTime.contains(list.get(1))) {
					teacher.add(teacherDao.selTeacherInfoById(apTeacher.get(i).getId()));
			
				}else {
					datas.clear();
				}
				meds.clear();
			}
			index++;
		}
		return teacher;
	}
	@Override
	public List<String> selFreeTime(String sid, String place, String week, String whichDay) {
		Hfuu_ClassRoom room = roomDao.selRoomInfoByName(place);
		String strs = room.getDate_addr();
		String strings="";
		List<String> list = new ArrayList<String>();
		list.add("1,2");
		list.add("3,4");
		list.add("5,6");
		list.add("7,8");
		if (!room.getMid().equals("-1")) {
			for(String mid:room.getMid().split(",")) {
				Hfuu_Mediation mediation=mediationDao.selMediationInfoById(mid);
				String pastDate=mediation.getPastDate();
				String newDate=mediation.getPastDate();
				if(mediation!=null&&mediation.getPastPlace().equals(place)) {
					strs=removeDate(strs, strings, pastDate);
				}
				if(mediation!=null&&mediation.getNewDate().equals(place)) {
					strs=addDate(strs, strings, newDate);
				}
			}
		}
		
		list = ExcelResolverUtils.getRemainTime(list, strs, whichDay, week);
		for(String id:sid.split(",")) {
			List<Hfuu_Course> data = courseDao.selCourseByCid(Integer.valueOf(id));
	
			for (int i = 0; i < data.size(); i++) {
				list = ExcelResolverUtils.getRemainTime(list, data.get(i).getDate_addr(), whichDay, week);
			}
		}
		return list;
	}
	@Override
	@SystemServiceLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求主监考信息")
	public List<Hfuu_Teacher> selOneTeacher(String tid, String place, String sid, String week, String whichDay,String time,String cid) {
		List<String> list = new ArrayList<String>();
		List<String> freelist = new ArrayList<String>();
		List<Hfuu_Teacher> teacher = new ArrayList<Hfuu_Teacher>();
		list.add("1,2");
		list.add("3,4");
		list.add("5,6");
		list.add("7,8");
		freelist.add("1,2");
		freelist.add("3,4");
		freelist.add("5,6");
		freelist.add("7,8");
		if (list.get(0).equals(time) || list.get(1).equals(time)) {
			list.remove(3);
			list.remove(2);
		} else {
			list.remove(0);
			list.remove(0);
		}
		
		List<Hfuu_Course> data = courseDao.selCourseByTid(tid.trim());
		List<Hfuu_Mediation> mediations=mediationDao.selMediationInfoByTid(tid);
		for (Hfuu_Course course:data) {
			String strs=course.getDate_addr();
			String strings="";
			for(Hfuu_Mediation mediation:mediations) {
				if(mediation!=null&&mediation.getCid()==course.getId()) {
					String pastDate=mediation.getPastDate();
					String newDate=mediation.getPastDate();
					strs=removeDate(strs, strings, pastDate);
					if(newDate!=null&&!newDate.equals("")) {
						strs=addDate(strs, strings, newDate);
					}
				}
			}
			freelist = ExcelResolverUtils.getRemainTime(freelist,strs, whichDay, week);
			List<String> coursetime=professionalDao.selTeacherTimeByTid(tid);
			if(!coursetime.isEmpty()) {
				for(String str:coursetime) {
					freelist = ExcelResolverUtils.getRemainTime(freelist,str, whichDay, week);
				}
			}
		}
		
		int index=0;
		String pid="";
		
		if (!freelist.isEmpty() && freelist.contains(list.get(0)) && freelist.contains(list.get(1))) {
			teacher.add(teacherDao.selTeacherInfoById(tid));
		}else{
			while(teacher.size()<1) {
				Hfuu_Teacher teacher2=teacherDao.selTeacherInfoByChief(tid,index,1,cid);
				List<String> listTime = new ArrayList<String>();
				listTime.add("1,2");
				listTime.add("3,4");
				listTime.add("5,6");
				listTime.add("7,8");
				List<Hfuu_Course> datas = courseDao.selCourseByTid(teacher2.getId());
				List<Hfuu_Mediation> meds=mediationDao.selMediationInfoByTid(teacher2.getId());
				for (int j = 0; j < datas.size(); j++) {
					String strs=data.get(j).getDate_addr();
					String strings="";
					for(Hfuu_Mediation med:meds) {
						if(med!=null&&med.getCid()==data.get(j).getId()) {
							String pastDate=med.getPastDate();
							String newDate=med.getPastDate();
							strs=removeDate(strs, strings, pastDate);
							if(newDate!=null&&!newDate.equals("")) {
								strs=addDate(strs, strings, newDate);
							}
						}
					}
					listTime = ExcelResolverUtils.getRemainTime(listTime, strs, whichDay, week);
					List<String> coursetime=professionalDao.selTeacherTimeByTid(teacher2.getId());
					if(!coursetime.isEmpty()) {
						for(String str:coursetime) {
							listTime = ExcelResolverUtils.getRemainTime(listTime,str, whichDay, week);
						}
					}
				}
				if (listTime.isEmpty()) {
					continue;
				}
				if (listTime.contains(list.get(0)) && listTime.contains(list.get(1))) {
					pid=teacher2.getId();
					teacher.add(teacherDao.selTeacherInfoById(pid));
					
					break;
				}
				index++;
			}
		}
		return teacher;
	}
	@Override
	public int selCountRebuild(String sid, String place, String week, String whichDay, String time) {
		List<Hfuu_Rebuild> data = rebuildDao.selRebuildInfoBySid(Arrays.asList(sid.split(",")));
		int count = data.size();//跟随该班级重修的所有学生
		for (int i = 0; i < data.size(); i++) {		
			if (data.get(i).getClassId() == 0) {//classid为零则该班级无课程信息
				List<Hfuu_Rebuild> other = rebuildDao.selRebuildInfoByClassNameAndSname(data.get(i).getClassName(),
						data.get(i).getSname());//查找该学生所有重修课程信息
				boolean flag = true;//定义变量  判断该学生是否空闲
				if (other.size() == 1) {
					//若只有一条重修信息，则该学生时刻表与跟随重修班级时刻表可以对应
				} else {
					for (int j = 0; j < other.size(); j++) {
						if (sid.contains(String.valueOf(other.get(j).getF_id()))) {
							continue;//判断跟随班级是否为为当前安排监考的班级
						}
						if (other.get(j).getR_dateAddr() == null) {
							continue;//判断该班级是否是开设重修班，且未填入时刻信息
						}
						String date_addr = other.get(j).getR_dateAddr();

						if (date_addr.equals("") || date_addr == null || date_addr.equals("null")) {
							continue;//判断该班级未填入时刻信息
						}
					
						for (String str : date_addr.split(";")) {
							String[] s = str.split(":");
							//判断时刻是否与空闲时刻重叠
							if (ExcelResolverUtils.isExistNumber(s[0], week)) {
								if (s[1].equals(whichDay) && time.contains(s[2])) {
									flag = false;
									j=other.size();//跳出外层循环
									break;
								}
							}
						}
					}
				}
				if (flag) {
					count--;
				}
				
			} else {

				List<String> list = new ArrayList<String>();
				list = selFreeTime(String.valueOf(data.get(i).getClassId()), place, week, whichDay);
				List<Hfuu_Rebuild> other = rebuildDao.selRebuildInfoByClassNameAndSname(data.get(i).getClassName(),
						data.get(i).getSname());
				boolean flag = true;
				for (int j = 0; j < other.size(); j++) {
					if (sid.contains(String.valueOf(other.get(j).getF_id()))) {
						continue;
					}
					if (other.get(j).getR_dateAddr() == null) {
						continue;
					}
					String date_addr = other.get(j).getR_dateAddr();

					for (String str : date_addr.split(";")) {
						String[] s = str.split(":");
						if (ExcelResolverUtils.isExistNumber(s[0], week)) {
							if (s[1].equals(whichDay) && time.contains(s[2])) {
								flag = false;
								j=other.size();//跳出外层循环
								break;
							}
						}
					}

				}
				if (flag && list.contains(time)) {
					count--;
				}
			}
		}
		return count;
	}

	@Override
	public Integer selCountProfessionalCourses() {
		return professionalDao.selProfessionalCourseCount();
	}

	@Override
	public Integer selCountOkProfessionalCourse() {
		
		return professionalDao.selCountOkProfessionalCourse();
	}
	
	private String removeDate(String strs,String strings,String pastDate) {
		String[] pastDateSpilt=pastDate.split(":");
		for(String str:strs.split(";")) {
			if(str.contains(":")) {
				String[] string=str.split(":");
				if(string[1].equals(pastDateSpilt[1])) {
					for(String s:pastDateSpilt[0].split(",")) {
						string[0].replace(s, "0");
					};
				}
				for(int j=0;j<string.length;j++) {
					strings+=string[j]+":";
				}
				strings=strings.substring(0,strings.length()-1);
				strings+=";";
			}
		}
		strings=strings.substring(0,strings.length()-1);
		return strings;
	}
    private String addDate(String strs,String strings,String newDate) {
    	boolean flag=true;
    	String[] newDateSpit=newDate.split(":");
		for(String str:strs.split(";")) {
			if(str.contains(":")) {
				String[] string=str.split(":");
				if(string[1].equals(newDateSpit[1])) {
					flag=false;
					for(String s:newDateSpit[0].split(",")) {
						string[0]+=(","+s);
					};
				}
				for(int j=0;j<string.length;j++) {
					strings+=string[j]+":";
				}
				strings=strings.substring(0,strings.length()-1);
				strings+=";";
			}
		}
		strings=strings.substring(0,strings.length()-1);
		if(flag) {
			strings+=newDate;
		}
		return strings;
	}

	@Override
	public boolean selTeacherCidByCid(String id, Integer ucid) {
		return professionalDao.selTeacherCidByCourseId(id)==ucid?true:false;			
	}

	@Override
	public Integer updProfessionalCourseInfo(String id, String number, String place, String time, String pid, String fapid,
			String sapid) {
		return professionalDao.updProfessionalCourseById(id, number, place, time, pid, fapid, sapid);
	}

	@Override
	public Integer updProfessionalCoursesReset(String id) {
	     List<String> list=Arrays.asList(id.split(","));
	     List<String> ids=new ArrayList<String>(list);
	     for(int i=0;i<ids.size();i++) {
	    	 ProfessionalCourses courses=professionalDao.selProfessionalCoursesInfoById(ids.get(i));
	    	
	    	 if(courses.getNumber()==null||courses.getNumber().equals("")) {
	    		 continue;
	    	 }
	    	 List<ProfessionalCourses> data=professionalDao.selProfessionalCoursesByPlaceAndTime(courses);
	    	 for(ProfessionalCourses course:data) {
	    		 professionalDao.updProfessionalCourseResetById(course.getId());
	    		 ids.remove(course.getId());
	    	 }
	    	 teacherDao.updTeacherDecChiefById(courses.getPid());
	    	 teacherDao.updTeacherDecDeputyById(courses.getFapid());
	    	 if(courses.getSapid()!=null&&!courses.getSapid().equals("")) {
	    		 teacherDao.updTeacherDecDeputyById(courses.getSapid());
	    	 }
	    	 Hfuu_ClassRoom room=roomDao.selRoomInfoByName(courses.getPlace());
	    	 roomDao.updAllRoomDateByName(courses.getPlace(),room.getDate_addr().replace(";"+courses.getTime(),""));
	     }
	     return 1;
	}

	@Override
	public List<String> selProfessionalCoursesWeek() {
		return professionalDao.selProfessionalCourseWeek();
	}

	@Override
	public List<String> selCourseName(String week) {
		if(week.equals("0")) {
			return professionalDao.selProfessionalCourseName();
		}
		return professionalDao.selProfessionalCourseNameByWeek(week);
	}

	@Override
	public List<ExportExcel> selProfessionalCoursesByWeekAndSname(String week, String courseName) throws ParseException {
		List<ProfessionalCourses> srcData=professionalDao.selProfessionalCourseByWeekAndSname(week, courseName);
		List<ExportExcel> distData=new ArrayList<ExportExcel>();
		System.out.println("srcData-----:"+srcData);
		for(int i=0;i<srcData.size();i++) {
			ProfessionalCourses pCourses=srcData.get(i);
			ExportExcel excel=new ExportExcel();
			for(int j=i+1;j<srcData.size();j++) {
				ProfessionalCourses sCourses=srcData.get(j);
				if(sCourses.getPlace().equals(pCourses.getPlace())&&sCourses.getTime().equals(pCourses.getTime())) {
					if(pCourses.getNumber().contains("+")) {
						String[] strings=pCourses.getNumber().split("\\+");
						pCourses.setNumber(strings[0]+"+"+sCourses.getNumber()+"+"+strings[1]);
					}else {
						pCourses.setNumber(pCourses.getNumber()+"+"+sCourses.getNumber());
					}
					pCourses.getCourse().getSubject().setName(pCourses.getCourse().getSubject().getName()+","+sCourses.getCourse().getSubject().getName());
					srcData.remove(sCourses);
				}
			}
			String date=calendarDao.selCalendarDate(pCourses.getTime().split(":")[0]);
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date2=sf.parse(date);
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.setTime(date2);
			calendar.add(Calendar.DATE,Integer.valueOf(pCourses.getTime().split(":")[1])-1);
			String distDate=sf.format(calendar.getTime());
			excel.setId(""+i);
			excel.setNumber(pCourses.getNumber());
			excel.setTeacher(pCourses.getCourse().getTeacher().getName());
			excel.setPlace(pCourses.getPlace());
			excel.setTime(distDate+"  第"+pCourses.getTime().split(":")[0]+"周周"+pCourses.getTime().split(":")[1]+" 第"+pCourses.getTime().split(":")[2]+"节");
			excel.setPname(pCourses.getProctor().getName());
			excel.setCollege(pCourses.getCourse().getSubject().getCollege().getName());
			excel.setClassName(pCourses.getCourse().getSubject().getName());
			excel.setSname(pCourses.getCourse().getSname());
			if(pCourses.getSapid()!=null&&!pCourses.getSapid().equals("")) {
				excel.setApname(pCourses.getFirstAssociateProctor().getName()+","+pCourses.getSecondAssociateProctor().getName());
			}
			else {
				excel.setApname(pCourses.getFirstAssociateProctor().getName());
			}
			distData.add(excel);
		}
		
		return distData;
	}


	@Override
	public List<Arrangement> selMainArrangementInfoById(String id) {
		return professionalDao.selMainArrangementInfoById(id);
	}

	@Override
	public List<Arrangement> selSecondArrangemnetInfoById(String id) {
		return professionalDao.selSecondArrangemnetInfoById(id);
	}
}