package hfuu.examination.domain;
/**
 * 
 * @ClassName: ExportExcel 
 * @Description: 文件导出类
 * @author author
 * @date 2020-05-15 10:49:07
 */
public class ExportExcel {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 授课教师
	 */
	private String teacher;
	/**
	 * 课程名
	 */
	private String sname;
	/**
	 * 班级名
	 */
	private String className;
	/**
	 * 考试人数
	 */
	private String number;
	/**
	 * 考试地点
	 */
	private String place;
	/**
	 * 考试时间
	 */
	private String time;
	/**
	 * 主监考
	 */
	private String pname;
	/**
	 * 副监考
	 */
	private String apname;
	/**
	 * 学院名
	 */
	private String college;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getApname() {
		return apname;
	}
	public void setApname(String apname) {
		this.apname = apname;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	@Override
	public String toString() {
		return "ExportExcel [id=" + id + ", teacher=" + teacher + ", sname=" + sname + ", className=" + className
				+ ", number=" + number + ", place=" + place + ", time=" + time + ", pname=" + pname + ", apname="
				+ apname + ", college=" + college + "]";
	}
	
	
}
