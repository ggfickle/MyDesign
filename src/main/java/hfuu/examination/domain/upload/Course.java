package hfuu.examination.domain.upload;

import java.io.Serializable;

public class Course implements Serializable{
	 /**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:09:19  
	 */
	private static final long serialVersionUID = 1L;

	//课程名称
    private String courseName;

    //班级名称
    private String className;

    //课程时间地点
    private String dateAddr;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDateAddr() {
        return dateAddr;
    }

    public void setDateAddr(String dateAddr) {
        this.dateAddr = dateAddr;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", className='" + className + '\'' +
                ", dateAddr='" + dateAddr + '\'' +
                '}';
    }

}
