package hfuu.examination.domain;

import java.io.Serializable;

public class Hfuu_ClassRoom implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:31  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 教室编号
	 */
	private int id;
	/**
	 * 教室名称
	 */
	private String name;
	/**
	 * 安排时间
	 */
	private String date_addr;
	
	private String mid;
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
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getDate_addr() {
		return date_addr;
	}
	public void setDate_addr(String date_addr) {
		this.date_addr = date_addr;
	}
}
