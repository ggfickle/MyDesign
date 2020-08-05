package hfuu.examination.service;

import hfuu.examination.domain.upload.Hfuu_Teacher;

public interface WxLoginService {
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
	Hfuu_Teacher selTeacherByNameAndPwd(String username,String pwd);
}
