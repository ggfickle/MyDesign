package hfuu.examination.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.upload.Hfuu_Teacher;

import java.util.List;
import java.util.Map;
@Component
public interface Hfuu_TeacherDao {

    /**
     * 查找老师属于哪个学院
     * @param name
     * @return
     */
    @Select("select cid from old_dept where name=#{0}")
    int selCid(String name);

    /**
     * 插入教职工信息
     * @param list
     * @return
     */
    int insTeacherInfo(@Param("list")List<Hfuu_Teacher> list);

    /**
     * 查看要添加的信息是否在教职工信息表中存在
     * @param hfuu_teacher
     * @return
     */
    @Select("select * from hfuu_teacher where id=#{id} and name=#{name} and cid=#{cid}")
    List<Hfuu_Teacher> selIsTeacher(Hfuu_Teacher hfuu_teacher);
    /**
     * 
     * @Title: selTid
     * @Description: 通过姓名 课程筛选老师id
     * @param name
     * @param sname
     * @return
     * @author llm
     * 
     */
    @Select("select id from hfuu_teacher where name=#{name} and id in (select  distinct tid from hfuu_course where sname=#{sname})")
    String selTid(@Param("name")String name,@Param("sname")String sname);
    /**
     * 根据教师姓名查找教师编号
     * @param name
     * @return
     */
    @Select("select id from hfuu_teacher where name=#{0}")
    String selTeacherIdByName(String name);
    /**
     * 
     * @Title: selTeacherInfoById
     * @Description: 查询教师信息
     * @param id
     * @return
     * @author author
     * @date 2020-04-17 08:06:37
     */
    @Select("select * from hfuu_teacher where id =#{0}")
    Hfuu_Teacher selTeacherInfoById(String id);
    /**
     * 
     * @Title: selTeacherInfoByDeputy
     * @Description: TODO(描述)
     * @param id
     * @param count
     * @return
     * @author author
     * @date 2020-04-20 12:29:17
     */
    @Select("select * from hfuu_teacher where  cid=#{cid} and LENGTH(id)<16 and id in "
    		+ "(select id from hfuu_teacher where  id <> #{id}  and id <> #{pid} and deputy<="
    		+ "(select avg(deputy) from hfuu_teacher)) limit #{start},#{end}")
    List<Hfuu_Teacher> selTeacherInfoByDeputy(@Param("id")String id,@Param("pid")String pid,@Param("start") int start,@Param("end") int end,@Param("cid")String cid);
    /**
     * 
     * @Title: selTeacherInfoByChief
     * @Description: TODO(描述)
     * @param id
     * @param start
     * @param end
     * @return
     * @author author
     * @date 2020-04-20 12:52:09
     */
    @Select("select * from hfuu_teacher where  cid=#{cid} and LENGTH(id)<16 and id in "
    		+ "(select id from hfuu_teacher where  id <> #{id} and deputy<="
    		+ "(select avg(chief) from hfuu_teacher)) limit #{start},#{end}")
    Hfuu_Teacher selTeacherInfoByChief(@Param("id")String id,@Param("start") int start,@Param("end") int end,@Param("cid")String cid);
    /**
     * 
     * @Title:查询教师信息
     * @Description: TODO(描述)
     * @param major
     * @param college
     * @return
     * @author author
     * @date 2020-05-11 01:22:51
     */
    List<Hfuu_Teacher> selTeacherInfo(@Param("name")String name,@Param("college")String college);
    /**
     * 
     * @Title: selCountTeacherInfoByLimit
     * @Description: 条件查询条数
     * @param name
     * @param college
     * @return
     * @author author
     * @date 2020-05-11 02:10:35
     */
    Integer selCountTeacherInfoByLimit(@Param("name")String name,@Param("college")String college);
    /**
     * 
     * @Title: updTeacherPwdById
     * @Description: 重置密码
     * @param id
     * @param pwd
     * @return
     * @author author
     * @date 2020-05-11 02:12:29
     */
    @Update("update hfuu_teacher set pwd=#{pwd} where id=#{id}")
    Integer updTeacherPwdById(@Param("id")String id,@Param("pwd")String pwd);
    /**
     * 
     * @Title: updTeacherAddChiefById
     * @Description: 更新主监考次数
     * @param id
     * @return
     * @author author
     * @date 2020-05-15 02:55:18
     */
    @Update("update hfuu_teacher set chief=chief+1 where id=#{id}")
    Integer updTeacherAddChiefById(@Param("id")String id);
    /**
     * 
     * @Title: updTeacherAddDeputyById
     * @Description: 更新副监考次数
     * @param id
     * @return
     * @author author
     * @date 2020-05-15 02:55:35
     */
    @Update("update hfuu_teacher set deputy=deputy+1 where id=#{id}")
    Integer updTeacherAddDeputyById(@Param("id")String id);
    /**
     * 
     * @Title: updTeacherAddChiefById
     * @Description: 更新主监考次数
     * @param id
     * @return
     * @author author
     * @date 2020-05-15 02:55:18
     */
    @Update("update hfuu_teacher set chief=chief-1 where id=#{id}")
    /**
     * 
     * @Title: updTeacherDecChiefById
     * @Description: 减主监考次数
     * @param id
     * @return
     * @author author
     * @date 2020-05-15 06:05:54
     */
    Integer updTeacherDecChiefById(@Param("id")String id);
   /**
    * 
    * @Title: updTeacherDecDeputyById
    * @Description: 减主副监考次数
    * @param id
    * @return
    * @author author
    * @date 2020-05-15 06:05:51
    */
    @Update("update hfuu_teacher set deputy=deputy-1 where id=#{id}")
    Integer updTeacherDecDeputyById(@Param("id")String id);
    
    @Update("truncate table hfuu_teacher")
    Integer delTeacherInfo();
    
    /**
     * 获取教师的考务信息
    * @Title: getArrangementInfo
    * @Description: 
    * @param @return    参数
    * @return List<Hfuu_Teacher>    返回类型
    * @throws
     */
    List<Hfuu_Teacher> getArrangementInfo();
    /**
     * 根据教师号获得教师邮箱
    * @Title: getTeacherMailInfo
    * @Description: 
    * @param @param id
    * @param @return    参数
    * @return String    返回类型
    * @throws
     */
    @Select("select email from hfuu_teacher where id=#{0}")
    String getTeacherMailInfoById(String id);
    
    /**
     * 根据教师id更新教师的邮箱
    * @Title: updTeacherEmailById
    * @Description: 
    * @param @param teacher
    * @param @return    参数
    * @return Integer    返回类型
    * @throws
     */
    @Update("update hfuu_teacher set email=#{email} where id=#{id}")
    Integer updTeacherEmailById(Hfuu_Teacher teacher);
    
    /**
     * 查询教师条数
    * @Title: selTeacherCount
    * @Description: 
    * @param @return    参数
    * @return Integer    返回类型
    * @throws
     */
    @Select("select count(*) from hfuu_teacher")
    Integer selTeacherCount();
    
    @Select("select CEIL(avg(chief)) 平均主监考次数,CEIL(avg(deputy)) 平均副监考次数 from hfuu_teacher where LENGTH(id)<16")
    List<Map<String, Integer>> selFrequency();

    @Select("select chief 主监考次数,deputy 副监考次数 from hfuu_teacher where id=#{0}")
    List<Map<String, Integer>> selFrequencyByTeacher(String id);
}
