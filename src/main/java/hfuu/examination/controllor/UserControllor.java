package hfuu.examination.controllor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
import hfuu.examination.domain.User;
import hfuu.examination.domain.WebResult;
import hfuu.examination.domain.upload.Hfuu_Teacher;
import hfuu.examination.service.HCollegeService;
import hfuu.examination.service.ProfessionalCoursesService;
import hfuu.examination.service.UserService;
import hfuu.examination.service.WxLoginService;
import hfuu.examination.service.impl.SendMailServiceImpl;
import hfuu.examination.utils.CryptographyUtil;


@Controller
public class UserControllor {
	@Resource
	private UserService service;
	@Resource
	private HCollegeService collegeService;
	@Resource
	private WxLoginService wxLoginService;
	@Resource
	private ProfessionalCoursesService professionalService;
	private static final Logger log = LoggerFactory.getLogger(LogController.class);

	/**
	 * 
	 * @Title: login
	 * @Description: 登录模块
	 * @param user
	 * @param request
	 * @return
	 * @author author
	 * @throws IOException 
	 * @date 2020-05-01 01:34:48
	 */
	@ResponseBody
	@RequestMapping(value="/userlogin")
	@SystemControllerLog(moduleType=LogTypes.moduleType.LOGIN,operateValue=LogTypes.operateValue.login,description = "用户登录")
	public String login(User user,@RequestParam(defaultValue="")String rememberMe, HttpServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),
				CryptographyUtil.md5(user.getPassWord(), "hfuuExamination"));
		WebResult result=new WebResult();
		try {
			subject.login(token);
			//User tuser = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
			result.setCode("1");
			result.setMsg("登录成功");
			//result.setData(tuser.getRoleName());
		} catch (Exception e) {
			result.setCode("0");
			result.setMsg("用户名或密码错误!");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	@ResponseBody
	@RequestMapping("/getUserName")
	public String getUserName() {
		WebResult result=new WebResult();
		
		try
		{
			User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
			if(user.getRoleName().equals("teacher")) {
				result.setData(user.getAccountNo());
			}else {
				result.setData(user.getUserName());
			}
			result.setCode("0");
			result.setMsg("");
			
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			result.invokeFail();
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@ResponseBody
	@RequestMapping("/adminList")
	public String getAllAdmin(@RequestParam(value="college",defaultValue="")String college,@RequestParam("page") Integer pageNum, @RequestParam("limit") Integer pageSize) {
		WebResult result=new WebResult();
		
		try
		{
			List<User> data=service.selAllAdmin(college, pageNum, pageSize);
			result.setCode("0");
			result.setMsg("");
			result.setCount(service.selCountAllAdmin(college));
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
	@RequestMapping("/adminAdd")
	public String AdminAdd(@RequestBody User user) {
		WebResult result=new WebResult();
		try
		{
			if(service.getAdminByUserNameAndPwd(user.getUserName(), user.getPassWord())!=null) {
				result.setCode("1");
				result.setMsg("账号密码已存在");
			}else {
				user.setCid(collegeService.selIdByName(user.getCollege().getName()));
				user.setRid(2);
				user.setPassWord(CryptographyUtil.md5(user.getPassWord(),"hfuuExamination"));
				service.insAdminInfo(user);
				result.setCode("0");
				result.setMsg("添加成功");
			}
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
	 * @Title: deptDel
	 * @Description: TODO(描述)
	 * @param ids
	 * @return
	 * @author author
	 * @date 2020-04-06 08:57:43
	 */
	@ResponseBody
	@RequestMapping("/adminDel")
	public String adminDel(@RequestParam("id") String ids) {
		List<Integer> data=new ArrayList<Integer>();
		JSONObject res = new JSONObject();
		if(ids.contains(",")) {
			for(String id:ids.split(",")) {
				data.add(Integer.valueOf(id));
			}
		}else {
			data.add(Integer.valueOf(ids));
		}
		if(service.delAdminInfo(data)) {
			res.put("code","1");
			res.put("msg","删除成功");
			return res.toJSONString();
		}
		res.put("code","0");
		res.put("msg","删除失败");
		return res.toJSONString();
		
	}
	@ResponseBody
	@RequestMapping("/adminUpdPwd")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "用户密码重置")
	public String adminUpdPwd(@RequestParam("id")String id) {
		String pwd=CryptographyUtil.md5("123456", "hfuuExamination");
		WebResult result=new WebResult();
		if(service.updAdminPwd(id, pwd)) {
			result.setCode("0");
			result.setMsg("管理员密码重置为初始密码");
		}else {
			result.setCode("1");
			result.setMsg("管理员密码重置失败");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	
	@ResponseBody
	@RequestMapping("/adminEmailUpdate")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "用户密码重置")
	public String adminEmailUpdate(@RequestBody User user) {
		WebResult result=new WebResult();
		System.out.println(user);
		if(service.updAdminEmail(user.getAccountNo(), user.getEmail(),user.getSmtp())) {
			result.setCode("0");
			result.setMsg("更新成功");
		}else {
			result.setCode("1");
			result.setMsg("更新失败");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	@ResponseBody
	@RequestMapping("/adminInfo")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "用户密码重置")
	public String adminInfo() {
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		String id=user.getAccountNo();
		WebResult result=new WebResult();
		try
		{
			System.out.println(user);
			User u=null;
			if(user.getRid()==3) {
				u=service.selTeacherById(user.getUserName());
			}else {
				u=service.selAdminById(id);
			}
			System.out.println(u);
			result.setCode("0");
			result.setMsg("");
			result.setData(u);
		}
		catch(Exception e)
		{
			log.error("查询异常:" + e.toString());
			result.invokeFail();
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	@ResponseBody
	@RequestMapping("/adminUpdate")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "管理员信息更新")
	public String adminUpdate(@RequestBody User user) {
		WebResult result=new WebResult();
		User u=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		String id=u.getAccountNo();
		user.setAccountNo(id);
		user.setCid(collegeService.selIdByName(user.getCollege().getName()));
		if(user.getPassWord().contains("*")) {
			user.setPassWord(u.getPassWord());
		}else {
			user.setPassWord(CryptographyUtil.md5(u.getPassWord(),"hfuuExamination"));
		}
		if(service.updAdminById(user)) {
			result.setCode("0");
			result.setMsg("个人信息更新成功");
		}else {
			result.setCode("1");
			result.setMsg("个人信息更新失败");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
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
	@ResponseBody
	@RequestMapping("/teacherEmailUpdate")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "用户密码重置")
	public String TeacherEmailUpdate(@RequestBody User user) {
		WebResult result=new WebResult();
		System.out.println(user);
		if(service.updTeacherEmail(user.getAccountNo(), user.getEmail())) {
			result.setCode("0");
			result.setMsg("更新成功");
		}else {
			result.setCode("1");
			result.setMsg("更新失败");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	/**
	 * 
	 * @Title: teacherUpdate
	 * @Description: 用户信息更新
	 * @param user
	 * @return
	 * @author author
	 * @date 2020-05-27 02:16:34
	 */
	@ResponseBody
	@RequestMapping("/teacherUpdate")
	@SystemControllerLog(moduleType=LogTypes.moduleType.PROJECT,operateValue=LogTypes.operateValue.edit,description = "用户信息更新")
	public String teacherUpdate(@RequestBody User user) {
		WebResult result=new WebResult();
		User u=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		
		if(user.getPassWord().contains("*")) {
			user.setPassWord(u.getPassWord());
		}else {
			user.setPassWord(CryptographyUtil.md5(u.getPassWord(),"hfuuExamination"));
		}
		try{
			service.updteacherById(user);
			result.setCode("0");
			result.setMsg("个人信息更新成功");
		}catch (Exception e) {
		
			result.setCode("1");
			result.setMsg("个人信息更新失败");
		}
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
	}
	/**
	 * 小程序登录模块
	* @Title: wxLogin
	* @Description: 
	* @param @param username
	* @param @param password    参数
	* @return void    返回类型
	* @throws
	 */
	@ResponseBody
	@RequestMapping("/wxLogin")
	public String login(@RequestBody User user) {
		System.out.println(user.getUserName()+"-----"+user.getPassWord());
		Hfuu_Teacher teacher=wxLoginService.selTeacherByNameAndPwd(user.getUserName(), CryptographyUtil.md5(user.getPassWord(), "hfuuExamination"));
		System.out.println(teacher);
		if(teacher==null) {
			return "error";
		}
		teacher.setMainArrangement(professionalService.selMainArrangementInfoById(user.getUserName()));
		teacher.setSecondArrangement(professionalService.selSecondArrangemnetInfoById(user.getUserName()));
		teacher.setMainInfo(new SendMailServiceImpl().getMainArrangement(teacher,"wx"));
		teacher.setSecondInfo(new SendMailServiceImpl().getSecondArrangement(teacher,"wx"));
		return JSON.toJSONString(teacher);
	}

	 /**
     * 
     * @return 注销
     * @throws Exception
     */
	@RequestMapping("/logout")
	public String logout(){
		SecurityUtils.getSubject().logout();
		return "redirect:/login.html";
	}
}


