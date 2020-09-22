package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import com.netcity.aspect.SystemLog;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.DictEntity;
import com.netcity.module.entity.PositionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.DictService;
import com.netcity.module.service.PositionService;
import com.netcity.module.service.UserService;
import com.netcity.util.ExcelHelper;
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
import org.apache.commons.lang3.StringUtils;
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
@RequestMapping({ "/position" })
public class PositionController {
	@Autowired
	PositionService positionService;
	@Autowired
	DepartmentService deptService;
	@Autowired
	DictService dictService;
	@Autowired
	UserService userService;
	@RequestMapping({ "/index.html" })
	public String index() {
		return "position/index";
	}

	@RequiresPermissions({ "position:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			PositionEntity usr) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			usr.setRoleId(user.getId());
			result = this.positionService.listByPage(usr, pageNum, pageSize);
			if (CollectionUtils.isNotEmpty(result.getData())){
				List<PositionEntity> list = (List<PositionEntity>) result.getData();
				for (PositionEntity positionEntity : list){
					//查询字典
					DictEntity dictEntity = dictService.queryDictNameByDictValue("sys_post_category",positionEntity.getPostCategory()+"");
					if (null != dictEntity){
						positionEntity.setPostCategoryStr(dictEntity.getDictLabel());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping(value = { "/queryPage1.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage1(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			PositionEntity usr) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			usr.setRoleId(user.getId());
			usr.setState(Long.valueOf(1L));
			result = this.positionService.listByPage(usr, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "position:create" })
	@SystemLog(action = "添加岗位")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody PositionEntity pos) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			pos.setCreateUser(usr.getUsercode());
			PositionEntity q = new PositionEntity();
			q.setWorkGroupId(Long.valueOf((pos.getWorkGroupId() == null) ? 0L : pos.getWorkGroupId().longValue()));
			q.setDeptId(pos.getDeptId());
			List<PositionEntity> lis = this.positionService.findList(q);
			if (CollectionUtils.isNotEmpty(lis)) {
				res.setFlag(ResponseFlag.Failed);
				res.setMessage("岗位名称重复");
			} else {
				this.positionService.insert(pos);
				res.setFlag(ResponseFlag.Success);
				res.setMessage("保存成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "position:update" })
	@SystemLog(action = "编辑岗位")
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody PositionEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			row.setUpdateUser(u.getUsercode());
			this.positionService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "position:delete" })
	@SystemLog(action = "禁用岗位")
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.positionService.deleteByIds(ids);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequestMapping(value = { "/queryList.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryList(PositionEntity post) {
		QueryResult result = new QueryResult();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			post.setRoleId(user.getId());
			post.setState(Long.valueOf(1L));
			List<PositionEntity> list = this.positionService.findList(post);



			result.setFlag(QueryResult.Success);
			result.setData(list);
			result.setCode(Long.valueOf(0L));
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}

	@RequiresPermissions({ "position:grant" })
	@SystemLog(action = "禁用岗位")
	@RequestMapping(value = { "/grant.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag grant(@RequestBody PositionEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			//如果有在职的,不能禁用
			UserEntity usr = new UserEntity();
			usr.setDepartmentId(row.getDeptId());
			usr.setUserState(1L);
			LayuiPageInfo result = this.userService.listByPage(usr, 0, 10);
			if (result.getCount() > 0){
				res.setFlag(ResponseFlag.Failed);
				res.setMessage("该组织下存在在职人员，不允许禁用操作!");
				return res;
			}
			row.setUpdateUser(u.getUsercode());
			row.setState(Long.valueOf(0L));
			this.positionService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "position:grant" })
	@SystemLog(action = "启用岗位")
	@RequestMapping(value = { "/grant1.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag grant1(@RequestBody PositionEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			row.setUpdateUser(u.getUsercode());
			row.setState(Long.valueOf(1L));
			this.positionService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(PositionEntity query, HttpServletRequest request, HttpServletResponse response) {
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
			List<PositionEntity> list = this.positionService.findList(query);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			ExportParams params = new ExportParams("导入模板", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, PositionEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "岗位导入模板.xlsx";
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
			Workbook workbook = ExcelExportUtil.exportExcel(params, PositionEntity.class,
					new ArrayList<PositionEntity>());
			String[] textlist = { "普通岗", "关键岗" };
			ExcelHelper.setXSSFValidation(workbook.getSheetAt(0), textlist, 2, 500, 1, 1);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions({ "position:importData" })
	@SystemLog(action = "批量导入岗位")
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<PositionEntity> list2 = ExcelImportUtil.importExcel(file.getInputStream(), PositionEntity.class,
					params);
			List<PositionEntity> list = new ArrayList<>();
			for (PositionEntity ce : list2) {
				if (ce.getDepartment() == null) {
					break;
				}
				ce.setCreateUser(user.getUsercode());
				list.add(ce);
			}
			for (PositionEntity pos : list) {
				Long id = this.positionService.findPositionByParam(pos);
				if (id == null || id.longValue() == 0L) {
					throw new ServiceException(
							String.format("部门%s科室/车间%s不存在", new Object[] { pos.getDepartment(), pos.getWorkShop() }));
				}
				if (!StringUtils.isBlank(pos.getWorkGroup())) {
					DepartmentEntity dept = new DepartmentEntity();
					dept.setParentId(id.toString());
					dept.setName(pos.getWorkGroup());
					List<DepartmentEntity> dl = this.deptService.findList(dept);
					if (CollectionUtils.isEmpty(dl)) {
						throw new ServiceException(String.format("部门%s科室/车间%s下的班组%s不存在",
								new Object[] { pos.getDepartment(), pos.getWorkShop(), pos.getWorkGroup() }));
					}
					pos.setWorkGroupId(Long.valueOf(((DepartmentEntity) dl.get(0)).getCode()));
				}
				pos.setDeptId(id);
			}
			this.positionService.insertImportList(list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (ExcelImportException e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("岗位类型输入错误,应为关键岗或普通岗");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}



}
