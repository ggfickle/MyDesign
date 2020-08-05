package hfuu.examination.domain;

import java.io.Serializable;

public class UrlFilter implements Serializable{

	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-05-04 01:27:27  
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;  
    
	private String name; //url名称/描述  
    
	private String url; //地址  
    
	private String roles; //所需要的角色，可省略  
    
	private String permissions; //所需要的权限，可省略  

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	
	
}
