package hfuu.examination.domain.upload;

import java.io.Serializable;

/**
 * 
 * @ClassName: HCalendar 
 * @Description: 校历类
 * @author llm
 */
public class HCalendar implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO(描述)
	 * @author author
	 * @date 2020-04-17 08:09:28  
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	private int id;
	/**
	 * 每周第一天日期
	 */
	private String date;
	/**
	 * 周次
	 */
	private int week;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	@Override
	public String toString() {
		return "HCalendar [id=" + id + ", date=" + date + ", week=" + week + "]";
	}
	
}
