package hfuu.examination.domain.upload;

import java.io.Serializable;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import hfuu.examination.domain.Arrangement;
import hfuu.examination.domain.HCollege;

public class Hfuu_Teacher implements Serializable {
	

	@Override
	public String toString() {
		return "Hfuu_Teacher [id=" + id + ", name=" + name + ", pwd=" + pwd + ", email=" + email + ", cid=" + cid
				+ ", college=" + college + ", chief=" + chief + ", deputy=" + deputy + ", mainArrangement="
				+ mainArrangement + ", secondArrangement=" + secondArrangement + "]";
	}

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:09:56
	 */
	@ExcelIgnore
	private static final long serialVersionUID = 1L;

	/**
	 * 教职工编号
	 */
	@ExcelProperty(index = 0)
	private String id;

	/**
	 * 教职工姓名
	 */
	@ExcelProperty(index = 1)
	private String name;
	/**
	 * 教职工密码
	 */
	@ExcelIgnore
	private String pwd;
	/**
	 * 教师院系
	 */
	@ExcelProperty(index = 2)
	private String email;
	/**
	 * 教职工所属学院编号
	 */
	@ExcelIgnore
	private int cid;
	/**
	 * 院系信息
	 */
	@ExcelIgnore
	private HCollege college;

	/**
	 * 主监考场数
	 */
	@ExcelIgnore
	private int chief;

	/**
	 * 副监考场数
	 */
	@ExcelIgnore
	private int deputy;

	/**
	 * 考务信息1
	 */
	@ExcelIgnore
	private List<Arrangement> mainArrangement;
	
	@ExcelIgnore
	private String mainInfo;
	/**
	 * 考务信息1
	 */
	@ExcelIgnore
	List<Arrangement> secondArrangement;
	@ExcelIgnore
	private String secondInfo;
	
	

	public String getMainInfo() {
		return mainInfo;
	}

	public void setMainInfo(String mainInfo) {
		this.mainInfo = mainInfo;
	}

	public String getSecondInfo() {
		return secondInfo;
	}

	public void setSecondInfo(String secondInfo) {
		this.secondInfo = secondInfo;
	}

	public List<Arrangement> getMainArrangement() {
		return mainArrangement;
	}

	public void setMainArrangement(List<Arrangement> mainArrangement) {
		this.mainArrangement = mainArrangement;
	}

	public List<Arrangement> getSecondArrangement() {
		return secondArrangement;
	}

	public void setSecondArrangement(List<Arrangement> secondArrangement) {
		this.secondArrangement = secondArrangement;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getChief() {
		return chief;
	}

	public void setChief(int chief) {
		this.chief = chief;
	}

	public int getDeputy() {
		return deputy;
	}

	public void setDeputy(int deputy) {
		this.deputy = deputy;
	}

	public HCollege getCollege() {
		return college;
	}

	public void setCollege(HCollege college) {
		this.college = college;
	}

}
