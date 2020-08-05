package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.User;
import hfuu.examination.domain.upload.Hfuu_Teacher;

public interface UserService {
	/**
	 * 
	 * @Title: getAdminByUserNameAndPwd
	 * @Description:通过姓名密码获取管理员用户
	 * @param userName
	 * @param password
	 * @return
	 * @author author
	 * @date 2020-05-11 01:28:54
	 */
	User getAdminByUserNameAndPwd(String userName,String password);
	/**
	 * 
	 * @Title: getAdminByUserName
	 * @Description: 通过姓名获取管理员用户
	 * @param userName
	 * @return
	 * @author author
	 * @date 2020-05-11 01:29:00
	 */
	User getAdminByUserName(String userName);
	/**
	 * 
	 * @Title: getTeacherByUserNameAndPwd
	 * @Description: 通过姓名密码获取教师用户
	 * @param userName
	 * @param password
	 * @return
	 * @author author
	 * @date 2020-05-11 01:29:03
	 */
	User getTeacherByUserNameAndPwd(String userName,String password);
	/**
	 * 
	 * @Title: getTeacherByUserName
	 * @Description: 通过姓名获取教师用户
	 * @param userName
	 * @return
	 * @author author
	 * @date 2020-05-11 01:29:08
	 */
	User getTeacherByUserName(String userName);
	/**
	 * 
	 * @Title: selTeacherInfo
	 * @Description: 获取教师信息
	 * @param name
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-05-11 01:31:02
	 */
	List<Hfuu_Teacher> selTeacherInfo(String name,String college,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountTeacherInfoByLimit
	 * @Description: 分页总条数
	 * @param name
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-05-11 01:39:15
	 */
	Integer selCountTeacherInfoByLimit(String name,String college);
	/**
	 * 
	 * @Title: updTeacherPwd
	 * @Description:重置用户id
	 * @param id
	 * @param pwd
	 * @return
	 * @author author
	 * @date 2020-05-11 02:13:35
	 */
	boolean updTeacherPwd(String id,String pwd);
	/**
	 * 
	 * @Title: selAllAdmin
	 * @Description: 查询Admin信息
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-05-11 04:27:38
	 */
	List<User> selAllAdmin(String college,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountAllAdmin
	 * @Description: 查询Admin条数
	 * @param college
	 * @return
	 * @author author
	 * @date 2020-05-11 04:28:14
	 */
	Integer selCountAllAdmin(String college);
	/**
	 * 
	 * @Title: insAdminInfo
	 * @Description: 添加其他院系管理员
	 * @param user
	 * @return
	 * @author author
	 * @date 2020-05-11 07:29:06
	 */
	Integer insAdminInfo(User user);
	/**
	 * 
	 * @Title: delAdminInfo
	 * @Description: 批量删除管理员
	 * @param data
	 * @return
	 * @author author
	 * @date 2020-05-11 07:45:15
	 */
	boolean delAdminInfo(List<Integer> data);
	/**
	 * 
	 * @Title: updAdminPwd
	 * @Description:重置管理员密码
	 * @param id
	 * @param pwd
	 * @return
	 * @author author
	 * @date 2020-05-11 02:13:35
	 */
	boolean updAdminPwd(String id,String pwd);
	/**
	 * 
	 * @Title: updAdminEmail
	 * @Description: 更新邮箱
	 * @param id
	 * @param email
	 * @return
	 * @author author
	 * @date 2020-05-11 08:15:35
	 */
	boolean updAdminEmail(String id,String email,String smtp);
	/**
	 * 
	 * @Title: selAdminById
	 * @Description: 通过id获取所有信息
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-11 08:52:09
	 */
	User selAdminById(String id);
	/**
	 * 
	 * @Title: selTeacherById
	 * @Description: TODO(描述)
	 * @param id
	 * @return
	 * @author author
	 * @date 2020-05-27 01:58:41
	 */
	User selTeacherById(String id);
	/**
	 * 
	 * @Title: updAdminById
	 * @Description: 更新信息
	 * @param user
	 * @return
	 * @author author
	 * @date 2020-05-11 09:17:43
	 */
	boolean updAdminById(User user);
	/**
	 * 
	 * @Title: updteacherById
	 * @Description: 修改用户信息
	 * @param user
	 * @return
	 * @author author
	 * @date 2020-05-27 02:13:34
	 */
	boolean updteacherById(User user);
	/**
	 * 
	 * @Title: updTeacherEmail
	 * @Description:更新教师邮箱信息
	 * @param id
	 * @param email
	 * @param smtp
	 * @return
	 * @author author
	 * @date 2020-05-14 08:44:55
	 */
	boolean updTeacherEmail(String id,String email);
	/**
     * 查找管理员的邮箱和smtp授权码
    * @Title: selEmailAndStmpByName
    * @Description: 
    * @param @param user
    * @param @return    参数
    * @return User    返回类型
    * @throws
     */
	User selEmailAndStmpByName(String userName);
}
