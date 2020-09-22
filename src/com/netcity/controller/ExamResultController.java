package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONObject;
import com.netcity.aspect.SystemLog;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.ExamResultEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.ExamResultService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.ResponseFlag;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping({ "/examresult" })
public class ExamResultController {
	@Autowired
	ExamResultService examResultService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "examresult/index";
	}

	@RequestMapping({ "/mime.html" })
	public String mime() {
		return "examresult/mime";
	}

	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			ExamResultEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setUsercode(usr.getUsercode());
			result = this.examResultService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("查询失败");
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping(value = { "/queryPageI.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPageI(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			ExamResultEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(usr.getId());
			result = this.examResultService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("查询失败");
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "examResult:create" })
	@SystemLog(action = "添加考核结果")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody ExamResultEntity usr) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ExamResultEntity query = new ExamResultEntity();
			query.setYear(usr.getYear());
			query.setMonth(usr.getMonth());
			query.setUsercode(usr.getUsercode());
			List<ExamResultEntity> list = this.examResultService.findList(query);
			if (CollectionUtils.isNotEmpty(list)) {
				res.setFlag(ResponseFlag.Failed);
				res.setMessage(String.valueOf(usr.getUsercode()) + "本月考核结果存在请勿重复添加");
				return res;
			}
			usr.setResult(usr.getResult().toUpperCase());
			usr.setCreateUser(user.getUsercode());
			this.examResultService.insert(usr);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "examResult:create" })
	@SystemLog(action = "批量添加考核结果")
	@RequestMapping(value = { "/tianjia.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag tianjia(@RequestBody JSONObject param) {
		ResponseFlag res = new ResponseFlag();
		try {
			String date = param.getString("beginDate");
			param.remove("beginDate");
			long year = Long.valueOf(date.split("年")[0]).longValue();
			long month = Long.valueOf(date.split("年")[1].split("月")[0]).longValue();
			List<ExamResultEntity> list = new ArrayList<>();
			for (String key : param.keySet()) {
				ExamResultEntity query = new ExamResultEntity();
				query.setYear(Long.valueOf(year));
				query.setMonth(Long.valueOf(month));
				UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
				query.setUsercode(key);
				List<ExamResultEntity> li = this.examResultService.findList(query);
				if (CollectionUtils.isNotEmpty(li)) {
					res.setFlag(ResponseFlag.Failed);
					res.setMessage(String.valueOf(key) + "本月考核结果存在请勿重复添加");
					return res;
				}
				query.setResult(param.getString(key).toUpperCase());
				query.setCreateUser(user.getUsercode());
				list.add(query);
			}
			this.examResultService.insertBatchOnce(list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "examResult:edit" })
	@SystemLog(action = "修改考核结果")
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody ExamResultEntity usr) {
		ResponseFlag res = new ResponseFlag();
		try {
			usr.setResult(usr.getResult().toUpperCase());
			this.examResultService.updateEntity(usr);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "考核结果导入模板.xlsx";
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
			ExportParams params = new ExportParams("导入模板", "考核结果", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, ExamResultEntity.class,
					new ArrayList<ExamResultEntity>());
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(ExamResultEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "导出数据.xlsx";
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
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
			List<ExamResultEntity> list = this.examResultService.findList(query);
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, ExamResultEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SystemLog(action = "批量导入考核结果")
	@RequiresPermissions({ "examResult:importData" })
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<ExamResultEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), ExamResultEntity.class,
					params);
			for (ExamResultEntity e : list) {
				if (StringUtils.isEmptyOrWhitespace(e.getUsercode())) {
					throw new ServiceException("工号不能为空!");
				}
				if (e.getYear() == null) {
					throw new ServiceException("年份不能为空!");
				}
				if (e.getMonth() == null) {
					throw new ServiceException("月份不能为空!");
				}
				if (StringUtils.isEmptyOrWhitespace(e.getResult())) {
					throw new ServiceException("考核结果不能为空!");
				}
				e.setResult(e.getResult().toUpperCase());
				e.setCreateUser(user.getUsercode());
			}
			this.examResultService.insertBatchOnce(list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("导入失败:" + e.getMessage());
		}
		return res;
	}
}