package hfuu.examination.domain;

import java.io.Serializable;

/**
 * 上传文件类
 * @author Administrator
 *
 */
public class UploadFile implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:11:07  
	 */
	private static final long serialVersionUID = 1L;
	
	private  Integer id;
	
	private String name;
	
	private Double size;
	
	private String date;
	
	private String path;
	
	private Integer type;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getSize() {
		return size;
	}
	public void setSize(Double size) {
		this.size = size;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
