package hfuu.examination.service;

import java.text.ParseException;
import java.util.List;


import hfuu.examination.domain.ExportExcel;
import hfuu.examination.domain.PublicCourses;
import hfuu.examination.domain.upload.Hfuu_Teacher;


public interface PublicCoursesService {
	/**
	 * 
	 * @Title: insCourseIdBatch
	 * @Description: 为公共课表添加课程id
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-16 03:56:58
	 */
	Integer insCourseIdBatch();
	/**
	 * 
	 * @Title: selPublicCoursesPage
	 * @Description: 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author author
	 * @date 2020-04-17 08:32:52
	 */
	List<PublicCourses> selPublicCoursesPage(int cid,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCoursesInfo
	 * @Description: 查询教师监考科目
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-27 02:33:29
	 */
	List<ExportExcel> selCoursesInfo(String  id) throws ParseException;
	/**
	 * 
	 * @Title: selPublicCoursesById
	 * @Description: 批量id查询信息
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-18 05:36:41
	 */
	List<PublicCourses> selPublicCoursesById(String ids) ;
	/**
	 * 
	 * @Title: selPublicCourseCount
	 * @Description: TODO(描述)
	 * @return
	 * @author author
	 * @date 2020-04-19 02:24:38
	 */
	Integer selPublicCourseCount();
	/**
	 * 
	 * @Title: selFreeTime
	 * @Description: TODO(描述)
	 * @param sid
	 * @param place
	 * @param week
	 * @param whichDay
	 * @return
	 * @author author
	 * @date 2020-04-19 02:37:08
	 */
	List<String> selFreeTime(String sid,String place,String week,String whichDay);
	/**
	 * 
	 * @Title: selTeacherByTime
	 * @Description: 筛选监考老师
	 * @param week
	 * @param whichDay
	 * @param time
	 * @return
	 * @author author
	 * @date 2020-04-19 06:53:39
	 */
	List<Hfuu_Teacher> selTeacherByTime(String tid,String place,String sid,String week,String whichDay,String time,String cid);
	/**
	 * 
	 * @Title: selCountRebuild
	 * @Description: TODO(描述)
	 * @param sid
	 * @param place
	 * @param week
	 * @param whichDay
	 * @param time
	 * @return
	 * @author author
	 * @date 2020-04-20 10:24:30
	 */
	int selCountRebuild(String sid,String place,String week, String whichDay, String time); 
	/**
	 * 
	 * @Title: selCountPublicCourses
	 * @Description: 查询全部课程数量
	 * @return
	 * @author author
	 * @date 2020-05-13 04:05:18
	 */
	Integer selCountPublicCourses();
	/**
	 * 
	 * @Title: selCountOkPublicCourse
	 * @Description: 查询已安排科目数量
	 * @return
	 * @author author
	 * @date 2020-05-13 04:05:21
	 */
	Integer selCountOkPublicCourse();
	
	boolean selTeacherCidByCid(String id,Integer ucid);
	/**
	 * 
	 * @Title: selOneTeacher
	 * @Description: 查询主监考
	 * @param tid
	 * @param place
	 * @param sid
	 * @param week
	 * @param whichDay
	 * @param time
	 * @return
	 * @author author
	 * @date 2020-05-13 10:41:23
	 */
	List<Hfuu_Teacher> selOneTeacher(String tid, String place, String sid, String week, String whichDay, String time,String cid);
    /**
     * 
     * @Title: updPublicCourseInfo
     * @Description: 更新院系信息
     * @param id
     * @param number
     * @param place
     * @param time
     * @param pid
     * @param fapid
     * @param sapid
     * @return
     * @author author
     * @date 2020-05-15 02:30:42
     */
	Integer updPublicCourseInfo(String id,String number,String place,String time,String pid,String fapid,String sapid);
	/**
	 * 
	 * @Title: updPublicCoursesReset
	 * @Description: 重置已安排公共课监考科目
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-15 05:48:33
	 */
	Integer updPublicCoursesReset(String id);
	/**
	 * 
	 * @Title: selPublicCoursesWeek
	 * @Description: 查询已安排监考的周次
	 * @return
	 * @author author
	 * @date 2020-05-16 04:59:11
	 */
	List<String> selPublicCoursesWeek();
	/**
	 * 
	 * @Title: selCourseName
	 * @Description:根据安排周数筛选班级名称
	 * @param week
	 * @return
	 * @author author
	 * @date 2020-05-16 07:16:50
	 */
	List<String> selCourseName(String week);
	
	List<ExportExcel> selPublicCoursesByWeekAndSname(String week,String courseName) throws ParseException;
}
