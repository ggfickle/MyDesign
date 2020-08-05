package hfuu.examination.domain;

import java.io.Serializable;

public class Old_Dept implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:55  
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private int type;
	
	private int cid;
	
	private HCollege college;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public HCollege getCollege() {
		return college;
	}

	public void setCollege(HCollege college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Old_Dept [id=" + id + ", name=" + name + ", type=" + type + ", cid=" + cid + ", college=" + college
				+ "]";
	}
	
	
}
