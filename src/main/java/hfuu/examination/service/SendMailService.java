package hfuu.examination.service;

public interface SendMailService {
	
	/**
	 * 获取教师的邮箱
	* @Title: getTeacherMailInfo
	* @Description: 
	* @param @param id
	* @param @return    参数
	* @return String    返回类型
	* @throws
	 */
	String getTeacherMailInfoById(String id);
}
