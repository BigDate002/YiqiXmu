package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.QuestionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.QuestionService;
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
@RequestMapping({ "/question" })
public class QuestionController {
	@Autowired
	QuestionService questionService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "question/index";
	}

	@RequiresPermissions({ "question:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@SystemLog(action = "试题分页查询")
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			QuestionEntity question) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			question.setRoleId(user.getId());
			result = this.questionService.listByPage(question, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@SystemLog(action = "新增试题")
	@RequiresPermissions({ "question:create" })
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody QuestionEntity question) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			question.setCreateUser(user.getUsercode());
			this.questionService.insert(question);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "编辑试题")
	@RequiresPermissions({ "question:update" })
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody QuestionEntity row) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			row.setUpdateUser(user.getUsercode());
			this.questionService.updateEntity(row);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "禁用试题")
	@RequiresPermissions({ "question:delete" })
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			QuestionEntity qe = new QuestionEntity();
			qe.setState(Long.valueOf(0L));
			qe.setIds(ids);
			this.questionService.updateByIds(qe);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "启用试题")
	@RequiresPermissions({ "question:delete" })
	@RequestMapping(value = { "/grant.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag enable(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			QuestionEntity qe = new QuestionEntity();
			qe.setState(Long.valueOf(1L));
			qe.setIds(ids);
			this.questionService.updateByIds(qe);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "question:query" })
	@RequestMapping(value = { "/queryList.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryList(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			QuestionEntity question) {
		QueryResult result = new QueryResult();
		try {
			List<QuestionEntity> list = this.questionService.findList(question);
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
	public void exportData(QuestionEntity query, HttpServletRequest request, HttpServletResponse response) {
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
			List<QuestionEntity> list = this.questionService.findList(query);
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, QuestionEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "试题导入模板.xlsx";
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
			List<QuestionEntity> examples = new ArrayList<>();
			QuestionEntity qe = new QuestionEntity();
			qe.setType("填空题");
			qe.setTitle("填空题(每个空用下划线表示)");
			qe.setContent("一年有____天[填空位用下划线表示]");
			qe.setAnswer("365");
			qe.setRequired(Long.valueOf(0L));
			qe.setCourseName("例子");
			examples.add(qe);
			qe = new QuestionEntity();
			qe.setType("单选题");
			qe.setTitle("单选题(选项和选项内容用.隔开,选项之间用|隔开)");
			qe.setContent("法定一周休息几天?");
			qe.setSelections("A.1|B.2|C.3|D.4");
			qe.setAnswer("B");
			qe.setRequired(Long.valueOf(0L));
			qe.setCourseName("例子");
			examples.add(qe);
			qe = new QuestionEntity();
			qe.setType("多选题");
			qe.setTitle("多选题(选项和选项内容用分号隔开.选项之间用|隔开)");
			qe.setContent("中国一年分几个季节?");
			qe.setSelections("A.1|B.2|C.3|D.4");
			qe.setAnswer("ABCD");
			qe.setRequired(Long.valueOf(0L));
			qe.setCourseName("例子");
			examples.add(qe);
			qe = new QuestionEntity();
			qe.setType("判断题");
			qe.setTitle("判断题(选项用|隔开)");
			qe.setContent("开车不喝酒对吗?");
			qe.setSelections("对|错");
			qe.setAnswer("对");
			qe.setRequired(Long.valueOf(0L));
			qe.setCourseName("例子");
			examples.add(qe);
			qe = new QuestionEntity();
			qe.setType("简答题");
			qe.setTitle("这是一个简答题");
			qe.setContent("初唐四杰是?");
			qe.setAnswer("参考");
			qe.setRequired(Long.valueOf(0L));
			qe.setCourseName("例子");
			examples.add(qe);
			Workbook workbook = ExcelExportUtil.exportExcel(params, QuestionEntity.class, examples);
			String[] textlist = { "填空题", "单选题", "多选题", "判断题", "简答题", "理论考核项目", "技能考核项目" };
			ExcelHelper.setXSSFValidation(workbook.getSheetAt(0), textlist, 2, 500, 3, 3);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SystemLog(action = "批量导入试题")
	@RequiresPermissions({ "question:importData" })
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<QuestionEntity> list2 = ExcelImportUtil.importExcel(file.getInputStream(), QuestionEntity.class,
					params);
			List<QuestionEntity> list = new ArrayList<>();
			for (QuestionEntity ce : list2) {
				if (ce.getDepartment() == null) {
					break;
				}
				list.add(ce);
			}
			for (QuestionEntity ce : list) {
				ce.setCreateUser(user.getUsercode());
			}
			this.questionService.insertImport(list);
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