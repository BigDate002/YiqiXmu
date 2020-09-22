package com.netcity.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.gwm.util.SHA1Util;
import com.netcity.common.util.NetworkUtil;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.ExamEntity;
import com.netcity.module.entity.ExamResultEntity;
import com.netcity.module.entity.ExamUserEntity;
import com.netcity.module.entity.LandingLogEntity;
import com.netcity.module.entity.PaperEntity;
import com.netcity.module.entity.PracticeEntity;
import com.netcity.module.entity.QualificationEntity;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.ExamResultService;
import com.netcity.module.service.ExamService;
import com.netcity.module.service.ExamUserService;
import com.netcity.module.service.LandingLogService;
import com.netcity.module.service.PaperService;
import com.netcity.module.service.PracticeService;
import com.netcity.module.service.QualificationService;
import com.netcity.module.service.TrainService;
import com.netcity.module.service.UserService;
import com.netcity.util.ResponseFlag;
import com.taobao.api.ApiException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.BASE64Decoder;

@Controller
@RequestMapping({ "/mobile" })
public class MobileController {
	@Autowired
	QualificationService qualificationService;
	@Autowired
	UserService userService;
	@Autowired
	ExamUserService examUserService;
	@Autowired
	TrainService trainService;
	@Autowired
	PaperService paperService;
	@Autowired
	ExamService examService;
	@Autowired
	ExamResultService examResultService;
	@Autowired
	PracticeService practiceService;
	@Autowired
	LandingLogService logService;

	@RequestMapping({ "/tishi.html" })
	public String tishi() {
		return "/mobile/tishi.html";
	}

	@RequestMapping({ "/login.html" })
	public String login() {
		return "/mobile/login.html";
	}

	@RequestMapping({ "/ddlogin.html" })
	public String ddlogin() {
		return "/mobile/ddlogin.html";
	}

	@RequestMapping(value = { "logout.do" }, method = { RequestMethod.GET })
	@ResponseBody
	public ResponseFlag logout() {
		ResponseFlag res = new ResponseFlag();
		res.setFlag(Boolean.valueOf(true));
		try {
			SecurityUtils.getSubject().logout();
		} catch (Exception exception) {
		}

		return res;
	}

	@RequestMapping({ "/ceshi.html" })
	public String ceshi(Model model) {
		return "/mobile/ceshi.html";
	}

	@RequestMapping({ "/selectuser.html" })
	public String selectuser() {
		return "/mobile/selectuser.html";
	}
	@RequestMapping({ "/special.html" })
	public String special(Model model) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		user = (UserEntity) this.userService.getById(user.getId());
		model.addAttribute("user", user);
		return "/mobile/special.html";
	}
	@RequestMapping({ "/test.html" })
	public String test(@RequestParam("id") Long id, Model mv) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		ExamEntity exam = (ExamEntity) this.examService.getById(id);
		PaperEntity pe = new PaperEntity();
		pe.setCode(exam.getCode());
		pe.setUserCode(user.getUsercode());
		List<PaperEntity> plist = this.paperService.findList(pe);
		mv.addAttribute("papers", plist);
		mv.addAttribute("exam", exam);
		return "/mobile/test.html";
	}

	@RequestMapping({ "/detail.html" })
	public String detail(@RequestParam("id") Long id, Model mv) {
		ExamEntity exam = (ExamEntity) this.examService.getById(id);
		PaperEntity pe = new PaperEntity();
		pe.setCode(exam.getCode());
		List<PaperEntity> plist = this.paperService.findList(pe);
		mv.addAttribute("papers", plist);
		mv.addAttribute("exam", exam);
		return "/mobile/detail.html";
	}

	@RequestMapping({ "/score.html" })
	public String score(@RequestParam("id") Long id, Model mv) {
		ExamEntity exam = (ExamEntity) this.examService.getById(id);
		PaperEntity pe = new PaperEntity();
		pe.setCode(exam.getCode());
		List<PaperEntity> plist = this.paperService.findList(pe);
		PracticeEntity pre = new PracticeEntity();
		pre.setCode(exam.getCode());
		List<PracticeEntity> prlist = this.practiceService.findList(pre);
		if (prlist.size() > 0) {
			pre = prlist.get(0);
		} else {
			pre = null;
		}
		mv.addAttribute("papers", plist);
		mv.addAttribute("p", pre);
		mv.addAttribute("exam", exam);
		return "/mobile/score.html";
	}

	@RequestMapping({ "/main.html" })
	public String main(Model model) {
		TrainEntity te = new TrainEntity();
		ExamUserEntity eue = new ExamUserEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		te.setUsercode(user.getUsercode());
		te.setState(Long.valueOf(1L));
		List<TrainEntity> list1 = this.trainService.findList(te);
		te.setState(Long.valueOf(2L));
		List<TrainEntity> list2 = this.trainService.findList(te);
		eue.setUsercode(user.getUsercode());
		List<ExamUserEntity> list3 = this.examUserService.findList(eue);
		long m1 = list3.stream().filter(x -> (x.getState().longValue() == 0L && x.getStatus().longValue() == 1L))
				.count();
		long m2 = list3.stream().filter(x -> (x.getStatus().longValue() == 2L)).count();
		model.addAttribute("n1", Integer.valueOf(list1.size()));
		model.addAttribute("n2", Integer.valueOf(list2.size()));
		model.addAttribute("m1", Long.valueOf(m1));
		model.addAttribute("m2", Long.valueOf(m2));
		return "/mobile/main.html";
	}

	@RequestMapping({ "/ddmain.html" })
	public String ddmain(Model model) {
		TrainEntity te = new TrainEntity();
		ExamUserEntity eue = new ExamUserEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		te.setUsercode(user.getUsercode());
		te.setState(Long.valueOf(1L));
		List<TrainEntity> list1 = this.trainService.findList(te);
		te.setState(Long.valueOf(2L));
		List<TrainEntity> list2 = this.trainService.findList(te);
		eue.setUsercode(user.getUsercode());
		List<ExamUserEntity> list3 = this.examUserService.findList(eue);
		long m1 = list3.stream().filter(x -> (x.getState().longValue() == 0L && x.getStatus().longValue() == 1L))
				.count();
		long m2 = list3.stream().filter(x -> (x.getStatus().longValue() == 2L)).count();
		model.addAttribute("n1", Integer.valueOf(list1.size()));
		model.addAttribute("n2", Integer.valueOf(list2.size()));
		model.addAttribute("m1", Long.valueOf(m1));
		model.addAttribute("m2", Long.valueOf(m2));
		return "/mobile/ddmain.html";
	}

	@RequestMapping({ "/train.html" })
	public String train(Model model) {
		return "/mobile/train.html";
	}

	@RequestMapping({ "/mytrain1.html" })
	public String mytrain1(Model model) {
		TrainEntity te = new TrainEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		te.setUsercode(user.getUsercode());
		te.setState(Long.valueOf(1L));
		List<TrainEntity> list1 = this.trainService.findList(te);
		model.addAttribute("list1", list1);
		return "/mobile/mytrain1.html";
	}

	@RequestMapping({ "/mytrain2.html" })
	public String mytrain2(Model model) {
		TrainEntity te = new TrainEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		te.setUsercode(user.getUsercode());
		te.setState(Long.valueOf(2L));
		List<TrainEntity> list2 = this.trainService.findList(te);
		model.addAttribute("list2", list2);
		return "/mobile/mytrain2.html";
	}

	@RequestMapping({ "/test2.html" })
	public String test1(Model model) {
		ExamUserEntity eue = new ExamUserEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		eue.setUsercode(user.getUsercode());
		eue.setStatus(Long.valueOf(2L));
		List<ExamUserEntity> list = this.examUserService.findList(eue);
		model.addAttribute("list", list);
		return "/mobile/test2.html";
	}

	@RequestMapping({ "/test1.html" })
	public String test2(Model model) {
		ExamUserEntity eue = new ExamUserEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		eue.setUsercode(user.getUsercode());
		eue.setStatus(Long.valueOf(1L));
		eue.setState(Long.valueOf(0L));
		List<ExamUserEntity> list = this.examUserService.findList(eue);
		model.addAttribute("list", list);
		return "/mobile/test1.html";
	}

	@RequestMapping({ "/editpwd.html" })
	public String editpwd() {
		return "/mobile/editpwd.html";
	}

	@RequestMapping({ "/qualify.html" })
	public String qualify(@RequestParam("type") Long type,Model model) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		QualificationEntity exam = new QualificationEntity();
		exam.setUserCode(user.getUsercode());
		exam.setType(type);
		List<QualificationEntity> list = this.qualificationService.findList(exam);
		model.addAttribute("list", list);
		return "/mobile/qualify.html";
	}

	@RequestMapping({ "/userinfo.html" })
	public String userinfo(Model model) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		user = (UserEntity) this.userService.getById(user.getId());
		model.addAttribute("user", user);
		return "/mobile/userinfo.html";
	}

	@RequestMapping({ "/zizhi.html" })
	public String zizhi(Model model) {
		return "/mobile/zizhi.html";
	}

	@RequestMapping(value = { "/login.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag Login(@RequestBody JSONObject jobj, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseFlag res = new ResponseFlag();
		boolean result = false;
		UserEntity user = new UserEntity();
		String username = jobj.getString("username");
		String password = jobj.getString("password");
		BASE64Decoder decoder = new BASE64Decoder();
		String userLoginID = jobj.getString("userLoginID");
		try {
			if (StringUtils.isNotEmpty(userLoginID)) {
				username = userLoginID;
				String key = "noMobile";
				String str1 = jobj.getString("token");
				String stamp = jobj.getString("stamp");
				String sha1Token = SHA1Util.encode(String.valueOf(key) + userLoginID + stamp);
				if (sha1Token.equals(str1)) {
					user.setUsercode(userLoginID);
					List<UserEntity> list = this.userService.findList(user);
					if (list.size() > 0) {
						user = list.get(0);
						res.setSuccess(new Boolean(true));
					} else {
						res.setSuccess(new Boolean(false));
						throw new ServiceException("系统无该用户");
					}
				} else {
					res.setSuccess(new Boolean(false));
					throw new ServiceException("系统无该用户");
				}
			} else {
				password = new String(decoder.decodeBuffer(password), "UTF-8");
				user.setUsercode(username);
				List<UserEntity> list = this.userService.findList(user);
				if (list.size() > 0) {
					user = list.get(0);
					if (user.getLockTime() != null
							&& System.currentTimeMillis() - user.getLockTime().getTime() < 300000L) {
						res.setFlag(Boolean.valueOf(false));
						res.setMessage("该账号已被锁定,请五分钟后重试");
						return res;
					}
					if (!password.equals(user.getPassword())) {
						res.setFlag(Boolean.valueOf(false));
						res.setMessage("帐号或密码错误");
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
						return res;
					}
				} else {
					res.setFlag(Boolean.valueOf(false));
					res.setMessage("帐号或密码错误");
					return res;
				}
			}
			SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsercode(), user.getPassword());
			Subject subject = SecurityUtils.getSubject();
			subject.login((AuthenticationToken) token);
			subject.getSession().setTimeout(259200000L);
			result = true;
			try {
				user.setChance(Long.valueOf(3L));
				this.userService.updateEntity(user);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			res.setMessage("http://www.e-gwm.cn:6085/webapp/mobile/main.html");
			if ("123456".equals(user.getPassword()) && !user.getIsDelete().booleanValue()) {
				result = false;
				if (StringUtils.isNotEmpty(userLoginID)) {
					res.setMessage("http://www.e-gwm.cn:6085/webapp/mobile/editpwd.html");
				} else {
					res.setMessage("abc");
				}
			}
			res.setFlag(Boolean.valueOf(result));
			res.setData(user);
			try {
				Cookie c = new Cookie("JSESSIONID", URLEncoder.encode(request.getSession().getId(), "utf-8"));
				c.setPath("/");
				c.setMaxAge(259200);
				response.addCookie(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			res.setMessage(e.getMessage());
			e.printStackTrace();
		} finally {
			LandingLogEntity landingLogEntity = new LandingLogEntity();
			landingLogEntity.setUsercode(username);
			try {
				landingLogEntity.setIp(NetworkUtil.getIpAddress(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
			landingLogEntity.setResult(Boolean.valueOf(result));
			landingLogEntity.setType(Long.valueOf(1L));
			this.logService.insert(landingLogEntity);
		}
		return res;
	}

	@RequestMapping(value = { "/initToken.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag initToken(@RequestBody JSONObject param) {
		ResponseFlag res = new ResponseFlag();
		try {
			JSONObject tokenObj = new JSONObject();
			String usercode = param.getString("code");
			UserEntity user = new UserEntity();
			user.setUsercode(usercode);
			List<UserEntity> list = this.userService.findList(user);
			if (list.size() > 0) {
				user = list.get(0);
				String key = "zizhi";
				long stamp = System.currentTimeMillis();
				String token = SHA1Util.encode(String.valueOf(key) + usercode + stamp);
				user.setToken(token);
				this.userService.updateEntity(user);
				tokenObj.put("token", token);
				tokenObj.put("stamp", Long.valueOf(stamp));
				tokenObj.put("loginId", usercode);
				res.setSuccess(Boolean.valueOf(true));
				res.setData(tokenObj);
			} else {
				res.setSuccess(Boolean.valueOf(false));
			}
		} catch (Exception e) {
			res.setSuccess(Boolean.valueOf(false));
		}
		return res;
	}

	@RequestMapping(value = { "/ddlogin.do" }, method = { RequestMethod.GET })
	public ModelAndView ddLogin(@RequestParam("code") String code, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		UserEntity user = new UserEntity();
		boolean flag = false;
		String userLoginID = "unknown";
		UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		if (u != null) {
			mav.setViewName("redirect:/mobile/ddmain.html");
			return mav;
		}
		try {
			if (StringUtils.isNotEmpty(code)) {
				DefaultDingTalkClient defaultDingTalkClient = new DefaultDingTalkClient(
						"https://oapi.dingtalk.com/user/getuserinfo");
				OapiUserGetuserinfoRequest req = new OapiUserGetuserinfoRequest();
				req.setCode(code);
				req.setHttpMethod("GET");
				String accessToken = getAccessToken();
				OapiUserGetuserinfoResponse resp = defaultDingTalkClient.execute(req, accessToken);
				String errorcode = resp.getErrorCode();
				if (StringUtils.isNotEmpty(errorcode) && "0".equals(errorcode)) {
					userLoginID = resp.getUserid();
					user.setUsercode(userLoginID);
					List<UserEntity> list = this.userService.findList(user);
					if (list.size() > 0) {
						user = list.get(0);
						flag = true;
						mav.setViewName("redirect:/mobile/ddmain.html");
						try {
							Cookie c = new Cookie("JSESSIONID",
									URLEncoder.encode(request.getSession().getId(), "utf-8"));
							c.setPath("/");
							c.setMaxAge(259200);
							response.addCookie(c);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						mav.setViewName("redirect:/mobile/tishi.html");
						return mav;
					}
				} else {
					mav.setViewName("redirect:/mobile/tishi.html");
					return mav;
				}
			}
			SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			UsernamePasswordToken tokenn = new UsernamePasswordToken(user.getUsercode(), user.getPassword());
			Subject subject = SecurityUtils.getSubject();
			subject.login((AuthenticationToken) tokenn);
			subject.getSession().setTimeout(259200000L);
			try {
				user.setChance(Long.valueOf(3L));
				user.setToken("");
				this.userService.updateEntity(user);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			LandingLogEntity landingLogEntity = new LandingLogEntity();
			landingLogEntity.setUsercode(userLoginID);
			try {
				landingLogEntity.setIp(NetworkUtil.getIpAddress(request));
			} catch (IOException e) {
				e.printStackTrace();
			}
			landingLogEntity.setResult(Boolean.valueOf(flag));
			landingLogEntity.setType(Long.valueOf(1L));
			this.logService.insert(landingLogEntity);
		}
		return mav;
	}

	private String getAccessToken() {
		String accessToken = null;
		DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey("dingazgtxl0vk2p1bafj");
		request.setAppsecret("1prZT0KWp2MlClK3ajZTvvmOyxuJXkaH-7a4WyBDYaaljDXw8UwWjUhLMfHhAJKU");
		request.setHttpMethod("GET");

		try {
			OapiGettokenResponse response = client.execute(request);
			accessToken = response.getAccessToken();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return accessToken;
	}

	@RequestMapping({ "/wodekaohe.html" })
	public String wodekaohe(Model model) {
		ExamResultEntity te = new ExamResultEntity();
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		te.setUsercode(user.getUsercode());
		List<ExamResultEntity> list1 = this.examResultService.findList(te);
		model.addAttribute("list", list1);
		return "/mobile/wodekaohe.html";
	}

	@RequestMapping({ "/kaohe.html" })
	public String kaohe(Model model) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		TrainEntity q = new TrainEntity();
		q.setDepartmentId(user.getDeptId());
		q.setRoleId(user.getId());
		List<UserEntity> users = this.trainService.queryUserbypostion(q);
		model.addAttribute("users", users);
		return "/mobile/kaohe.html";
	}
}
