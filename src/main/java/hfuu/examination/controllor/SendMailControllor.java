package hfuu.examination.controllor;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.annotation.LogTypes;
import hfuu.examination.annotation.SystemControllerLog;
import hfuu.examination.domain.User;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.impl.SendMailServiceImpl;
import hfuu.examination.service.impl.UserServiceImpl;

@Controller
public class SendMailControllor {
	@Resource
	private SendMailServiceImpl sendMailService;
	@Resource
	private UserServiceImpl userService;
	
	private static final Logger log = LoggerFactory.getLogger(ProfessionalCoursesControllor .class);
	
	@ResponseBody
	@RequestMapping("/sendSingleMail")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "单个邮件发送")
	public String sendSingleMail(@RequestBody Hfuu_Teacher teacher) {
		WebResult wt = new WebResult();
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		
		try {
			User emailAndStmp = userService.selEmailAndStmpByName(user.getUserName());
			user.setEmail(emailAndStmp.getEmail());
			user.setSmtp(emailAndStmp.getSmtp());
			sendMailService.sendSingleMail(teacher,user);
			wt.setCode("0");
			wt.setMsg("发送成功");
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		
	}
	
	@ResponseBody
	@RequestMapping("/sendAllMail")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.view,description = "发送全部邮件")
	public String sendAllMail(@RequestBody List<Hfuu_Teacher> teacherList) {
		WebResult wt = new WebResult();
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		try {
			User emailAndStmp = userService.selEmailAndStmpByName(user.getUserName());
			user.setEmail(emailAndStmp.getEmail());
			user.setSmtp(emailAndStmp.getSmtp());
			sendMailService.sendAllMail(teacherList,user);
			wt.setCode("0");
			wt.setMsg("发送成功");
			return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
		} catch (Exception e) {
			log.error("查询异常:" + e.toString());
			wt.invokeFail();
		}
		return JSON.toJSONString(wt,SerializerFeature.DisableCircularReferenceDetect);
	}
	
}
