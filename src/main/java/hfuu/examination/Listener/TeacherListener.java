package hfuu.examination.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import hfuu.examination.domain.Hfuu_ClassRoom;
import hfuu.examination.domain.upload.Course;
import hfuu.examination.domain.upload.Hfuu_Class;
import hfuu.examination.domain.upload.Hfuu_Course;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.Hfuu_ClassRoomService;
import hfuu.examination.service.Hfuu_ClassService;
import hfuu.examination.service.Hfuu_CourseService;
import hfuu.examination.service.Hfuu_TeacherService;
import hfuu.examination.utils.CryptographyUtil;
import hfuu.examination.utils.ExcelResolverUtils;
/**
 * 
 * @ClassName: TeacherListener 
 * @Description: 教室课程表处理类
 * @author hoongfei
 * @date 2020-03-25 05:08:04
 */
public class TeacherListener extends AnalysisEventListener<Map<Integer, String>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeacherListener.class);
	List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
	List<Course> clist = new ArrayList<>();
	List<Course> courseList = new ArrayList<>();
	Set<String> set = new HashSet<>();
	Map<String, StringBuilder> map = new HashMap<>();
	List<Hfuu_Teacher> teachetList = new ArrayList<>();
	List<Hfuu_Course> hfuuCourseList = new ArrayList<>();
	private Hfuu_ClassService hfuu_classService;
	private Hfuu_ClassRoomService hfuu_classRoomService;
	private Hfuu_TeacherService hfuu_teacherService;
	private Hfuu_CourseService hfuu_courseService;

	public TeacherListener(Hfuu_ClassService hfuu_classService, Hfuu_ClassRoomService hfuu_ClassRoomService,
			Hfuu_TeacherService hfuu_teacherService, Hfuu_CourseService hfuu_courseService) {
		this.hfuu_classService = hfuu_classService;
		this.hfuu_classRoomService = hfuu_ClassRoomService;
		this.hfuu_teacherService = hfuu_teacherService;
		this.hfuu_courseService = hfuu_courseService;
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		List<Hfuu_Class> cllist = new ArrayList<>();
		List<Hfuu_ClassRoom> roomlist = new ArrayList<>();

		courseList = getCourseList();
		map = getCourseInfo();
		
		for (Map.Entry<String, StringBuilder> entry : map.entrySet()) {
			String classRoom = entry.getValue().toString();
			String className = entry.getKey().substring(entry.getKey().indexOf(",") + 1);
			String professName = null;
			if (className.contains("（")) {
				className = className.replace('（', '(');
			}
			if (className.contains("）")) {
				className = className.replace('）', ')');
			}
			if (className.contains("(")) {
				if(ExcelResolverUtils.isNumber(className.substring(3, 4))) {
					professName = className.substring(5, className.indexOf("("));
				}else {
					professName = className.substring(2, className.indexOf("("));
				}
				if(ExcelResolverUtils.isNumber(professName.substring(professName.length()-1, professName.length()))){
					professName =professName.substring(0, professName.length()-1);
				}
			} else {
				professName = className.substring(2);
			}
			for (String room : classRoom.split(";")) {
				Hfuu_ClassRoom cm = new Hfuu_ClassRoom();
				cm.setName(room.substring(room.lastIndexOf(":") + 1));
				if(cm.getName().contains("(")||cm.getName().contains("（")) {
					if(cm.getName().contains("（")) {
						cm.setName(cm.getName().replace("（", "("));
					}
					cm.setName(cm.getName().substring(0, cm.getName().indexOf("(")));
				}
				cm.setDate_addr(room.substring(0, room.lastIndexOf(":")));
				if (hfuu_classRoomService.selClassRoomByNameAndDate_Addr(cm.getName(), cm.getDate_addr())) {
					roomlist.add(cm);
				}
			}
			int did = hfuu_classService.selDid(professName.trim());
			if (did == 0) {
				List<Map<String, Object>> maps = hfuu_classService.selDIdByLikeName(professName.trim());
					for (Map<String, Object> map : maps) {
						String snames = map.get("sname").toString();
						if (snames.contains(",")) {
							for (String name : snames.split(",")) {
								if (name.equals(professName.trim())) {
									did = (int) map.get("id");
									break;
								}
							}
						} else {
							if (snames.equals(professName.trim())) {
								did = (int) map.get("id");
								break;
							}
						}
					}
				}
			
			Hfuu_Class hfuu_class = new Hfuu_Class();
			hfuu_class.setName(className);
			hfuu_class.setDid(did);
			cllist.add(hfuu_class);
		}
	
		int index = 0;
		if (roomlist.size() > 0) {
			index = hfuu_classRoomService.insertClassRoomBatch(roomlist);
			if (index > 0) {
				LOGGER.info("教室信息插入成功");
			} else {
				LOGGER.info("教室信息插入失败");
			}
			roomlist.clear();
		}
		if (cllist.size() > 0) {
			index = hfuu_classService.insClassInfoBatch(cllist);
			if (index > 0) {
				LOGGER.info("班级信息插入成功");
			} else {
				LOGGER.info("班级信息插入失败");
			}
			cllist.clear();
		}
		String tId = getTeacherId();// 教职工编号
		String teacherName = getTeacherName();
		int cid = hfuu_teacherService.selCid(getDeaprtName());// 教师所属专业的编号
		// 添加教师信息
		Hfuu_Teacher hfuu_teacher = new Hfuu_Teacher();
		hfuu_teacher.setId(tId);
		hfuu_teacher.setName(teacherName);
		hfuu_teacher.setCid(cid);
		hfuu_teacher.setPwd(CryptographyUtil.md5(tId, "hfuuExamination"));
		System.out.println(hfuu_teacher);
		teachetList.add(hfuu_teacher);
		for (Map.Entry<String, StringBuilder> entry : map.entrySet()) {
			String className = entry.getKey().substring(entry.getKey().indexOf(",") + 1);
			String courseName = entry.getKey().substring(0, entry.getKey().indexOf(","));
			
			if (className.contains("（")) {
				className = className.replace('（', '(');
			}
			if (className.contains("）")) {
				className = className.replace('）', ')');
			}
			int classId = hfuu_classService.selIdByName(className);// 班级id
			
			
			// 添加课程信息
			Hfuu_Course hfuu_course = new Hfuu_Course();
			hfuu_course.setTid(tId);
			hfuu_course.setCid(classId);
			hfuu_course.setSname(courseName);
			hfuu_course.setSattri(3);
			hfuu_course.setDate_addr(
					entry.getValue().toString().substring(0, entry.getValue().toString().lastIndexOf(";")));
			System.out.println(hfuu_course.getDate_addr());
			if (hfuu_courseService.selCourseByOther(hfuu_course)) {
				hfuuCourseList.add(hfuu_course);
			}
		}
		
		if (teachetList.size() > 0) {
			index = hfuu_teacherService.insTeacherInfo(teachetList);

			if (index > 0) {
				LOGGER.info("教师信息插入成功");
			} else {
				LOGGER.info("教师信息插入失败");
			}
		}
		String str=list.get(list.size()-1).get(0);
		if(str!=null&&str.contains("实践课程")) {
			getPracticalCourse(str);
		}
		System.out.println(hfuuCourseList);
		if (hfuuCourseList.size() > 0) {
			index = hfuu_courseService.insCourseInfo(hfuuCourseList);

			if (index > 0) {
				LOGGER.info("课程信息插入成功");
			} else {
				LOGGER.info("课程信息插入失败");
			}
		}
		listClear();
	}

	@Override
	public void invoke(Map<Integer, String> data, AnalysisContext context) {
		LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
		list.add(data);

	}

	// 获取教师编号
	public String getTeacherId() {
		return list.get(1).get(0).substring(6, 12);
	}

	// 获取教师名称
	public String getTeacherName() {
		String name = list.get(0).get(0);
		return name.substring(0, name.lastIndexOf("老师"));
	}

	// 获取教师所属系别名称
	public String getDeaprtName() {
		return list.get(1).get(0).substring(15);
	}
	/**
	 * 
	 * @Title: getPracticalCourse
	 * @Description: 获取实践课程
	 * @return
	 * @author author
	 * @date 2020-03-27 02:00:04
	 */
	public void getPracticalCourse(String courses) {
		courses=courses.substring(5);
	    for(String course:courses.split(";")) {
	    	String[] strs=course.split("◇");
		
	    	if(strs[1].contains("（")) {
	    		strs[1]=strs[1].replace("（", "(").replace("）", ")");
	    	}
	    	String str=strs[1].substring(strs[1].indexOf(')')+1,strs[1].lastIndexOf("/"));
	   
	    	if(str.contains(",")) {
	        
	    	for(String name:str.split(",")) {
	    		Hfuu_Course hc=new Hfuu_Course();
				hc.setSname(strs[0]);
				hc.setTid(getTeacherId());
				String date=strs[1].substring(strs[1].lastIndexOf("/")+1);
				String start=date.substring(0,date.indexOf("-"));
				String end=date.substring(date.indexOf("-")+1,date.indexOf("周"));
				String remark="";
				for(int i=Integer.valueOf(start);i<=Integer.valueOf(end);i++) {
				    if(remark.equals("")) {
				    	remark+=i;
				    }else {
				    	remark+=(","+i);
				    }
				 }
			    hc.setSattri(3);
				hc.setDate_addr(remark);
	    		hc.setCid(hfuu_classService.selIdByName(name));
	    		System.out.println(hc);
	    		if (hfuu_courseService.selCourseByOther(hc)) {
					hfuuCourseList.add(hc);
				}
	    	}
	    }
	    }
	}
	private List<Course> getCourseList() {
		// Map<Integer, String> week = getListMap().get(1);
		Map<Integer, Integer> week = new HashMap<>();
		for (int i = 2; i < 9; i++) {
			week.put(i, i - 1);
		}
		
		for (int i = 3; i < 8; i++) {
			Map<Integer, String> course1 = list.get(i);
			
			for (int j = 2; j < 10; j++) {
			
				if(course1.size()<2) {
					break;
				}
				String courseInfo1 = course1.get(j);
				if (courseInfo1 != null) {
					String[] strings = courseInfo1.split("\n");
					for (String strInfo : strings) {
						strInfo = strInfo.trim();
						String courseName = strInfo.substring(0, strInfo.indexOf("◇"));
						String courseTimes = strInfo.substring(strInfo.indexOf("◇") + 1).substring(0,
								strInfo.substring(strInfo.indexOf("◇") + 1).indexOf("◇"));
						String classNames = strInfo.substring(strInfo.lastIndexOf("◇") + 1);
						String addr = strInfo.substring(0, strInfo.lastIndexOf("◇"))
								.substring(strInfo.substring(0, strInfo.lastIndexOf("◇")).lastIndexOf("◇") + 1);
						String[] times = courseTimes.split("\\),");
						String[] names = classNames.split(",");
						for (String courseTime : times) {
							for (String className : names) {
								StringBuilder weekNumber = new StringBuilder();
								String weekStr = courseTime.substring(0, courseTime.indexOf("("));
								if (weekStr.contains("-")) {
									if(weekStr.contains("，")) {
										weekStr.replace("，", ",");
									}
									if(weekStr.contains(",")) {
										for(String str:weekStr.split(",")) {
											int beginWeek = Integer.parseInt(str.substring(0, weekStr.indexOf("-")));
											int endWeek = Integer.parseInt(str.substring(weekStr.indexOf("-") + 1));
											// System.out.println("beginWeek:" + beginWeek + ",endWeek:" + endWeek);
											for (int k = beginWeek; k <= endWeek; k++) {
												weekNumber.append(k + ",");
											}
											
										}
										weekNumber = weekNumber.deleteCharAt(weekNumber.length() - 1);
									}else {
									int beginWeek = Integer.parseInt(weekStr.substring(0, weekStr.indexOf("-")));
									int endWeek = Integer.parseInt(weekStr.substring(weekStr.indexOf("-") + 1));
									// System.out.println("beginWeek:" + beginWeek + ",endWeek:" + endWeek);
									for (int k = beginWeek; k <= endWeek; k++) {
										weekNumber.append(k + ",");
									}
									weekNumber = weekNumber.deleteCharAt(weekNumber.length() - 1);
									}
								} else {
									weekNumber = weekNumber.append(weekStr);
								}

								String jieci = courseTime.substring(courseTime.indexOf("(") + 1,
										courseTime.length() - 1);
								if (jieci.contains("节")) {
									jieci = jieci.substring(0, jieci.length() - 1);
								}
								Course course = new Course();
								course.setCourseName(courseName);
								course.setDateAddr(weekNumber + ":" + week.get(j) + ":" + jieci + ":" + addr);
								course.setClassName(className);
								System.out.println(course);
								clist.add(course);
							}
						}
					}
				}
			}
		}
		return clist;
	}

	/**
	 * 
	 * @Title: listClear
	 * @Description: 清空
	 * @author llm
	 * @date 2020-03-23 02:11:52
	 */
	public void listClear() {
		list.clear();
		clist.clear();
		courseList.clear();
		teachetList.clear();
		set.clear();
		map.clear();
	}

	// 获取课程信息
	public Map<String, StringBuilder> getCourseInfo() {

		for (Course course : courseList) {
			set.add(course.getCourseName() + "," + course.getClassName());
		}
		for (String s : set) {
			StringBuilder sb = new StringBuilder();
			for (Course course : courseList) {
				if (s.contains(course.getClassName()) && s.contains(course.getCourseName())) {
					sb.append(course.getDateAddr() + ";");
					map.put(s, sb);
				}
			}
		}
		return map;
	}

}
