package hfuu.examination.domain.upload;

import java.io.Serializable;

import hfuu.examination.domain.HCollege;

/**
 * 
 * @ClassName: Hfuu_Class 
 * @Description: TODO(描述)
 * @author hoongfei
 * @date 2020-03-23 12:54:12
 */
public class Hfuu_Class implements Serializable{
	

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:09:36  
	 */
	private static final long serialVersionUID = 1L;

	//编号
	private int  id;
 
	//班级名称
    private String name;

    //所属专业编号
    private int did;
    
    //所属院别
    private HCollege college;
    
    public String getName() {
        return name;
    }
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    public void setName(String name) {
        this.name = name;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }
    
    public HCollege getCollege() {
		return college;
	}
	public void setCollege(HCollege college) {
		this.college = college;
	}
	@Override
	public String toString() {
		return "Hfuu_Class [id=" + id + ", name=" + name + ", did=" + did + "]";
	}
}
