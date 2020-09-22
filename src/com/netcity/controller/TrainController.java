package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.ExamEntity;
import com.netcity.module.entity.TrainEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.TrainService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.QueryResult;
import com.netcity.util.ResponseFlag;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping({ "train" })
public class TrainController {
	@Autowired
	TrainService trainService;
	@RequestMapping({ "/index.html" })
	public String index() {
		return "train/index";
	}

	@RequiresPermissions({ "train:query" })
	@SystemLog(action = "培训分页查询")
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			TrainEntity course) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			course.setRoleId(user.getId());
			result = trainService.listByPage(course, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}
	@RequiresPermissions({ "train:query" })
	@SystemLog(action = "培训分页查询")
	@RequestMapping(value = { "/queryPage1.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage1(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			TrainEntity course) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			UserEntity user = (UserEntity) currentUser.getPrincipal();
			//是否具有隐藏查看权限
			if (!currentUser.isPermitted("train:hideOropen")){
				course.setState1(0);
			}
			course.setRoleId(user.getId());
			result = trainService.listByPage1(course, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "train:create" })
	@SystemLog(action = "发布通用培训")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody TrainEntity course) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			course.setCreateUser(user.getUsercode());
			course.setType("0");
			trainService.createTrain(course);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@RequiresPermissions({ "train:create" })
	@SystemLog(action = "发布资质培训")
	@RequestMapping(value = { "/createI.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag createI(@RequestBody TrainEntity train) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			train.setType("1");
			train.setCreateUser(user.getUsercode());
			trainService.createTrain(train);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@RequiresPermissions({ "train:cancel" })
	@SystemLog(action = "取消培训")
	@RequestMapping(value = { "/cancel.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody TrainEntity te) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			te.setUpdateUser(user.getUsercode());
			te.setState(Long.valueOf(0L));
			trainService.updateEntity(te);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "train:stop" })
	@SystemLog(action = "结束培训")
	@RequestMapping(value = { "/stop.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag updatestop(@RequestBody ExamEntity pe) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			pe.setUpdateUser(user.getUsercode());
			trainService.updateStop(pe);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@RequestMapping(value = { "/queryUserbypostion.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryUserbypostion(TrainEntity q) {
		QueryResult qr = new QueryResult();
		qr.setCode(Long.valueOf(0L));
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			q.setRoleId(user.getId());
			List<UserEntity> data = trainService.queryUserbypostion(q);
			qr.setData(data);
			qr.setFlag(Boolean.valueOf(true));
		} catch (Exception e) {
			e.printStackTrace();
			qr.setMessage("查询失败");
			qr.setFlag(Boolean.valueOf(false));
		}
		return qr;
	}

	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(TrainEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "导出数据.xlsx";
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
			List<TrainEntity> list = trainService.findList(query);
			ExportParams params = new ExportParams("导出数据", "培训数据", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, TrainEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequiresPermissions({ "train:hideOropen" })
	@SystemLog(action = "数据隐藏开启")
	@RequestMapping(value = { "/hideOropen.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag hideOropen(@RequestParam("ids") String ids,@RequestParam("state1") Integer state1) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			TrainEntity query = new TrainEntity();
			query.setUpdateUser(user.getUsercode());
			query.setUpdateDate(new Date());
			query.setIds(ids);
			query.setState1(state1);
			trainService.updateByIds(query);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

}
