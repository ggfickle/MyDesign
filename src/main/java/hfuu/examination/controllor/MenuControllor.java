package hfuu.examination.controllor;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import hfuu.examination.domain.Menu;
import hfuu.examination.domain.User;
import hfuu.examination.domain.WebResult;
import hfuu.examination.service.MenuService;

@Controller
public class MenuControllor {
	@Resource
	private MenuService menuService;
	
	@RequestMapping(value="/sussess")
	public String sussessUrl() {
		//获取用户信息
		User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		//获取路径信息
		String path=menuService.selectMainTemp(user.getRid());
		//跳转主页面
		return "redirect:"+path;
	}
	@ResponseBody
	@RequestMapping("/getMenu")
	public String getUserMenu() {
		WebResult result=new WebResult();
		//获取用户信息
	    User user=(User) SecurityUtils.getSubject().getSession().getAttribute("user");
		List<Menu> data=menuService.selectMenuByRoles(0, user.getRid());
		result.setCode("0");
		result.setCount(data.size());
		result.setData(data);
		System.out.println(data);
		return JSON.toJSONString(result,SerializerFeature.DisableCircularReferenceDetect);
		
	}
}
