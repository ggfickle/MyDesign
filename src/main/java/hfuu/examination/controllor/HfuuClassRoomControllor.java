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
import hfuu.examination.domain.Hfuu_ClassRoom;
import hfuu.examination.domain.WebResult;
import hfuu.examination.service.Hfuu_ClassRoomService;

@Controller
public class HfuuClassRoomControllor {
	@Resource
	private Hfuu_ClassRoomService room_service;
	private static final Logger log = LoggerFactory.getLogger(CollegeControllor.class);
	@ResponseBody
	@RequestMapping("/roomListLimit")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "查看校历")
	public String roomListLimit(@RequestParam(value="keyword",defaultValue="") String name,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult wt = new WebResult();
		try
		{
			List<Hfuu_ClassRoom> data=room_service.selRoomInfoLimit(name, pageNum, pageSize);
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(room_service.selCountRoom(name));
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
