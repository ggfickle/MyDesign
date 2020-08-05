package hfuu.examination.service;

import java.util.List;

import hfuu.examination.domain.upload.HCalendar;

public interface HCalendarService {
	int insertHcalendarBatch(List<HCalendar> data);
	/**
	 * 
	 * @Title: selCalendarPage
	 * @Description: 分页检索信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author author
	 * @date 2020-04-18 07:48:53
	 */
	List<HCalendar> selCalendarPage(String name,Integer pageNum,Integer pageSize);
	/**
	 * 
	 * @Title: selCountCalendarInfo
	 * @Description: 查询数量
	 * @return
	 * @author author
	 * @date 2020-04-18 08:02:25
	 */
	int selCountCalendarInfo(String week);
}
