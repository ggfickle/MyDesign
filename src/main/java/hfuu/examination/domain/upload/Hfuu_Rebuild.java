package hfuu.examination.domain.upload;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 重修学生
 */
public class Hfuu_Rebuild implements Serializable{

    /**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:09:49  
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 重修学生编号
     */
    @ExcelIgnore
    private int r_id;

    /**
     * 班级编号
     */
    @ExcelIgnore
    private int classId;

    /**
     * 班级名称
     */
    @ExcelProperty(index = 9)
    private String className;

    /**
     * 学生姓名
     */
    @ExcelProperty(index = 8)
    private String sname;

    /**
     * 学号
     */
    @ExcelProperty(index = 7)
    private int sid;

    /**
     * 重修课程名称
     */
    @ExcelProperty(index = 2)
    private String courseName;

    /**
     * 重修课程代码
     */
    @ExcelProperty(index = 1)
    private String courseId;

    /**
     * 跟随班级名称
     */
    @ExcelProperty(index = 4)
    private String f_name;

    /**
     * 跟随班级编号
     */
    @ExcelIgnore
    private int f_id;

    /**
     * 上课时间地点
     */
    @ExcelIgnore
    private String r_dateAddr;

    /**
     * 教师编号
     */
    @ExcelIgnore
    private String t_id;

    /**
     * 教师姓名
     */
    @ExcelProperty(index = 5)
    private String t_name;

    /**
     * 审核意见
     */
    @ExcelProperty(index = 14)
    private String checkFlag;

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getR_dateAddr() {
        return r_dateAddr;
    }

    public void setR_dateAddr(String r_dateAddr) {
        this.r_dateAddr = r_dateAddr;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    @Override
    public String toString() {
        return "Hfuu_Rebuild{" +
                "r_id=" + r_id +
                ", classId=" + classId +
                ", className='" + className + '\'' +
                ", sname='" + sname + '\'' +
                ", sid=" + sid +
                ", courseName='" + courseName + '\'' +
                ", courseId=" + courseId +
                ", f_name='" + f_name + '\'' +
                ", f_id=" + f_id +
                ", r_dateAddr='" + r_dateAddr + '\'' +
                ", t_id=" + t_id +
                ", t_name='" + t_name + '\'' +
                ", checkFlag='" + checkFlag + '\'' +
                '}';
    }
}
