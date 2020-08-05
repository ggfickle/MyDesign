package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.PublicCourses;

/**
 * 
 * @ClassName: PublicCoursesDao 
 * @Description: 公共课数据层
 * @author author
 * @date 2020-04-16 03:50:41
 */
@Component
public interface PublicCoursesDao {
	/**
	 * 
	 * @Title: insCourseIdBatch
	 * @Description: 插入公共课id
	 * @return
	 * @author author
	 * @date 2020-04-16 03:51:43
	 */
	Integer insCourseIdBatch(List<Integer> data);
	/**
	 * 
	 * @Title: selPublicCourseInfoLimit
	 * @Description: 分页查询公共课信息
	 * @return
	 * @author author
	 * @date 2020-04-17 07:55:06
	 */
	List<PublicCourses> selPublicCourseInfoLimit(@Param("college")int cid);
	
	
	
	List<PublicCourses> selPublicCourseInfoByTeacher(@Param("id")String id);
	/**
	 * 
	 * @Title: selPublicCourseByWeekAndSname
	 * @Description: 查询公共课
	 * @param week
	 * @param sname
	 * @return
	 * @author author
	 * @date 2020-05-16 09:23:19
	 */
	List<PublicCourses> selPublicCourseByWeekAndSname(@Param("week")String week,@Param("courseName")String courseName);
	/**
	 * 
	 * @Title: selPublicCoursesById
	 * @Description: 通过id查询公共课信息
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-18 05:29:33
	 */
	List<PublicCourses> selPublicCoursesById(List<Integer> data);
	
	/**
	 * 
	 * @Title: selPublicCoursesInfoById
	 * @Description: 查询课程信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-15 03:37:44
	 */
	@Select("select * from public_courses where id=#{0}")
	PublicCourses selPublicCoursesInfoById(String id);
	/**
	 * 
	 * @Title: selPublicCoursesByPlaceAndTime
	 * @Description: 查询所有相同考试时间和地点的课程
	 * @param courses
	 * @return
	 * @author author
	 * @date 2020-05-15 03:41:02
	 */
	List<PublicCourses> selPublicCoursesByPlaceAndTime(PublicCourses courses);
	/**
	 * 
	 * @Title: selPublicCourseCount
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-04-19 02:24:01
	 */
	Integer selPublicCourseCount();
	
	@Select("select count(*) from public_courses where number!='' or number !=null")
	Integer selCountOkPublicCourse();
	/**
	 * 
	 * @Title: selTeacherCidByCourseId
	 * @Description: 查询公共课教师院系名
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-13 03:53:50
	 */
    @Select("select n.cid from (select tid from public_courses p left join hfuu_course  c on p.cid=c.id where p.id=#{id}) m LEFT JOIN hfuu_teacher n on m.tid=n.id")
	Integer selTeacherCidByCourseId(@Param("id")String id);
    /**
     * 
     * @Title: selClassCidByCourseById
     * @Description:查询班级院系
     * @param id
     * @return
     * @author author
     * @date 2020-05-15 02:28:36
     */
	@Select("select q.cid from (select did from (select c.cid from public_courses p left join hfuu_course  c on p.cid=c.id where p.id=#{id}) m" 
			+" left join hfuu_class n ON m.cid=n.id) t left JOIN hdept q on t.did=q.id ")
    Integer selClassCidByCourseById(@Param("id")String id);
	/**
	 * 
	 * @Title: updPublicCourseById
	 * @Description: 更新公共课信息
	 * @param id
	 * @param number
	 * @param place
	 * @param time
	 * @param pid
	 * @param fapid
	 * @param sapid
	 * @return
	 * @author author
	 * @date 2020-05-15 02:28:58
	 */
	@Update("update public_courses set number=#{number},place=#{place},time=#{time},pid=#{pid},fapid=#{fapid},sapid=#{sapid} where id=#{id}")
	Integer updPublicCourseById(@Param("id")String id,@Param("number")String number,@Param("place")String place,@Param("time")String time,@Param("pid")String pid,@Param("fapid")String fapid,@Param("sapid")String sapid);
	/**
	 * 
	 * @Title: updPublicCourseResetById
	 * @Description: 重置公共课课程信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-15 06:02:29
	 */
	@Update("update public_courses set number=null,place=null,time=null,pid=null,fapid=null,sapid=null where id=#{id}")
	Integer updPublicCourseResetById(String id);
	@Select("select DISTINCT time from public_courses where pid=#{0} or fapid=#{0}      or  sapid=#{0}")
	List<String> selTeacherTimeByTid(String tid);
	/**
	 * 
	 * @Title: selPublicCourseWeek
	 * @Description: 查询已安排监考的周次
	 * @return
	 * @author author
	 * @date 2020-05-16 04:58:02
	 */
	List<String> selPublicCourseWeek();
	/**
	 * 
	 * @Title: selPublicCourseName
	 * @Description:查询所有公共课名称
	 * @return
	 * @author author
	 * @date 2020-05-16 07:11:28
	 */
	List<String> selPublicCourseName();
	/**
	 * 
	 * @Title: selPublicCourseNameByWeek
	 * @Description: 查询所有公共课名通过排考周次
	 * @param week
	 * @return
	 * @author author
	 * @date 2020-05-16 07:19:50
	 */
	List<String> selPublicCourseNameByWeek(@Param("week")String week);
	
	 @Update("truncate table public_courses")
	Integer delPublicCourse();
}
