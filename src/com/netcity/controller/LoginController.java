package com.netcity.controller;

import com.netcity.common.util.NetworkUtil;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.LandingLogEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.LandingLogService;
import com.netcity.module.service.UserService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.BASE64Decoder;

@Controller
public class LoginController {
	@Autowired
	UserService userService;
	@Autowired
	LandingLogService logService;

	@RequestMapping({ "/toLogin" })
	public String toLogin() {
		return "login";
	}

	@RequestMapping(value = { "/login.do" }, method = { RequestMethod.GET })
	public String login() {
		return "login";
	}

	@RequestMapping(value = { "/login.do" }, method = { RequestMethod.POST })
	public ModelAndView Login(String username, String password, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		BASE64Decoder decoder = new BASE64Decoder();
		boolean result = false;
		try {
			password = new String(decoder.decodeBuffer(password), "UTF-8");
			ModelAndView mav = new ModelAndView();
			UserEntity user = new UserEntity();
			user.setUsercode(username);
			List<UserEntity> list = this.userService.findList(user);
			if (list.size() > 0) {
				user = list.get(0);
				if (user.getLockTime() != null && System.currentTimeMillis() - user.getLockTime().getTime() < 300000L) {
					mav.setViewName("redirect:/login.html");
					redirectAttributes.addFlashAttribute("msg", "该账号已被锁定,请五分钟后重试");
					return mav;
				}
				if (!password.equals(user.getPassword())) {
					mav.setViewName("redirect:/login.html");
					redirectAttributes.addFlashAttribute("username", username);
					redirectAttributes.addFlashAttribute("msg", "帐号或密码错误");
					try {
						if (user.getChance().longValue() == 0L && user.getLockTime() != null
								&& System.currentTimeMillis() - user.getLockTime().getTime() >= 300000L) {
							user.setChance(Long.valueOf(2L));
						} else if (user.getChance().longValue() > 0L) {
							user.setChance(Long.valueOf(user.getChance().longValue() - 1L));
						}
						if (user.getChance().longValue() == 0L) {
							user.setLockTime(new Date());
						}
						this.userService.updateEntity(user);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					return mav;
				}
				if (user.getUserState()==0) {
					mav.setViewName("redirect:/login.html");
					redirectAttributes.addFlashAttribute("username", username);
					redirectAttributes.addFlashAttribute("msg", "该用户无登录权限");
					try {
						if (user.getChance().longValue() == 0L && user.getLockTime() != null
								&& System.currentTimeMillis() - user.getLockTime().getTime() >= 300000L) {
							user.setChance(Long.valueOf(2L));
						} else if (user.getChance().longValue() > 0L) {
							user.setChance(Long.valueOf(user.getChance().longValue() - 1L));
						}
						if (user.getChance().longValue() == 0L) {
							user.setLockTime(new Date());
						}
						this.userService.updateEntity(user);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					return mav;
				}
			} else {
				mav.setViewName("redirect:/login.html");
				redirectAttributes.addFlashAttribute("msg", "帐号或密码错误");
				return mav;
			}
			SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsercode(), user.getPassword());
			Subject subject = SecurityUtils.getSubject();
			subject.login((AuthenticationToken) token);
			subject.getSession().setTimeout(259200000L);
			try {
				user.setChance(Long.valueOf(3L));
				this.userService.updateEntity(user);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			if (!user.getIsDelete().booleanValue() && "123456".equals(user.getPassword())) {
				mav.setViewName("redirect:/user/setpwd.html");
			} else {
				mav.setViewName("redirect:/index.html");
				try {
					Cookie c = new Cookie("JSESSIONID", URLEncoder.encode(request.getSession().getId(), "utf-8"));
					c.setPath("/");
					c.setMaxAge(259200);
					response.addCookie(c);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			result = true;
			return mav;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LandingLogEntity log = new LandingLogEntity();
			log.setUsercode(username);
			try {
				log.setIp(NetworkUtil.getIpAddress(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.setResult(Boolean.valueOf(result));
			log.setType(Long.valueOf(0L));
			this.logService.insert(log);
		}

		return null;
	}
}