package com.netcity.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netcity.module.entity.PositionEntity;
import com.netcity.module.service.NewsService;
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

import com.netcity.aspect.SystemLog;
import com.netcity.exception.ServiceException;
import com.netcity.module.entity.CourseEntity;
import com.netcity.module.entity.CoursePositionEntity;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.CoursePositionService;
import com.netcity.module.service.CourseService;
import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.PositionService;
import com.netcity.util.ExcelHelper;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.QueryResult;
import com.netcity.util.ResponseFlag;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;

@Controller
@RequestMapping({ "/course" })
public class CourseController {

	@Autowired
	CourseService courseService;
	@Autowired
	CoursePositionService coursePositionService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "course/index";
	}

	@Autowired
	PositionService positionService;
	@Autowired
	DepartmentService departmentService;

	@RequiresPermissions({ "course:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			CourseEntity course) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			course.setRoleId(user.getId());
			result = this.courseService.listByPage(course, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@SystemLog(action = "新增课程")
	@RequiresPermissions({ "course:create" })
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody CourseEntity course) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if (course.getType().longValue() == 1L) {
				CourseEntity cq = new CourseEntity();
				cq.setName(course.getName());
				cq.setWorkShopId(course.getWorkShopId());
				List<CourseEntity> cl = this.courseService.findList(cq);
				if (cl.size() > 0)
					throw new ServiceException(String.format("部门%s科室/车间%s课程%s已存在",
							new Object[] { ((CourseEntity) cl.get(0)).getDepartment(),
									((CourseEntity) cl.get(0)).getWorkShop(), course.getName() }));
			}
			if (course.getWorkShopId() == null) {
				course.setWorkShopId(user.getWorkShopId());
			}
			course.setCreateUser(user.getUsercode());
			this.courseService.insertRow(course);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "编辑课程")
	@RequiresPermissions({ "course:update" })
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody CourseEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			row.setUpdateUser(user.getUsercode());
			this.courseService.updateRow(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "禁用课程")
	@RequiresPermissions({ "course:delete" })
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.courseService.deleteByIds(ids);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "启用课程")
	@RequiresPermissions({ "course:delete" })
	@RequestMapping(value = { "/grant.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag enable(@RequestParam("id") Long id) {
		ResponseFlag res = new ResponseFlag();
		try {
			CourseEntity ce = new CourseEntity();
			ce.setId(id);
			ce.setState(Long.valueOf(1L));
			this.courseService.updateEntity(ce);
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
	public QueryResult queryList(CourseEntity course) {
		QueryResult result = new QueryResult();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			course.setRoleId(user.getId());
			course.setState(Long.valueOf(1L));
			List<CourseEntity> list = this.courseService.findList(course);
			result.setFlag(QueryResult.Success);
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}

	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(CourseEntity query, HttpServletRequest request, HttpServletResponse response) {
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
			List<CourseEntity> list = this.courseService.findExportList(query);
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, CourseEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "课程导入模板.xlsx";
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
			ExportParams params = new ExportParams("导入模板[多个岗位用|隔开]", "课程", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, CourseEntity.class, new ArrayList<CourseEntity>());
			String[] textlist = { "通用培训", "资质培训" };
			ExcelHelper.setXSSFValidation(workbook.getSheetAt(0), textlist, 2, 500, 3, 3);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SystemLog(action = "批量导入课程")
	@RequiresPermissions({ "course:importData" })
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<CourseEntity> list2 = ExcelImportUtil.importExcel(file.getInputStream(), CourseEntity.class, params);
			List<CourseEntity> list = new ArrayList<>();
			for (CourseEntity ce : list2) {
				if (ce.getName() == null) {
					break;
				}
				list.add(ce);
			}
			Map<String, DepartmentEntity> dept = this.departmentService.findDept(null);
			for (CourseEntity ce : list) {
				if (ce.getType().longValue() == 0L) {
					ce.setWorkShopId(user.getWorkShopId());
					continue;
				}
				String key = String.valueOf(ce.getDepartment()) + "-" + ce.getWorkShop();
				Long id = Long.valueOf(0L);
				DepartmentEntity dp = dept.get(key);
				if (dp == null) {
					throw new ServiceException(
							String.format("部门%s科室/车间%s不存在", new Object[] { ce.getDepartment(), ce.getWorkShop() }));
				}
				id = Long.valueOf(dp.getCode());
				ce.setWorkShopId(id);
				ce.setCreateUser(user.getUsercode());
				CourseEntity cq = new CourseEntity();
				cq.setName(ce.getName());
				cq.setWorkShopId(id);
				List<CourseEntity> clist = this.courseService.findList(cq);
				if (clist.size() > 0) {
					if (ce.getType().longValue() == 0L) {
						throw new ServiceException(String.format("部门%s科室/车间%s课程%s已存在",
								new Object[] { ce.getDepartment(), ce.getWorkShop(), ce.getName() }));
					}
					ce.setId(clist.get(0).getId());
				}
			}
			this.courseService.insertImport(list, user);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("导入失败:" + e.getMessage());
		}
		return res;
	}

	@SystemLog(action = "解除课程与岗位关系")
	@RequiresPermissions({ "course:delposition" })
	@RequestMapping(value = { "/deleteById2.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag deleteById2(@RequestBody List<CoursePositionEntity> row) {
		ResponseFlag res = new ResponseFlag();
		try {
			if (row != null && row.size() > 0) {
				this.coursePositionService.deleteById2(row);
			}else {
				throw new ServiceException("请选择数据操作");
			}
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "删除岗位")
	@RequestMapping(value = { "/deleteGW.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag deleteGW(@RequestBody PositionEntity de) {
		ResponseFlag res = new ResponseFlag();
		try {
//			UserEntity u = (UserEntity) SecurityUtils.getSubject().getPrincipal();
//			if (!"admin".equals(u.getUsercode())) {
//				throw new ServiceException("只有管理员能使用该功能");
//			}
//			de.setState(Long.valueOf(1L));
			this.positionService.deleteById(de.getId());

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
