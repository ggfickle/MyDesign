package hfuu.examination.domain;

import java.io.Serializable;

/**
 * 
 * @ClassName: HCollege 
 * @Description: 学院类
 * @author llm
 * @date 2020-03-28 03:34:39
 */
public class HCollege implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:18  
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String name;
	
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "HCollege [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	
	
}
