package hfuu.examination.domain;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:11:16  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 通行id
	 */
	private String accountNo;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 邮箱授权协议
	 */
	private String smtp;
	/**
	 * 所属院系编号
	 */
	private int cid;
	/**
	 * 院系
	 */
	private HCollege college;
	/**
	 * 权限
	 */
	private int rid;
	/**
	 * 权限菜单
	 */
	private List<Menu> menus;
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getUserName() {
		return userName;
	}
	
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public HCollege getCollege() {
		return college;
	}

	public void setCollege(HCollege college) {
		this.college = college;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	@Override
	public String toString() {
		return "User [accountNo=" + accountNo + ", userName=" + userName + ", passWord=" + passWord + ", roleName="
				+ roleName + ", email=" + email + ", smtp=" + smtp + ", cid=" + cid + ", college=" + college + ", rid="
				+ rid + ", menus=" + menus + "]";
	}
    
	
    
}
