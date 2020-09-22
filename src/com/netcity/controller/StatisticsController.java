package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONObject;
import com.netcity.aspect.SystemLog;
import com.netcity.base.entity.LayTree;
import com.netcity.module.entity.DepartmentEntity;
import com.netcity.module.entity.JobReserveVO;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.entity.WorkerReserveVO;
import com.netcity.module.service.DepartmentService;
import com.netcity.module.service.PositionService;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/chart" })
public class StatisticsController {
	@Autowired
	PositionService positionService;
	@Autowired
	DepartmentService deptService;

	@RequestMapping({ "/position.html" })
	private String position() {
		return "chart/position";
	}

	@RequestMapping({ "/staff.html" })
	private String staff() {
		return "chart/staff";
	}

	private void processParam(Long deptId, Map<String, Object> param, int type) {
		UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
		List<LayTree> depts = this.deptService.findDepartments(user);
		LayTree dp = depts.get(0);
		if (deptId == null || deptId.longValue() == 0L) {
			deptId = dp.getId();
		}
		param.put("departmentId", deptId);
		DepartmentEntity dept = (DepartmentEntity) this.deptService.getById(deptId);
		if ("四级".equals(dept.getLevel())) {
			if (type == 1) {
				param.put("group", "postId");
				param.put("field", "postName");
				param.put("where", "c");
			} else {
				param.put("group", "workGroupId");
				param.put("field", "username");
				param.put("where", "c");
			}
			param.put("where1", "workGroupId");
		} else if ("三级".equals(dept.getLevel())) {
			if (type == 1) {
				param.put("group", "workGroupId");
				param.put("field", "workGroup");
			} else {
				param.put("group", "workGroupId");
				param.put("field", "username");
			}
			param.put("where", "b");
			param.put("where1", "workShopId");
		} else if ("二级".equals(dept.getLevel())) {
			param.put("group", "workShopId");
			param.put("field", "workShop");
			param.put("where", "a");
			param.put("where1", "departmentId");
		} else {
			param.put("group", "departmentId");
			param.put("field", "department");
			param.put("where", "tp");
		}
	}

	@SystemLog(action = "岗位储备查询")
	@RequestMapping(value = { "/queryPositionData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject queryPositionData(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			@RequestParam(name = "departmentId", required = false) Long deptId, Map<String, Object> param) {
		JSONObject result = new JSONObject();
		try {
			processParam(deptId, param, 1);
			param.put("first", Integer.valueOf((pageNum - 1) * pageSize));
			param.put("limit", Integer.valueOf(pageSize));
			long cont = this.positionService.queryPositionDataCount(param);
			List<JobReserveVO> data = new ArrayList<>();
			if (cont > 0L) {
				data = this.positionService.queryPositionData(param);
				if (data.size() == 0 && pageNum > 1) {
					pageNum = (int) ((cont % pageSize == 0L) ? (cont / pageSize) : (cont / pageSize + 1L));
					param.put("first", Integer.valueOf((pageNum - 1) * pageSize));
					param.put("limit", Integer.valueOf(pageSize));
					data = this.positionService.queryPositionData(param);
				}
			}
			result.put("count", Long.valueOf(cont));
			result.put("code", Integer.valueOf(0));
			result.put("data", data);
		} catch (Exception e) {
			result.put("code", Integer.valueOf(1));
			e.printStackTrace();
		}
		return result;
	}

	@SystemLog(action = "岗位储备图表查询")
	@RequestMapping(value = { "/queryPositionList.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject queryPositionList(@RequestParam(name = "departmentId", required = false) Long deptId,
			Map<String, Object> param) {
		JSONObject result = new JSONObject();
		try {
			processParam(deptId, param, 1);
			List<JobReserveVO> list = this.positionService.queryPositionList(param);
			list.removeIf(x -> StringUtils.isEmpty(x.getDepartment()));
			result.put("code", Integer.valueOf(0));
			result.put("list", list);
		} catch (Exception e) {
			result.put("code", Integer.valueOf(1));
			e.printStackTrace();
		}
		return result;
	}

	@SystemLog(action = "多能工储备查询")
	@RequestMapping(value = { "/queryStaffData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject queryStaffData(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			@RequestParam(name = "departmentId", required = false) Long deptId, Map<String, Object> param) {
		JSONObject result = new JSONObject();
		try {
			processParam(deptId, param, 0);
			param.put("first", Integer.valueOf((pageNum - 1) * pageSize));
			param.put("limit", Integer.valueOf(pageSize));
			List<WorkerReserveVO> data = new ArrayList<>();
			long cont = this.positionService.queryStaffDataCount(param);
			if (cont > 0L) {
				data = this.positionService.queryStaffData(param);
				if (data.size() == 0 && pageNum > 1) {
					pageNum = (int) ((cont % pageSize == 0L) ? (cont / pageSize) : (cont / pageSize + 1L));
					param.put("first", Integer.valueOf((pageNum - 1) * pageSize));
					param.put("limit", Integer.valueOf(pageSize));
					data = this.positionService.queryStaffData(param);
				}
			}
			result.put("count", Long.valueOf(cont));
			result.put("code", Integer.valueOf(0));
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SystemLog(action = "多能工储备图表数据")
	@RequestMapping(value = { "/queryStaffList.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject queryStaffList(@RequestParam(name = "departmentId", required = false) Long deptId,
			Map<String, Object> param) {
		JSONObject result = new JSONObject();
		try {
			processParam(deptId, param, 0);
			List<JobReserveVO> list = this.positionService.queryStaffList(param);
			result.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = { "/exportFile.do" }, method = { RequestMethod.GET })
	public void exportMineData(@RequestParam(name = "id", required = false) Long deptId, Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = "岗位储备清单.xlsx";
		try {
			processParam(deptId, param, 1);
			List<JobReserveVO> list = this.positionService.queryPositionData(param);
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
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, JobReserveVO.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/exportFile1.do" }, method = { RequestMethod.GET })
	public void exportMineData1(@RequestParam(name = "id", required = false) Long deptId, Map<String, Object> param,
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = "多能工储备清单.xlsx";
		try {
			processParam(deptId, param, 0);
			List<WorkerReserveVO> data = this.positionService.queryStaffData(param);
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
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, WorkerReserveVO.class, data);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}