package hfuu.examination.controllor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.domain.Log;
import hfuu.examination.domain.WebResult;
import hfuu.examination.service.LogService;

@Controller
public class LogController {
	@Resource
	private LogService logService;
	
	private static final Logger log = LoggerFactory.getLogger(LogController.class);
	@ResponseBody
	@RequestMapping("/logList")
	public String getLogInfo(@RequestParam(value="field",defaultValue="id")String field,@RequestParam(value="order",defaultValue="desc")String order,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult result=new WebResult();
		try
		{
			List<Log> data=logService.selAll(field, order, pageNum, pageSize);
			result.setCode("0");
			result.setMsg("");
			result.setCount(logService.selCountAll());
			result.setData(data);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			result.invokeFail();
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	/**
	 * 
	 * @Title: delLog
	 * @Description: TODO(描述)
	 * @param ids
	 * @return
	 * @author author
	 * @date 2020-04-06 08:57:43
	 */
	@ResponseBody
	@RequestMapping("/delLog")
	public String delLog(@RequestParam("id") String ids) {
		List<Integer> data=new ArrayList<Integer>();
		WebResult res=new WebResult();
		if(ids.contains(",")) {
			for(String id:ids.split(",")) {
				data.add(Integer.valueOf(id));
			}
		}else {
			data.add(Integer.valueOf(ids));
		}
		try {
			logService.delLogInfo(data);
			res.setCode("0");
			res.setMsg("删除成功");
		} catch (Exception e) {
			log.error("删除日志异常:" + e.toString());
			res.invokeFail();
		}
		
		return JSON.toJSONString(res,SerializerFeature.DisableCircularReferenceDetect);
		
	}
	/**
	 * 
	 * @Title: delAll
	 * @Description: TODO(描述)
	 * @param ids
	 * @return
	 * @author author
	 * @date 2020-04-06 08:57:43
	 */
	@ResponseBody
	@RequestMapping("/delAll")
	public String delAll() {
		WebResult res=new WebResult();
		try{
			logService.delAllLogInfo();
			res.setCode("0");
			res.setMsg("删除成功");
		}catch (Exception e) {
			log.error("删除日志异常:" + e.toString());
			res.invokeFail();
		}
		
		return JSON.toJSONString(res,SerializerFeature.DisableCircularReferenceDetect);
		
	}
}
