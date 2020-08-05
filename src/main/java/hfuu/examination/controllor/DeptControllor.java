package hfuu.examination.controllor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.domain.Hdept;
import hfuu.examination.service.HCollegeService;
import hfuu.examination.service.HDeptService;

@Controller
public class DeptControllor {
	@Resource
	private HDeptService dept_service;
	@Resource
	private HCollegeService college_service;
	
	@ResponseBody
    @RequestMapping("/deptList")
    public String deptList(@RequestParam(value="majorid",defaultValue="") String major,@RequestParam(value="collegeid",defaultValue="") String college,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) throws Exception {
		List<Hdept> depts = dept_service.selDeptInfoByLimit(major,college,pageNum, pageSize);
        int count = dept_service.selCountByLimit(major, college);
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("count", count);
        json.put("data", depts);
        return JSON.toJSONString(json,SerializerFeature.DisableCircularReferenceDetect);
    }
	@ResponseBody
	@RequestMapping("/deptUpdate")
	public String deptUpdate(@RequestBody Hdept dept) {
		System.out.println(dept);
		
		if(dept.getSname().contains("，")) {
			dept.setSname(dept.getSname().replace("，", ","));
		}
		dept.setCid(college_service.selIdByName(dept.getCollege().getName()));
		JSONObject res = new JSONObject();
		if(dept.getId()!=0) {
			if(dept_service.updDeptByEdit(dept)>0) {
				res.put("code","1");
				res.put("msg","更新成功");
				return res.toJSONString();
			}
		}
		else {
			if(dept_service.insDeptInfo(dept)>0) {
				res.put("code","1");
				res.put("msg","专业新增成功");
				return res.toJSONString();
			}
		}
		res.put("code","0");
		res.put("msg","操作失败");
		return res.toJSONString();
	}
	/**
	 * 
	 * @Title: deptDel
	 * @Description: TODO(描述)
	 * @param ids
	 * @return
	 * @author author
	 * @date 2020-04-06 08:57:43
	 */
	@ResponseBody
	@RequestMapping("/deptDel")
	public String deptDel(@RequestParam("id") String ids) {
		List<Integer> data=new ArrayList<Integer>();
		JSONObject res = new JSONObject();
		if(ids.contains(",")) {
			for(String id:ids.split(",")) {
				data.add(Integer.valueOf(id));
			}
		}else {
			data.add(Integer.valueOf(ids));
		}
		if(dept_service.delDeptInfo(data)) {
			res.put("code","1");
			res.put("msg","删除成功");
			return res.toJSONString();
		}
		res.put("code","0");
		res.put("msg","删除失败");
		return res.toJSONString();
		
	}
	
}
