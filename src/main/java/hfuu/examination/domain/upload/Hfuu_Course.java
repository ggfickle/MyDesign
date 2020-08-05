package hfuu.examination.domain.upload;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;


/**
 * 
 * @ClassName: Hfuu_Course 
 * @Description: TODO(描述)
 * @author hoongfei
 * @date 2020-03-23 12:53:51
 */
public class Hfuu_Course implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:09:02  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 课程编号
	 */
	@ExcelIgnore
	private int id;
    /**
     * 教师编号
     */
    @ExcelProperty(index = 2)
    private String tid;
    /**
     * 教师名
     */
    @ExcelIgnore
    private Hfuu_Teacher teacher;
    /**
     * 课程代码
     */
    @ExcelProperty(index = 0)
    private String sid;

    /**
     * 班级编号
     */
    @ExcelIgnore
    private int cid;
    /**
     * 班级
     */
    @ExcelIgnore
    private Hfuu_Class subject;
    /**
     *课程名称
     */
    @ExcelProperty(index = 1)
    private String sname;
    /**
     *课程属性名称，用于在学院所上课程中检索
     */
    @ExcelProperty(index = 17)
    private String sattriName;
    /**
     *考核方式，用于在学院所上课程中检索
     */
    @ExcelProperty(index = 18)
    private String method;
    /**
     * 用于添加到课程信息表中
     */
    @ExcelIgnore
    private int sattri;

    /**
     *课程时间地点
     */
    @ExcelIgnore
    private String date_addr;

    /**
     *监考分配状况
     */
    @ExcelIgnore
    private int type;

    public String getSattriName() {
		return sattriName;
	}

	public void setSattriName(String sattriName) {
		this.sattriName = sattriName;
	}
    
	

	public Hfuu_Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Hfuu_Teacher teacher) {
		this.teacher = teacher;
	}

	public Hfuu_Class getSubject() {
		return subject;
	}

	public void setSubject(Hfuu_Class subject) {
		this.subject = subject;
	}

	public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getSid() {
        return sid;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSid(String sid) {
        this.sid = sid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getSattri() {
        return sattri;
    }

    public void setSattri(int sattri) {
        this.sattri = sattri;
    }

    public String getDate_addr() {
        return date_addr;
    }

    public void setDate_addr(String date_addr) {
        this.date_addr = date_addr;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "Hfuu_Course [id=" + id + ", tid=" + tid + ", teacher=" + teacher + ", sid=" + sid + ", cid=" + cid
				+ ", subject=" + subject + ", sname=" + sname + ", sattriName=" + sattriName + ", method=" + method
				+ ", sattri=" + sattri + ", date_addr=" + date_addr + ", type=" + type + "]";
	}



	

	
}
