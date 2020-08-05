package hfuu.examination.domain;

import java.io.Serializable;

/**
 * 
* @ClassName: Arrangement
* @Description: 考务安排信息
* @author xhflove@foxmail.com
* @date 2020年5月8日
*
 */
public class Arrangement implements Serializable{

	
	/**
	* @Fields field:field:
	*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * 学院
	 */
	private String college;
	
	/**
	 * 重修人数
	 */
	private int rnum;
	
	/**
	 * 考试周数
	 */
	private String week;
	
	/**
	 * 主监考
	 */
	private String pname;
	
	/**
	 * 主监考id
	 */
	private String pid;
	
	/**
	 * 教师编号
	 */
	private String tid;
	
	/**
	 * 原班级考试总人数
	 */
	private String peonum;
	
	/**
	 * 班级人数1
	 */
	private String peonum0;
	
	/**
	 * 班级人数2
	 */
	private String peonum1;
	
	/**
	 * 班级编号
	 */
	private String sid;
	
	/**
	 * 教师名称
	 */
	private String teacher;
	
	/**
	 * 课程名称
	 */
	private String sname;
	
	/**
	 * 班级名称
	 */
	private String name;
	
	/**
	 * 副监考名称
	 */
	private String apname;
	
	/**
	 * 副监考id
	 */
	private String apid;
	
	/**
	 * 第一个副监考id
	 */
	private String fapid;
	
	/**
	 * 第二个副监考id
	 */
	private String sapid;
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 考试地点
	 */
	private String place;
	
	/**
	 * 星期几
	 */
	private int whichDay;
	
	/**
	 * 哪一节课考试
	 */
	private String time;

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getPeonum() {
		return peonum;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApname() {
		return apname;
	}

	public void setApname(String apname) {
		this.apname = apname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getWhichDay() {
		return whichDay;
	}

	public void setWhichDay(int whichDay) {
		this.whichDay = whichDay;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPeonum0() {
		return peonum0;
	}

	public void setPeonum0(String peonum0) {
		this.peonum0 = peonum0;
	}

	public String getPeonum1() {
		return peonum1;
	}

	public void setPeonum1(String peonum1) {
		this.peonum1 = peonum1;
	}

	public void setPeonum(String peonum) {
		this.peonum = peonum;
	}
	
	
	
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getApid() {
		return apid;
	}

	public void setApid(String apid) {
		this.apid = apid;
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

	@Override
	public String toString() {
		return "Arrangement [college=" + college + ", rnum=" + rnum + ", week=" + week + ", pname=" + pname + ", pid="
				+ pid + ", tid=" + tid + ", peonum=" + peonum + ", peonum0=" + peonum0 + ", peonum1=" + peonum1
				+ ", sid=" + sid + ", teacher=" + teacher + ", sname=" + sname + ", name=" + name + ", apname=" + apname
				+ ", apid=" + apid + ", fapid=" + fapid + ", sapid=" + sapid + ", id=" + id + ", place=" + place
				+ ", whichDay=" + whichDay + ", time=" + time + "]";
	}

	
	
	
	
}
