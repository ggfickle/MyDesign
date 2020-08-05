package hfuu.examination.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.upload.Hfuu_Teacher;

@Component
public interface WxLoginDao {
	
	/**
	 * 微信小程序端登录
	* @Title: selTeacherByNameAndPwd
	* @Description: 
	* @param @param username
	* @param @param pwd
	* @param @return    参数
	* @return Integer    返回类型
	* @throws
	 */
	@Select("select * from hfuu_teacher where id=#{arg0} and pwd=#{arg1}")
	Hfuu_Teacher selTeacherByNameAndPwd(String username,String pwd);
}
