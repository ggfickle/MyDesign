package hfuu.examination.domain;

import java.io.Serializable;

public class Hdept implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:25  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 专业主键
	 */
	private int id;
	/**
	 * 专业名
	 */
	private String name;
	/**
	 * 专业简称
	 */
	private String sname;
	/**
	 * 院编号
	 */
	private int cid;
	/**
	 * 院信息
	 */
	
	private HCollege college;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
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
	@Override
	public String toString() {
		return "Hdept [id=" + id + ", name=" + name + ", sname=" + sname + ", cid=" + cid + ", college=" + college
				+ "]";
	}
	
}
