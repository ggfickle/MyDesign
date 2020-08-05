package hfuu.examination.controllor;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.annotation.LogTypes;
import hfuu.examination.annotation.SystemControllerLog;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.UserService;
import hfuu.examination.utils.CryptographyUtil;

public class TeacherController {
	@Resource
	private UserService service;
	private static final Logger log = LoggerFactory.getLogger(LogController.class);
	@ResponseBody
	@RequestMapping("/teacherList")
	public String getTeacherUser(@RequestParam(value="name",defaultValue="")String name,@RequestParam(value="college",defaultValue="")String college,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult result=new WebResult();
		
		try
		{
			List<Hfuu_Teacher> data=service.selTeacherInfo(name, college, pageNum, pageSize);
			result.setCode("0");
			result.setMsg("");
			result.setCount(service.selCountTeacherInfoByLimit(name, college));
			result.setData(data);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			result.invokeFail();
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	@ResponseBody
	@RequestMapping("/teacheUpdPwd")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "用户密码重置")
	public String teacheUpdPwd(@RequestParam("id")String id) {
		String pwd=CryptographyUtil.md5(id, "hfuuExamination");
		WebResult result=new WebResult();
		if(service.updTeacherPwd(id, pwd)) {
			result.setCode("0");
			result.setMsg("教师密码重置为初始密码");
		}else {
			result.setCode("1");
			result.setMsg("教师密码重置失败");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
}
