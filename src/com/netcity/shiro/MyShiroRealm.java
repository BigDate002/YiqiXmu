package com.netcity.shiro;

import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.RoleService;
import com.netcity.module.service.UserService;
import java.util.List;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("myShiroRealm")
public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	RoleService roleService;
	@Autowired
	private UserService userService;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		UserEntity user = (UserEntity) pc.fromRealm(getName()).iterator().next();
		if (user != null) {
			String username = user.getUsercode();
			List<String> pers = this.userService.getPermissionsByUserName(username);
			if (pers != null && !pers.isEmpty()) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				for (String each : pers) {
					info.addStringPermission(each);
				}
				return info;
			}
		}
		return null;
	}

	public void clearCachedAuthorizationInfoByRoleId(Long id) {
		getAuthorizationCache().remove(id);
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken at) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) at;
		String username = token.getUsername();
		String password = new String(token.getPassword());
		if (username != null && !"".equals(username)) {
			UserEntity user = new UserEntity();
			user.setUsercode(username);
			user = this.userService.findList(user).get(0);
			user.setPassword(password);
			if (user != null) {
				return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
			}
		}
		return null;
	}

	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			return null;
		}
		AuthorizationInfo info = null;
		Object obj = principals.getPrimaryPrincipal();
		UserEntity user = (UserEntity) obj;
		Object key = user.getRoleId();
		if (key == null) {
			return null;
		}
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			info = cache.get(key);
		}
		if (info == null) {
			info = doGetAuthorizationInfo(principals);
			if (info != null && cache != null) {
				cache.put(key, info);
			}
		}
		return info;
	}
}