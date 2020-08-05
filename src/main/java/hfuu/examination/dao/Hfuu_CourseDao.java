package hfuu.examination.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.upload.Hfuu_Course;

import java.util.List;
import java.util.Map;
@Component
public interface Hfuu_CourseDao {

    /**
     * 插入课程信息
     * @param list
     * @return
     */
    int insCourseInfo(@Param("list")List<Hfuu_Course> list);
    /**
     * 
     * @Title: isCourse
     * @Description: TODO(描述)
     * @param hfuu_course
     * @return
     * @author hoongfei
     * @date 2020-03-24 11:02:10
     */
    @Select("select * from hfuu_course where tid=#{tid} and cid=#{cid} and sname=#{sname}")
    List<Hfuu_Course> isCourse(Hfuu_Course hfuu_course);
    /**
     * 
     * @Title: selCourseByOther
     * @Description: 判断课程date_addr是否存在
     * @param tid
     * @param cid
     * @param sname
     * @param date_addr
     * @return
     * @author llm
     * @date 2020-03-24 11:05:37
     */
    @Select("select count(*) from hfuu_course where tid=#{tid} and cid=#{cid} and sname=#{sname} and date_addr like concat('%',#{date_addr},'%')")
    int selCourseByOther(Hfuu_Course course);
    /**
     * 
     * @Title: selCourseListInfo
     * @Description: TODO(描述)
     * @return
     * @author llm
     * @date 2020-03-24 07:17:06
     */
    List<Map<String,Object>> selCourseListInfo();
    /**
     *
     * @Title: selByTidAndSname
     * @Description: 根据tid和课程名查找cid和时间
     * @param tid
     * @param sname
     * @return
     * @author author
     * @date 2020-03-24 07:20:20
     */
    @Select("select id,date_addr from hfuu_course where tid=#{tid} and sname=#{sname}")
    List<Map<String, Object>> selByTidAndSname(@Param("tid")String tid,@Param("sname")String sname);
    /**
     * 通过课程编号和班级编号查询该课程的上课时间地点
     *
     * @param cid
     * @param sid
     * @return
     */
    @Select("select date_addr from hfuu_course where cid=#{arg0} and sid=#{arg1} and tid=#{arg2}")
    String selDateAddrByCidAndSid(int cid, String sid,String t_id);
    

    /**
     * 查询课程表中是否有“学院所上课程.xls”中对应的数据
     *
     * @param hfuu_course
     * @return
     */
    @Select("select * from hfuu_course where tid=#{tid} and sname=#{sname}")
    List<Hfuu_Course> selCourseCodeAndAttre(Hfuu_Course hfuu_course);

    /**
     * 更新插入课程代码和属性
     *
     * @param hfuu_course
     * @return
     */
    @Update("update hfuu_course set sid=#{sid},sattri=#{sattri} where tid=#{tid} and sname=#{sname}")
    int updateUsersBySname(Hfuu_Course data);
    /**
     * @Title: selPublicCourses
     * @Description: 获取所有公共课程id
     * @return
     * @author author
     * @date 2020-04-16 03:49:41
     */
    @Select("select id from hfuu_course where sattri=1")
    List<Integer> selPublicCourses();
    /**
     * 
    * @Title: selProfessionalCourses
    * @Description: 获取大数据人工智能学院所有专业课程id
    * @param @return    参数
    * @return List<Integer>    返回类型
    * @throws
     */
    @Select("select id from hfuu_course where sattri=0 and cid in(select id from hfuu_class where did in(select hdept.id from hcollege,hdept where hcollege.id=hdept.cid and hcollege.name='人工智能与大数据学院'))")
    List<Integer> selProfessionalCourses();

    /**
     * 
     * @Title: selCourseInfoById
     * @Description: 通过id查询课程信息
     * @param id
     * @return
     * @author author
     * @date 2020-04-17 08:14:00
     */
    Hfuu_Course selCourseInfoById(int id);
    /**
     * 
     * @Title: selCourseByCid
     * @Description: 查询班级所有课程
     * @param cid
     * @return
     * @author author
     * @date 2020-04-19 06:20:21
     */
    @Select("select * from hfuu_course where cid=#{0}")
    List<Hfuu_Course> selCourseByCid(int cid);
    /**
     * 
     * @Title: selCourseByTid
     * @Description: TODO(描述)
     * @param tid
     * @return
     * @author author
     * @date 2020-04-20 10:47:07
     */
    @Select("select * from hfuu_course where tid=#{0}")
    List<Hfuu_Course> selCourseByTid(String  tid);
    /**
     * 
     * @Title: selCoursesNumber
     * @Description: 返回各院系课程数
     * @return
     * @author author
     * @date 2020-05-12 08:03:07
     */
    @Select("select g.name,COUNT(n.id) value from (select m.id,d.cid from (select c.*,did from hfuu_course c join hfuu_class  t on c.cid=t.id) m  LEFT JOIN hdept d on m.did=d.id) n" 
    		+" LEFT JOIN hcollege g on  n.cid=g.id GROUP BY n.cid")
    List<Map<String, String>> selCoursesNumber();
    
    @Update("truncate table hfuu_course")
    Integer delCourseDao();
}
