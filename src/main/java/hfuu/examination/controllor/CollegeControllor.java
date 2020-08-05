package hfuu.examination.controllor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.annotation.LogTypes;
import hfuu.examination.annotation.SystemControllerLog;
import hfuu.examination.domain.HCollege;
import hfuu.examination.domain.WebResult;
import hfuu.examination.service.HCollegeService;

/**
 * 
 * @ClassName: CollegeListControllor 
 * @Description: 院系控制层
 * @author author
 * @date 2020-03-28 03:16:44
 */
@Controller
public class CollegeControllor {
	@Resource
	private HCollegeService college_service;
	private static final Logger log = LoggerFactory.getLogger(CollegeControllor.class);
	@ResponseBody
	@RequestMapping("/collegeList")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "查看院校")
	public String collegeList() {
		WebResult wt = new WebResult();
		try
		{
			List<HCollege> data=college_service.selCollegeInfo();
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(data.size());
			wt.setData(data);
            
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		
		 return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	} 
	@ResponseBody
	@RequestMapping("/collegeListLimit")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "查看院系")
	public String collegeListLimit(@RequestParam(value="keyword",defaultValue="") String name,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult wt = new WebResult();
		try
		{
			List<HCollege> data=college_service.selCollegeInfoLimit(name,pageNum, pageSize);
			int count=college_service.selCountCollegeInfo(name);
			wt.setCode("0");
			wt.setMsg("");
			wt.setCount(count);
			wt.setData(data);
            
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		
		 return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	} 
	@ResponseBody
	@RequestMapping("/collegeAdd")
	public String collegeAdd(@RequestBody HCollege college) {
		if(college_service.selCollegeInfoIsExist(college)) {
			return "院系已存在";
		}
		if(college_service.insCollegeInfo(college)) {
			return "院系添加成功";
		}
		return "院系添加失败";
		
	}
	/**
	 * 
	 * @Title: collegeDel
	 * @Description: 院系删除
	 * @param ids
	 * @return
	 * @author llm
	 * @date 2020-03-29 01:07:47
	 */
	@ResponseBody
	@RequestMapping("/collegeDel")
	public String collegeDel(@RequestParam("id") String ids) {
		List<Integer> data=new ArrayList<Integer>();
		JSONObject res = new JSONObject();
		if(ids.contains(",")) {
			for(String id:ids.split(",")) {
				data.add(Integer.valueOf(id));
			}
		}else {
			data.add(Integer.valueOf(ids));
		}
		if(college_service.delCollegeInfo(data)) {
			res.put("code","1");
			res.put("msg","删除成功");
			return res.toJSONString();
		}
		res.put("code","0");
		res.put("msg","删除失败");
		return res.toJSONString();
		
	}
	
	@ResponseBody
	@RequestMapping("/collegeUpdate")
	public String collegeUpdate(@RequestBody HCollege college) {
		JSONObject res = new JSONObject();
		if(college_service.updCollegeInfo(college)) {
			res.put("code","1");
			return res.toJSONString();
		}
		res.put("code","0");
		return res.toJSONString();
		
	}
}
