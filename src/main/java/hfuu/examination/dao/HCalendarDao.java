package hfuu.examination.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import hfuu.examination.domain.upload.HCalendar;
/**
 * 
 * @ClassName: HCalendarDao 
 * @Description: 日历数据层
 * @author llm
 * @date 2020-03-20 02:24:01
 */
@Component
public interface HCalendarDao {
	/**
	 * 批量增加校历信息
	 * @param data
	 * @return
	 */
	int insertHcalendarBatch(List<HCalendar> data);
	/**
	 * 清空数据库
	 * @return
	 */
	@Update("truncate table hcalendar")
	int delHcalendar();
	/**
	 * 查询数据条数
	 * @return
	 */
	int selCountHcalendar(@Param("week")String week);
	/**
	 * 
	 * @Title: selCalendarsInfoLimit
	 * @Description: 分页查询日历信息
	 * @return
	 * @author author
	 * @date 2020-04-18 07:46:55
	 */
	List<HCalendar> selCalendarsInfoLimit(@Param("week")String week);
	
	@Select("select date from hcalendar where week=#{week}")
	String selCalendarDate(@Param("week")String week);
	
}
