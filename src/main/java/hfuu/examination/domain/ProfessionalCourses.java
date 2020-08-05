package hfuu.examination.domain;

import java.io.Serializable;

import hfuu.examination.domain.upload.Hfuu_Course;
import hfuu.examination.domain.upload.Hfuu_Teacher;

public class ProfessionalCourses implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:11:01  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 课程编号
	 */
	private int cid;
	/**
	 * 课程信息
	 */
	private Hfuu_Course course;
	/**
	 * 班级人数
	 */
	private String number;
	/**
	 * 地点
	 */
	private String place;
	/**
	 * 时间
	 */
	private String time;
	/**
	 * 主监考id
	 */
	private String pid;
	/**
	 * 主监考姓名
	 */
	private Hfuu_Teacher proctor;
	/**
	 * 第一个副监考id
	 */
	private String fapid;
	/**
	 * 第一个副监考信息
	 */
	private Hfuu_Teacher firstAssociateProctor;
	/**
	 * 第二个副监考id
	 */
	private String sapid;
	/**
	 * 第二个副监考姓名
	 */
	private Hfuu_Teacher secondAssociateProctor;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public Hfuu_Course getCourse() {
		return course;
	}
	public void setCourse(Hfuu_Course course) {
		this.course = course;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Hfuu_Teacher getProctor() {
		return proctor;
	}
	public void setProctor(Hfuu_Teacher proctor) {
		this.proctor = proctor;
	}
	public String getFapid() {
		return fapid;
	}
	public void setFapid(String fapid) {
		this.fapid = fapid;
	}

	public String getSapid() {
		return sapid;
	}
	public void setSapid(String sapid) {
		this.sapid = sapid;
	}
	public Hfuu_Teacher getFirstAssociateProctor() {
		return firstAssociateProctor;
	}
	public void setFirstAssociateProctor(Hfuu_Teacher firstAssociateProctor) {
		this.firstAssociateProctor = firstAssociateProctor;
	}
	public Hfuu_Teacher getSecondAssociateProctor() {
		return secondAssociateProctor;
	}
	public void setSecondAssociateProctor(Hfuu_Teacher secondAssociateProctor) {
		this.secondAssociateProctor = secondAssociateProctor;
	}
	@Override
	public String toString() {
		return "PublicCourses [id=" + id + ", cid=" + cid + ", course=" + course + ", number=" + number + ", place="
				+ place + ", time=" + time + ", pid=" + pid + ", proctor=" + proctor + ", fapid=" + fapid
				+ ", firstAssociateProctor=" + firstAssociateProctor + ", sapid=" + sapid + ", secondAssociateProctor="
				+ secondAssociateProctor + "]";
	}
	
	
	
	
}
