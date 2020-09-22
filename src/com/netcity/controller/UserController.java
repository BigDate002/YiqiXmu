package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONObject;
import com.netcity.aspect.SystemLog;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.StaffEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.UserService;
import com.netcity.shiro.MyShiroRealm;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.ResponseFlag;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "/user" })
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	MyShiroRealm myShiroRealm;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "user/index";
	}

	@RequestMapping({ "/index1.html" })
	public String index1() {
		return "user/index1";
	}

	@RequestMapping({ "/setpwd.html" })
	public String setpwd() {
		return "user/setpwd";
	}

	@RequiresPermissions({ "user:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			UserEntity usr) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if (!"admin".equals(user.getUsercode())) {
				usr.setRoleId(user.getId());
			}
			result = this.userService.listByPage(usr, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "user:resetPassword" })
	@RequestMapping(value = { "/resetPassword.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag resetPassword(@RequestParam("ids") String ids) {
		ResponseFlag result = new ResponseFlag();
		try {
			this.userService.updatePasswordReset(ids);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage("保存失败");
		}
		return result;
	}

	@RequiresPermissions({ "user:updateRole" })
	@RequestMapping(value = { "/updateRole.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag updateRole(@RequestBody UserEntity usr) {
		ResponseFlag result = new ResponseFlag();
		try {
			this.userService.updateEntity(usr);
			this.myShiroRealm.clearCachedAuthorizationInfoByRoleId(usr.getRoleId());
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage("保存失败");
		}
		return result;
	}

	@SystemLog(action = "修改密码")
	@RequestMapping(value = { "/updatePassword.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag updatePassword(@RequestBody JSONObject jobj) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			usr = (UserEntity) this.userService.getById(usr.getId());
			String oldpwd = jobj.getString("oldpwd");
			String newpass = jobj.getString("newpass");
			if (!usr.getPassword().equals(oldpwd)) {
				throw new ServiceException("旧密码错误,保存失败");
			}
			usr.setPassword(newpass);
			usr.setIsDelete(Boolean.valueOf(true));
			this.userService.updateEntity(usr);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping({ "/train1.html" })
	public ModelAndView mytrain1() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("mytrain/index");
		mv.addObject("state", Integer.valueOf(1));
		return mv;
	}

	@RequestMapping({ "/train2.html" })
	public ModelAndView mytrain2() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("mytrain/index");
		mv.addObject("state", Integer.valueOf(2));
		return mv;
	}

	@RequestMapping({ "/myinfo.html" })
	public ModelAndView myinfo(Model model) {
		ModelAndView mv = new ModelAndView();
		UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", this.userService.getById(usr.getId()));
		mv.setViewName("user/myinfo");
		return mv;
	}

	@RequestMapping({ "/downloadYC2GWTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "工学转正导入模板.xlsx";
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			ExportParams params = new ExportParams("导入模板", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, StaffEntity.class, new ArrayList<StaffEntity>());
			workbook.getSheetAt(0).setColumnHidden(6, true);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions({ "user:importData" })
	@SystemLog(action = "工学转正批量导入")
	@RequestMapping(value = { "/importYC2GW.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<StaffEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), StaffEntity.class, params);
			for (StaffEntity s : list) {
				s.setGwUsercode(s.getGwUsercode().trim());
				s.setGwUsername(s.getGwUsername().trim());
				s.setYcUsercode(s.getYcUsercode().trim());
				s.setYcUsername(s.getYcUsername().trim());
				if (!s.getYcUsername().equals(s.getGwUsername())) {
					throw new ServiceException("工学工号" + s.getYcUsercode() + "姓名不匹配");
				}
			}
			this.userService.updateUserInfo(list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}
}