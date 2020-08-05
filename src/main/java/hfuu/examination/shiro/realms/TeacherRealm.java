package hfuu.examination.shiro.realms;


import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;

import org.apache.shiro.subject.PrincipalCollection;

import hfuu.examination.domain.User;
import hfuu.examination.service.UserService;
/**
 * shiro中域相当于数据源，存取授权，认证信息
 * @author Administrator
 *
 */
public class TeacherRealm extends AuthorizingRealm {
	@Resource
	private UserService userService;
	/**
	 * 用于授权
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1,从PrincipalCollection获取登录用户的信息
		
		//2,利用登录用户的信息来查询用户的角色
		
		//3,创建SimpleAuthorizationInfo 并设置roles属性
		
		//4,返回SimpleAuthorizationInfo对象
		
		return null;
	}
	/**
	 * 用于认证的方法
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String userName = (String) token.getPrincipal();
		User user = this.userService.getTeacherByUserName(userName);
		
		if (user != null) {
			SecurityUtils.getSubject().getSession().setAttribute("user", user);
			AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassWord(),
					user.getRoleName());
			return authcInfo;
		}
		return null;
	}
}
