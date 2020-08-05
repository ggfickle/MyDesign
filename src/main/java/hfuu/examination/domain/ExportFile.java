package hfuu.examination.domain;

/**
 * 
 * @ClassName: ExportFile 
 * @Description: 导出文件信息类
 * @author author
 * @date 2020-05-16 08:31:35
 */
public class ExportFile {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 路径
	 */
	private String path;
	/**
	 * 大小
	 */
	private Double size;
	/**
	 * 创建时间
	 */
	private String date;
	/**
	 * 下载次数
	 */
	private Integer number;
	
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
}
