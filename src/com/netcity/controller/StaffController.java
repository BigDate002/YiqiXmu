package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.UserService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.QueryResult;
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

@Controller
@RequestMapping({ "/staff" })
public class StaffController {
	@Autowired
	UserService userService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "staff/index";
	}

	@RequiresPermissions({ "staff:query" })
	@SystemLog(action = "人员分页查询")
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
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "staff:create" })
	@SystemLog(action = "添加人员")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody UserEntity usr) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			UserEntity query = new UserEntity();
			query.setUsercode(usr.getUsercode());
			List<UserEntity> list = this.userService.findList(query);
			if (CollectionUtils.isNotEmpty(list)) {
				res.setFlag(ResponseFlag.Failed);
				res.setMessage(String.valueOf(usr.getUsercode()) + "编码已存在");
				return res;
			}
			usr.setCreateUser(user.getUsercode());
			this.userService.insert(usr);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "staff:update" })
	@SystemLog(action = "编辑人员")
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody UserEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			UserEntity query = new UserEntity();
			query.setUsercode(row.getUsercode());
			List<UserEntity> list = this.userService.findList(query);
			if (CollectionUtils.isNotEmpty(list)
					&& ((UserEntity) list.get(0)).getId().intValue() != row.getId().intValue()) {
				res.setFlag(ResponseFlag.Failed);
				res.setMessage(String.valueOf(row.getUsercode()) + "编码已存在");
				return res;
			}
			row.setUpdateUser(user.getUsercode());
			this.userService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "staff:delete" })
	@SystemLog(action = "删除人员")
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.userService.deleteByIds(ids);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "staff:query" })
	@RequestMapping(value = { "/queryList.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryList(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			UserEntity usr) {
		QueryResult result = new QueryResult();
		try {
			List<UserEntity> list = this.userService.findList(usr);
			result.setFlag(QueryResult.Success);
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}

	@RequiresPermissions({ "staff:exportData" })
	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(UserEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "导出数据.xlsx";
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if (!"admin".equals(user.getUsercode())) {
				query.setRoleId(user.getId());
			}
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
			List<UserEntity> list = this.userService.findList(query);
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, UserEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "人员导入模板.xlsx";
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
			Workbook workbook = ExcelExportUtil.exportExcel(params, UserEntity.class, new ArrayList<UserEntity>());
			workbook.getSheetAt(0).setColumnHidden(6, true);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions({ "staff:importData" })
	@SystemLog(action = "人员批量导入")
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<UserEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), UserEntity.class, params);
			this.userService.insertImport(list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@RequiresPermissions({ "staff:grant" })
	@SystemLog(action = "启用人员")
	@RequestMapping(value = { "/grant.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag grant(@RequestBody UserEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			row.setUserState(Long.valueOf(1L));
			this.userService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "staff:grant" })
	@SystemLog(action = "禁用人员")
	@RequestMapping(value = { "/grant1.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag grant1(@RequestBody UserEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			row.setUserState(Long.valueOf(0L));
			this.userService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}


	@RequiresPermissions({ "staff:del" })
	@SystemLog(action = "删除人员")
	@RequestMapping(value = { "/del.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag del(@RequestBody UserEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.userService.deleteByIds(row.getIds());
			res.setFlag(ResponseFlag.Success);
			res.setMessage("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

}
