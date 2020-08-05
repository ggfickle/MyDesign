package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.Arrangement;
import hfuu.examination.domain.ProfessionalCourses;
import hfuu.examination.domain.PublicCourses;

/**
 * 
 * @ClassName: ProfessionalCoursesDao 
 * @Description: 专业课数据层
 * @author author
 * @date 2020-04-16 03:50:41
 */
@Component
public interface ProfessionalCoursesDao {
	/**
	 * 
	 * @Title: insCourseIdBatch
	 * @Description: 插入专业课id
	 * @return
	 * @author author
	 * @date 2020-04-16 03:51:43
	 */
	Integer insCourseIdBatch(List<Integer> data);
	/**
	 * 
	 * @Title: selProfessionalCourseInfoLimit
	 * @Description: 分页查询专业课信息
	 * @return
	 * @author author
	 * @date 2020-04-17 07:55:06
	 */
	List<ProfessionalCourses> selProfessionalCourseInfoLimit(@Param("college")int cid);
	/**
	 * 
	 * @Title: selProfessionalCourseInfoByTeacher
	 * @Description: 查询专业课监考
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-27 02:32:34
	 */
	List<PublicCourses> selProfessionalCourseInfoByTeacher(@Param("id")String id);
	/**
	 * 
	 * @Title: selProfessionalCourseByWeekAndSname
	 * @Description: 查询专业课
	 * @param week
	 * @param sname
	 * @return
	 * @author author
	 * @date 2020-05-16 09:23:19
	 */
	List<ProfessionalCourses> selProfessionalCourseByWeekAndSname(@Param("week")String week,@Param("courseName")String courseName);
	/**
	 * 
	 * @Title: selProfessionalCoursesById
	 * @Description: 通过id查询专业课信息
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-04-18 05:29:33
	 */
	List<ProfessionalCourses> selProfessionalCoursesById(List<Integer> data);
	/**
	 * 
	 * @Title: selProfessionalCoursesInfoById
	 * @Description: 查询课程信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-15 03:37:44
	 */
	@Select("select * from professional_courses where id=#{0}")
	ProfessionalCourses selProfessionalCoursesInfoById(String id);
	/**
	 * 
	 * @Title: selProfessionalCoursesByPlaceAndTime
	 * @Description: 查询所有相同考试时间和地点的课程
	 * @param courses
	 * @return
	 * @author author
	 * @date 2020-05-15 03:41:02
	 */
	List<ProfessionalCourses> selProfessionalCoursesByPlaceAndTime(ProfessionalCourses courses);
	/**
	 * 
	 * @Title: selProfessionalCourseCount
	 * @Description: 查询数据条数
	 * @return
	 * @author author
	 * @date 2020-04-19 02:24:01
	 */
	Integer selProfessionalCourseCount();
	
	@Select("select count(*) from professional_courses where number!='' or number !=null")
	Integer selCountOkProfessionalCourse();
	/**
	 * 
	 * @Title: selTeacherCidByCourseId
	 * @Description: 查询专业课教师院系名
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-13 03:53:50
	 */
    @Select("select n.cid from (select tid from professional_courses p left join hfuu_course  c on p.cid=c.id where p.id=#{id}) m LEFT JOIN hfuu_teacher n on m.tid=n.id")
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
	@Select("select q.cid from (select did from (select c.cid from professional_courses p left join hfuu_course  c on p.cid=c.id where p.id=#{id}) m" 
			+" left join hfuu_class n ON m.cid=n.id) t left JOIN hdept q on t.did=q.id ")
    Integer selClassCidByCourseById(@Param("id")String id);
	/**
	 * 
	 * @Title: updProfessionalCourseById
	 * @Description: 更新专业课信息
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
	@Update("update professional_courses set number=#{number},place=#{place},time=#{time},pid=#{pid},fapid=#{fapid},sapid=#{sapid} where id=#{id}")
	Integer updProfessionalCourseById(@Param("id")String id,@Param("number")String number,@Param("place")String place,@Param("time")String time,@Param("pid")String pid,@Param("fapid")String fapid,@Param("sapid")String sapid);
	/**
	 * 
	 * @Title: updProfessionalCourseResetById
	 * @Description: 重置专业课课程信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-15 06:02:29
	 */
	@Update("update professional_courses set number=null,place=null,time=null,pid=null,fapid=null,sapid=null where id=#{id}")
	Integer updProfessionalCourseResetById(String id);
	@Select("select DISTINCT time from professional_courses where pid=#{0} or fapid=#{0}  or  sapid=#{0}")
	List<String> selTeacherTimeByTid(String tid);
	/**
	 * 
	 * @Title: selProfessionalCourseWeek
	 * @Description: 查询已安排监考的周次
	 * @return
	 * @author author
	 * @date 2020-05-16 04:58:02
	 */
	List<String> selProfessionalCourseWeek();
	/**
	 * 
	 * @Title: selProfessionalCourseName
	 * @Description:查询所有专业课名称
	 * @return
	 * @author author
	 * @date 2020-05-16 07:11:28
	 */
	List<String> selProfessionalCourseName();
	/**
	 * 
	 * @Title: selProfessionalCourseNameByWeek
	 * @Description: 查询所有专业课名通过排考周次
	 * @param week
	 * @return
	 * @author author
	 * @date 2020-05-16 07:19:50
	 */
	List<String> selProfessionalCourseNameByWeek(@Param("week")String week);
	
	 @Update("truncate table professional_courses")
	Integer delProfessionalCourse();
	 
	 /**
	 * 查找主监考信息
	* @Title: selMainArrangementInfo
	* @Description: 
	* @param @param id
	* @param @return    参数
	* @return List<Arrangement>    返回类型
	* @throws
	 */
	List<Arrangement> selMainArrangementInfoById(String id);
	
	/**
	 * 查找副监考信息
	* @Title: selSecondArrangemnetInfo
	* @Description: 
	* @param @param id
	* @param @return    参数
	* @return List<Arrangement>    返回类型
	* @throws
	 */
	List<Arrangement> selSecondArrangemnetInfoById(String id);
}
