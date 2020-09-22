package com.netcity.controller;

import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.entity.NewsEntity;
import com.netcity.module.entity.NewsUserEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.ColumnService;
import com.netcity.module.service.NewsService;
import com.netcity.module.service.NewsUserService;

import java.util.*;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	@Autowired
	ColumnService columnService;
	@Autowired
	NewsService newsServie;
	@Autowired
	NewsUserService newsUserService;

	@RequestMapping({ "/login.html" })
	public String index() {
		return "index";
	}

	@RequestMapping({ "/index.html" })
	public String home(Model mv) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		Map<String, Object> param = new HashMap<>();
		param.put("createUser", user.getUsercode());
		List<NewsEntity> news = this.newsServie.findListByCond(param);
		mv.addAttribute("news", news);
		return "home";
	}

	@RequestMapping({ "/detail_{id}.html" })
	public String detail(@PathVariable("id") Long id, Model mv) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		NewsEntity qr = new NewsEntity();
		qr.setCreateUser(user.getUsercode());
		qr.setId(id);
		NewsEntity detail = new NewsEntity();
		try {
			detail = this.newsServie.findList(qr).get(0);
			if (!detail.getIsRead().booleanValue()) {
				NewsUserEntity nue = new NewsUserEntity();
				nue.setUsercode(user.getUsercode());
				nue.setNewsId(detail.getId());
				this.newsUserService.insert(nue);
			}
		} catch (Exception exception) {
		}

		mv.addAttribute("detail", detail);
		return "detail";
	}

	public static List<String> menus = new ArrayList<>();

	static {
		menus.add("登陆日志");
		menus.add("系统设置");
		menus.add("部门管理");
		menus.add("角色管理");
		menus.add("用户管理");
		menus.add("个人中心");
		menus.add("密码修改");
		menus.add("考核管理");
	}

	@RequestMapping({ "/menulist.do" })
	@ResponseBody
	private List<ColumnEntity> menulist() {
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			Map<String, Object> map = new HashMap<>();
			if (user.getRoleId() == null) {
				map.put("roleId", 0);
			} else {
				map.put("roleId", user.getRoleId());
			}
			List<ColumnEntity> list1 = this.columnService.findListByCond(map);
			//TODO
//			if ("admin".equals(user.getUsercode())) {
//				list1.removeIf(e -> !menus.contains(e.getName()));
//			} else {
//				list1.removeIf(e -> !((e.getVisible().longValue() != 1L || e.getChecked().booleanValue())
//						&& e.getSort().longValue() != 0L));
//			}
			List<ColumnEntity> list = new ArrayList<>();
			ColumnEntity pre = null;
			for (ColumnEntity ce : list1) {
				if (ce.getParentId().longValue() == 0L) {
					list.add(ce);
					pre = ce;
					continue;
				}
				if (pre.getChildrens() == null) {
					pre.setChildrens(new ArrayList<ColumnEntity>());
				}
				pre.getChildrens().add(ce);
			}
			//新增三级
//			for (ColumnEntity ce : list1) {
//				if (ce.getParentId().longValue() == 0L) {
//					list.add(ce);
//					pre = ce;
//					continue;
//				}
//				if (pre.getChildrens() == null) {
//					pre.setChildrens(new ArrayList<ColumnEntity>());
//				}
//				if (ce.getParentId().equals(pre.getId())){
//					pre.getChildrens().add(ce);
//				}else {
//					pre.getChildrens().stream().filter(o-> o.getId().equals(ce.getParentId())).findFirst().ifPresent(
//							p->{
//								if (p.getChildrens() == null) {
//									p.setChildrens(new ArrayList<ColumnEntity>());
//								}
//								p.getChildrens().add(ce);
//							}
//					);
//				}
//			}

			Map<String, Object> info = this.columnService.getPersonInfo(user);
			for (ColumnEntity ce : list) {
				if (ce.getName().equals("个人中心")) {
					for (ColumnEntity col : ce.getChildrens()) {
						if (info.containsKey(col.getName())) {
							col.setDescript(info.get(col.getName()).toString());
						}
					}
					break;
				}
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}
