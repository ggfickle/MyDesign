package hfuu.examination.domain.upload;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;

public class MediationExcel implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:04  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 调停课类别
	 */
	@ExcelProperty("调停课类别")
	private String medType;
	/**
	 * 原教师名称
	 */
	@ExcelProperty("原教师")
	private String teacherName;
	/**
	 * 新教师名称
	 */
	@ExcelProperty("新教师")
	private String newTeacher;
	/**
	 * 课程名称
	 */
	
	@ExcelProperty("课程名称")
	private String className;
	/**
	 * 原上课时间
	 */
	@ExcelProperty("原上课时间")
	private String pastDate;
	/**
	 * 新上课时间
	 */
	@ExcelProperty("新上课时间")
	private String newDate;
	/**
	 * 原上课地点
	 */
	@ExcelProperty("原上课地点")
	private String pastPlace;
	/**
	 * 新上课地点
	 */
	@ExcelProperty("新上课地点")
	private String newPlace;
	/**
	 * 原因类别
	 */
	@ExcelProperty("原因类别")
	private String note;
	public String getMedType() {
		return medType;
	}
	public void setMedType(String medType) {
		this.medType = medType;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getNewTeacher() {
		return newTeacher;
	}
	public void setNewTeacher(String newTeacher) {
		this.newTeacher = newTeacher;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	
	
	
}
