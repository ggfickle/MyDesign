package hfuu.examination.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import hfuu.examination.dao.HCalendarDao;
import hfuu.examination.domain.upload.HCalendar;
import hfuu.examination.service.HCalendarService;
@Service
public class HCalendarServiceImpl implements HCalendarService{
	@Resource
	private HCalendarDao dao;
	@Override
	public int insertHcalendarBatch(List<HCalendar> data) {
		int index=dao.selCountHcalendar("");
		if(index>0) {
			dao.delHcalendar();
		}
		return dao.insertHcalendarBatch(data);
	}
	@Override
	public List<HCalendar> selCalendarPage(String week,Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		return dao.selCalendarsInfoLimit(week);
	}
	@Override
	public int selCountCalendarInfo(String week) {
		// TODO Auto-generated method stub
		return dao.selCountHcalendar(week);
	}

}
