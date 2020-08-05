package hfuu.examination.domain;

import java.io.Serializable;

public class Hfuu_Mediation implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:37  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 调停课编号
	 */
	private  int id;
	/**
	 * 原教师编号
	 */
	private  String tid;
	/**
	 * 新教师编号
	 */
	private String ntid;
	/**
	 * 课程编号
	 */
	private int cid;
	/**
	 * 调停课类别
	 */
	private int type;
	/**
	 * 原上课时间
	 */
	private String pastDate;
	/**
	 * 新上课时间
	 */
	private String newDate;
	/**
	 * 原上课地点
	 */
	private String pastPlace;
	/**
	 * 新上课地点
	 */
	private String newPlace;
	/**
	 * 原因类别
	 */
	private String note;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getNtid() {
		return ntid;
	}
	public void setNtid(String ntid) {
		this.ntid = ntid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPastDate() {
		return pastDate;
	}
	public void setPastDate(String pastDate) {
		this.pastDate = pastDate;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
	public String getPastPlace() {
		return pastPlace;
	}
	public void setPastPlace(String pastPlace) {
		this.pastPlace = pastPlace;
	}
	public String getNewPlace() {
		return newPlace;
	}
	public void setNewPlace(String newPlace) {
		this.newPlace = newPlace;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString() {
		return "Hfuu_Mediation [id=" + id + ", tid=" + tid + ", ntid=" + ntid + ", cid=" + cid + ", type=" + type
				+ ", pastDate=" + pastDate + ", newDate=" + newDate + ", pastPlace=" + pastPlace + ", newPlace="
				+ newPlace + ", note=" + note + "]";
	}


}
