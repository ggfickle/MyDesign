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
import hfuu.examination.domain.ExportExcel;
import hfuu.examination.domain.PublicCourses;
import hfuu.examination.domain.User;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.Hfuu_ClassRoomService;
import hfuu.examination.service.Hfuu_CourseService;
import hfuu.examination.service.Hfuu_RebuildService;
import hfuu.examination.service.Hfuu_TeacherService;
import hfuu.examination.service.ProfessionalCoursesService;
import hfuu.examination.service.PublicCoursesService;
import hfuu.examination.utils.UUIDUtils;
/**
 * 
 * @ClassName: PublicCoursesControllor 
 * @Description: 公共课模块
 * @author author
 * @date 2020-04-11 01:34:10
 */
@Controller
public class PublicCoursesControllor {
	@Resource
	private PublicCoursesService publicService;
	@Resource
	private Hfuu_CourseService courseService;
	@Resource
	private Hfuu_RebuildService rebuildService;
	@Resource
	private Hfuu_TeacherService teacherService;
	@Resource
	private Hfuu_ClassRoomService roomService;
	@Resource
	private ProfessionalCoursesService professionalService;
	private static final Logger log = LoggerFactory.getLogger(PublicCoursesControllor .class);
	@ResponseBody
	@RequestMapping("/publicList")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "公共课查询")
	public String publicCourseList(@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult wt = new WebResult();
		try
		{
			User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
			System.out.println(user);
			List<PublicCourses> data=publicService.selPublicCoursesPage(user.getCid(),pageNum, pageSize);
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(publicService.selPublicCourseCount());
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
	@RequestMapping("/getPublicCourses")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求公共课数据")
	public String getPublicCourses(@RequestParam(value="id",defaultValue="")String id) {
		WebResult wt = new WebResult();
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		
		try
		{
			List<PublicCourses> data=publicService.selPublicCoursesById(id);
			wt.setCode("0");
			if(!publicService.selTeacherCidByCid(id, user.getCid())) {
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
	@RequestMapping("/getFreeTime")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求空闲时间数据")
	public String getFreeTime(@RequestParam("sid")String sid,@RequestParam("place")String place,@RequestParam("week")String week,@RequestParam("whichDay") String whichDay) {
		
		WebResult wt = new WebResult();
		try
		{
			wt.setCode("0");
			wt.setMsg("");
			wt.setData(publicService.selFreeTime(sid, place, week, whichDay));
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
	 * @Title: getRebuidNumber
	 * @Description: 获取不能参加考试的学生并安排教师监考
	 * @param tid
	 * @param sid
	 * @param place
	 * @param week
	 * @param whichDay
	 * @param time
	 * @param type
	 * @return
	 * @author author
	 * @date 2020-05-27 02:59:28
	 */
	@ResponseBody
	@RequestMapping("/getRebuidNumber")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "请求主副监考信息及重修学生信息")
	@Transactional(propagation = Propagation.REQUIRED)
	public String getRebuidNumber(@RequestParam("tid")String tid,@RequestParam("sid")String sid,@RequestParam("place")String place,@RequestParam("week")String week,@RequestParam("whichDay") String whichDay,@RequestParam("time") String time,@RequestParam("type")String type) {
		WebResult wt = new WebResult();
		try
		{
			wt.setCode("0");
			wt.setCount(publicService.selCountRebuild(sid, place, week, whichDay, time));
			User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
			wt.setMsg("");
			if (type.equals("1")) {
				wt.setData(publicService.selTeacherByTime(tid, place, sid, week, whichDay, time,String.valueOf(user.getCid())));
			}else {
				wt.setData(publicService.selOneTeacher(tid, place, sid, week, whichDay, time,String.valueOf(user.getCid())));
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
	@ResponseBody
	@RequestMapping("/getCoursesNumber")
	public String getCoursesNumberData() {
		WebResult wt = new WebResult();
		try
		{
			wt.setCode("0");
			wt.setCount(courseService.selCourseNumberByCollege().size());
			
			wt.setMsg("");
			wt.setData(courseService.selCourseNumberByCollege());
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
	@RequestMapping("/getPublicCoursesNumber")
	public String getPublicCoursesNumber() {
		WebResult wt = new WebResult();
		try
		{   int all=publicService.selCountPublicCourses();
		    int ok=publicService.selCountOkPublicCourse();
			wt.setCode("0");
			Map<String,String> count1=new HashMap<String, String>();
			count1.put("name", "已安排公共课监考");
			count1.put("value",String.valueOf(ok));
			Map<String,String> count2=new HashMap<String, String>();
			count2.put("name", "未安排公共课监考");
			count2.put("value",String.valueOf(all-ok));
			all=professionalService.selCountProfessionalCourses();
			ok=professionalService.selCountOkProfessionalCourse();
			Map<String,String> count3=new HashMap<String, String>();
			count3.put("name", "已安排专业课监考");
			count3.put("value",String.valueOf(ok));
			Map<String,String> count4=new HashMap<String, String>();
			count4.put("name", "未安排专业课监考");
			count4.put("value",String.valueOf(all-ok));
			
			List<Map<String, String>> data=new ArrayList<Map<String,String>>();
			data.add(count1);
			data.add(count2);
			data.add(count3);
			data.add(count4);
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
	/**
	 * 
	 * @Title: getCoursesInfoNumber
	 * @Description: 获取教师主副监考次数
	 * @return
	 * @author author
	 * @date 2020-05-27 03:14:25
	 */
	@ResponseBody
	@RequestMapping("/getCoursesInfoNumber")
	public String getCoursesInfoNumber() {
		WebResult wt = new WebResult();
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		try
		{  
			List<Map<String, Integer>> data=teacherService.selFrequency(user.getUserName());
			List<Map<String, String>> d=new ArrayList<Map<String,String>>();
			for(int i=0;i<data.size();i++) {
				for(String key : data.get(i).keySet()) {
				Map<String,String> count=new HashMap<String, String>();
				count.put("name",key );
				count.put("value",""+data.get(i).get(key));
				d.add(count);
				}
			}
			wt.setMsg("");
			wt.setData(d);
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
	 * @Title: arrangeInvigilation
	 * @Description: 安排公共课监考
	 * @param courses
	 * @return
	 * @author author
	 * @date 2020-05-15 12:38:32
	 */
	@ResponseBody
	@RequestMapping("/arrangeInvigilation")
	public String arrangeInvigilation(@RequestBody PublicCourses courses) {
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
						publicService.updPublicCourseInfo(ids[i], numbers[i] + "+" + numbers[numbers.length - 1],
								courses.getPlace(), courses.getTime(), courses.getPid(), courses.getFapid(),
								courses.getSapid());
					} else {
						publicService.updPublicCourseInfo(ids[i], numbers[i], courses.getPlace(), courses.getTime(),
								courses.getPid(), courses.getFapid(), courses.getSapid());
					}
				} else {
					publicService.updPublicCourseInfo(ids[i], numbers[i], courses.getPlace(), courses.getTime(),
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
	/**
	 * 
	 * @Title: publicCourseReset
	 * @Description: 重置公共课
	 * @param ids
	 * @return
	 * @author author
	 * @date 2020-05-15 06:21:04
	 */
	@ResponseBody
	@RequestMapping("/publicCourseReset")
	public String publicCourseReset(@RequestParam("id") String ids) {
		WebResult wt = new WebResult();
		try {
			publicService.updPublicCoursesReset(ids);
			wt.setCode("0");
		} catch (Exception e) {
			log.error("重置异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	
	/**
	 * 
	 * @Title: courseList
	 * @Description: 获取所有监考课程信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author author
	 * @date 2020-05-27 02:25:48
	 */
	@ResponseBody
	@RequestMapping("/coursesList")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "公共课查询")
	public String courseList() {
		WebResult wt = new WebResult();
		try
		{
			User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
			List<ExportExcel> data=publicService.selCoursesInfo(user.getUserName());
			System.out.println(data);
			wt.setCode("0");
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
}
