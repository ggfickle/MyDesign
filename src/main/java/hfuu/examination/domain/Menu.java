package hfuu.examination.domain;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:10:50  
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private int pid;
	
	private int uid;
	
	private UrlFilter url;
	
	private List<Menu> children;
	
	private String image;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public UrlFilter getUrl() {
		return url;
	}

	public void setUrl(UrlFilter url) {
		this.url = url;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", pid=" + pid + ", uid=" + uid + ", url=" + url + ", children="
				+ children + ", image=" + image + "]";
	}
}
