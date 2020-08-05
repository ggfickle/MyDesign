package hfuu.examination.controllor;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.annotation.LogTypes;
import hfuu.examination.annotation.SystemControllerLog;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.HCalendar;
import hfuu.examination.service.HCalendarService;


@Controller
public class CalendarControllor {
	@Resource
	private HCalendarService calendar_service;
	private static final Logger log = LoggerFactory.getLogger(CollegeControllor.class);
	@ResponseBody
	@RequestMapping("/calendarListLimit")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "查看校历")
	public String calendarList(@RequestParam(value="keyword",defaultValue="") String week,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult wt = new WebResult();
		try
		{
			List<HCalendar> data=calendar_service.selCalendarPage(week,pageNum,pageSize);
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(calendar_service.selCountCalendarInfo(week));
			wt.setData(data);
            
		}
		catch(Exception e)
		{
		    log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	} 
}
