package hfuu.examination.dao;


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import hfuu.examination.domain.User;

public interface AdminDao {
	/**
	 * 
	 * @Title: selAdminByNameAndPwd
	 * @Description: 登陆用户数据
	 * @param username
	 * @param password
	 * @return
	 * @author author
	 * @date 2020-05-02 06:42:04
	 */
	@Select("select * from (select a.id,cid,rid,username,password,rolename from admin  a left join role on rid=role.id) m where m.username=#{name} and m.password=#{pwd}")
	@Results(id="admin" ,value= {
			@Result(property="accountNo",column="id",id=true),
			@Result(property="userName",column="username"),
			@Result(property="passWord",column="password"),
			@Result(property="roleName",column="rolename"),
			@Result(property="cid",column="cid"),
			@Result(property="rid",column="rid")
			})
	User selAdminByNameAndPwd(@Param("name")String username,@Param("pwd")String password);

	@Select("select * from (select a.id,cid,rid,username,password,rolename from admin  a left join role on rid=role.id) m where m.username=#{name} ")
	@ResultMap("admin")
	User selAdminByName(@Param("name")String username);
	
	@Select("select * from (select t.id,rid,name,pwd,rolename from hfuu_teacher t left join role r on rid=r.id) m where m.id=#{id} and m.password=#{pwd}")
	@Results(id="teacher" ,value= {
			@Result(property="userName",column="id",id=true),
			@Result(property="accountNo",column="name"),
			@Result(property="passWord",column="pwd"),
			@Result(property="roleName",column="rolename"),
			@Result(property="cid",column="cid"),
			@Result(property="rid",column="rid")
			})
	User selTeacherByNameAndPwd(@Param("id")String username,@Param("pwd")String password);

	@Select("select * from (select t.id,rid,name,pwd,rolename from hfuu_teacher t left join role r on rid=r.id) m where m.id=#{id} ")
	@ResultMap("teacher")
	User selATeacherByName(@Param("id")String username);
	
	
	List<User> selAllAdmin(@Param("college")String college);
	
	Integer selCountAllAdmin(@Param("college")String college);
	/**
	 * 
	 * @Title: insertExAdmin
	 * @Description: TODO(描述)
	 * @param user
	 * @return
	 * @author author
	 * @date 2020-05-11 07:24:01
	 */
	@Insert("insert into admin values(default,#{userName},#{passWord},#{email},#{smtp},#{cid},#{rid})")
	Integer insertExAdmin(User user);
	/**
	 * 
	 * @Title: delAdminInfo
	 * @Description: 批量删除
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-05-11 07:44:38
	 */
	Integer delAdminInfo(List<Integer> data);
	/**
     * 
     * @Title: updAdminPwdById
     * @Description: 重置密码
     * @param id
     * @param pwd
     * @return
     * @author author
     * @date 2020-05-11 02:12:29
     */
    @Update("update admin set password=#{pwd} where id=#{id}")
    Integer updAdminPwdById(@Param("id")String id,@Param("pwd")String pwd);
    /**
     * 
     * @Title: updAdminEmailById
     * @Description: 更新邮箱
     * @param id
     * @param email
     * @return
     * @author author
     * @date 2020-05-11 08:15:06
     */

    Integer updAdminEmailById(@Param("id")String id,@Param("email")String email,@Param("smtp")String smtp);
    
    User selectTeacherInfoById(@Param("id")String id);
    
    User selectAdminInfoById(@Param("id")String id);
    
    @Update("update admin set email=#{email} ,smtp=#{smtp}, password=#{passWord} , cid=#{cid} where id=#{accountNo}")
    Integer adminUpdateById(User user);
    
    @Update("update admin set email=#{email} , password=#{passWord}  where id=#{userName}")
    Integer teacherUpdateById(User user);
    /**
     * 
     * @Title: updTeacherEmailById
     * @Description: 更新教师邮箱及smtp
     * @param id
     * @param email
     * @param smtp
     * @return
     * @author author
     * @date 2020-05-14 08:43:42
     */
    Integer updTeacherEmailById(@Param("id")String id,@Param("email")String email);
    
    /**
     * 查找管理员的邮箱和smtp授权码
    * @Title: selEmailAndStmpByName
    * @Description: 
    * @param @param user
    * @param @return    参数
    * @return User    返回类型
    * @throws
     */
    @Select("select * from admin where username=#{0}")
    User selEmailAndStmpByName(String userName);
}
