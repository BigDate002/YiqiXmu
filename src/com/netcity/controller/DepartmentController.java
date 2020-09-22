package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.netcity.aspect.SystemLog;
import com.netcity.base.entity.LayTree;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.RoleDepartmentEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.CodeService;
import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.UserService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.QueryResult;
import com.netcity.util.ResponseFlag;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
@RequestMapping({ "/department" })
public class DepartmentController {
	@Autowired
	DepartmentService departmentService;
	@Autowired
	CodeService codeService;
	@Autowired
	UserService userService;
	@RequestMapping({ "/index.html" })
	public String index() {
		return "department/index";
	}

	@RequiresPermissions({ "department:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			DepartmentEntity Department) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if (!"admin".equals(u.getUsercode())) {
				Department.setRoleId(u.getId());
			}
			result = this.departmentService.listByPage(Department, pageNum, pageSize);
		} catch (Exception e) {
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping(value = { "/queryClass.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryClass() {
		QueryResult result = new QueryResult();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			DepartmentEntity query = new DepartmentEntity();
			query.setRoleId(user.getId());
			query.setLevel("三级");
			List<DepartmentEntity> list = this.departmentService.findList(query);
			result.setData(list);
			result.setFlag(QueryResult.Success);
			result.setMessage("查询成功");
		} catch (Exception e) {
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}

	@RequestMapping(value = { "/queryClass2.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryClass2(DepartmentEntity query) {
		QueryResult result = new QueryResult();
		try {
			query.setState(Long.valueOf(0L));
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
			List<DepartmentEntity> list = this.departmentService.findList(query);
			if ("二级".equals(query.getLevel()) && list.size() == 0) {
				query.setCode(user.getDepartmentId().toString());
				query.setRoleId(null);
				list = this.departmentService.findList(query);
			}
			result.setData(list);
			result.setFlag(QueryResult.Success);
			result.setMessage("查询成功");
		} catch (Exception e) {
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}

	@RequestMapping(value = { "/queryDept.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult selectByRoleId(@RequestParam("id") Long id) {
		QueryResult result = new QueryResult();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			UserEntity query = new UserEntity();
			if (!"admin".equals(user.getUsercode())) {
				query.setId(user.getId());
			}
			query.setRoleId(id);
			List<DepartmentEntity> list = this.departmentService.selectByRoleId(query);
			result.setData(list);
			result.setFlag(QueryResult.Success);
			result.setMessage("查询成功");
		} catch (Exception e) {
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}

	@RequiresPermissions({ "department:create" })
	@SystemLog(action = "添加组织结构")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody DepartmentEntity dept) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			List<DepartmentEntity> lis = this.departmentService.findList(dept);
			if (CollectionUtils.isNotEmpty(lis)) {
				throw new ServiceException(String.format("组织名称重复%s", new Object[] { dept.getName() }));
			}
			Integer code = Integer.valueOf(this.codeService.createNextNo("DEPARTMENT"));
			dept.setCode(code.toString());
			dept.setState(Long.valueOf(0L));
			dept.setCreateUser(user.getUsercode());
			RoleDepartmentEntity roleDept = new RoleDepartmentEntity();
			roleDept.setRoleId(user.getId());
			roleDept.setDeptId(Long.valueOf(code.longValue()));
			this.departmentService.insertDept(dept, roleDept);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@SystemLog(action = "编辑组织结构")
	@RequiresPermissions({ "department:update" })
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody DepartmentEntity dept) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.departmentService.updateEntity(dept);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "启用组织结构")
	@RequiresPermissions({ "department:delete" })
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestBody DepartmentEntity de) {
		ResponseFlag res = new ResponseFlag();
		try {
			de.setState(Long.valueOf(0L));
			this.departmentService.updateByIds(de);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "禁用组织结构")
	@RequiresPermissions({ "department:delete" })
	@RequestMapping(value = { "/delete1.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete1(@RequestBody DepartmentEntity de) {
		ResponseFlag res = new ResponseFlag();
		try {
			//校验是否有在职人员
			//如果有在职的,不能禁用
			UserEntity usr = new UserEntity();
			usr.setDepartmentId(Long.parseLong(de.getIds()));
			usr.setUserState(1L);
			LayuiPageInfo result = this.userService.listByPage(usr, 0, 10);
			if (result.getCount() > 0){
				res.setFlag(ResponseFlag.Failed);
				res.setMessage("该组织下存在在职人员，不允许禁用操作!");
				return res;
			}
			de.setState(Long.valueOf(1L));
			this.departmentService.updateByIds(de);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequestMapping(value = { "/query.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult query() {
		QueryResult result = new QueryResult();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			List<LayTree> list = this.departmentService.findDepartments(user);
			List<LayTree> list1 = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(list)) {
				list.forEach(y -> y.setChildren(
						list.stream().filter(x -> x.getPid().longValue() == y.getId()).collect(Collectors.toList())));
				list1 = list.stream().filter(x -> x.getLevel().equals(((LayTree) list.get(0)).getLevel()))
						.collect(Collectors.toList());
			}
			result.setFlag(QueryResult.Success);
			result.setData(list1);
		} catch (Exception e) {
			result.setFlag(QueryResult.Failed);
		}
		return result;
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "组织结构导入模板.xlsx";
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
			ExportParams params = new ExportParams(null, "模板", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, DepartmentEntity.class,
					new ArrayList<DepartmentEntity>());
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions({ "department:importData" })
	@SystemLog(action = "组织结构批量导入")
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file, @RequestParam("id") String id) {
		ResponseFlag res = new ResponseFlag();
		try {
			if ("0".equals(id)) {
				throw new ServiceException("请选择组织结构");
			}
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(0);
			List<DepartmentEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), DepartmentEntity.class,
					params);
			DepartmentEntity query = new DepartmentEntity();
			query.setCode(id);
			DepartmentEntity dept = this.departmentService.findList(query).get(0);
			String[] lvs = { "一级", "二级", "三级", "四级" };
			List<String> ls = Arrays.asList(lvs);
			int index = ls.indexOf(dept.getLevel());
			if (index > 2) {
				throw new ServiceException("班组下不能添加组织");
			}
			for (DepartmentEntity ce : list) {
				ce.setParentId(id);
				if (this.departmentService.findList(ce).size() > 0) {
					throw new ServiceException(String.format("组织名称重复%s", new Object[] { ce.getName() }));
				}
				Integer code = Integer.valueOf(this.codeService.createNextNo("DEPARTMENT"));
				ce.setCode(code.toString());
				ce.setLevel(lvs[index + 1]);
				ce.setCreateUser(user.getUsercode());
			}
			for (DepartmentEntity ent : list) {
				RoleDepartmentEntity roleDept = new RoleDepartmentEntity();
				roleDept.setRoleId(user.getId());
				roleDept.setDeptId(Long.valueOf(ent.getCode()));
				this.departmentService.insertDept(ent, roleDept);
			}
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("导入失败:" + e.getMessage());
		}
		return res;
	}

	@SystemLog(action = "删除组织结构")
	@RequestMapping(value = { "/deleteReal.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag deleteReal(@RequestBody DepartmentEntity de) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if (!"admin".equals(u.getUsercode())) {
				throw new ServiceException("只有管理员能使用该功能");
			}
			de.setState(Long.valueOf(1L));
			this.departmentService.deleteByIds(de.getIds());
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}
}
