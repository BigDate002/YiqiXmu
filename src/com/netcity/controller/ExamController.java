package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONObject;
import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.*;
import com.netcity.module.service.ExamService;
import com.netcity.module.service.ExamUserService;
import com.netcity.module.service.PaperService;
import com.netcity.module.service.PracticeService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.ResponseFlag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping({ "/exam" })
public class ExamController {
	@Autowired
	ExamService examService;
	@Autowired
	ExamUserService examUserService;
	@Autowired
	PaperService paperService;
	@Autowired
	PracticeService practiceService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "exam/index";
	}

	@RequestMapping({ "/fushen.html" })
	public String fushen() {
		return "exam/fushen";
	}

	@RequiresPermissions({ "exam:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			ExamUserEntity exam) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			if (StringUtils.isNotBlank(exam.getQuery())) {
				exam.setQuery(String.format(" (a.usercode like '%%%s%%' or d.name like '%%%s%%') ",
						new Object[] { exam.getQuery(), exam.getQuery() }));
			}
			exam.setRoleId(user.getId());
			Subject currentUser = SecurityUtils.getSubject();
			//是否具有隐藏查看权限
			if (!currentUser.isPermitted("exam:hideOropen")){
				exam.setState1(0);
			}
			result = this.examUserService.listByPage(exam, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping({ "/index1.html" })
	public String index1() {
		return "exam/index1";
	}

	@RequestMapping({ "/index2.html" })
	public String index2() {
		return "exam/index2";
	}

	@RequestMapping({ "/detail1.html" })
	public String detail1(@RequestParam("id") Long id, Model mv) {
		ExamEntity exam = (ExamEntity) this.examService.getById(id);
		PaperEntity pe = new PaperEntity();
		pe.setCode(exam.getCode());
		List<PaperEntity> plist = this.paperService.findList(pe);
		mv.addAttribute("papers", plist);
		mv.addAttribute("exam", exam);

		PracticeEntity pre = new PracticeEntity();
		pre.setCode(exam.getCode());
		List<PracticeEntity> prlist = this.practiceService.findList(pre);
		if (prlist.size() > 0) {
			pre = prlist.get(0);
		} else {
			pre = null;
		}
		mv.addAttribute("practice", pre);
		return "exam/detail1";
	}

	@RequestMapping({ "/detail2.html" })
	public String detail2(@RequestParam("id") Long id, Model mv) {
		ExamEntity exam = (ExamEntity) this.examService.getById(id);
		PaperEntity pe = new PaperEntity();
		pe.setCode(exam.getCode());
		long score = 0L;
		List<PaperEntity> plist = this.paperService.findList(pe);
		for (PaperEntity p : plist) {
			if (p.getScore() != null) {
				score += p.getScore().longValue();
			}
		}
		score = score * exam.getPassrate().longValue() / 100L;
		PracticeEntity pre = new PracticeEntity();
		pre.setCode(exam.getCode());
		List<PracticeEntity> prlist = this.practiceService.findList(pre);
		if (prlist.size() > 0) {
			pre = prlist.get(0);
		} else {
			pre = null;
		}
		mv.addAttribute("papers", plist);
		mv.addAttribute("exam", exam);
		mv.addAttribute("score", Long.valueOf(score));
		mv.addAttribute("practice", pre);
		return "exam/detail2";
	}

	@RequestMapping({ "/score.html" })
	public String score(@RequestParam("id") Long id, @RequestParam("id1") Long id1, Model mv) {
		ExamEntity exam = (ExamEntity) this.examService.getById(id);
		ExamUserEntity eue = (ExamUserEntity) this.examUserService.getById(id1);
		PaperEntity pe = new PaperEntity();
		pe.setCode(exam.getCode());
		List<PaperEntity> plist = this.paperService.findList(pe);
		PracticeEntity pre = new PracticeEntity();
		pre.setCode(exam.getCode());
		List<PracticeEntity> prlist = this.practiceService.findList(pre);
		if (CollectionUtils.isNotEmpty(prlist)) {
			pre = prlist.get(0);
		} else {
			pre = null;
		}
		mv.addAttribute("papers", plist);
		mv.addAttribute("practice", pre);
		mv.addAttribute("usercode", ((PaperEntity) plist.get(0)).getUserCode());
		mv.addAttribute("exam", exam);
		mv.addAttribute("examuser", eue);
		return "exam/score";
	}

	@RequestMapping(value = { "/queryPerson.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPerson(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			ExamUserEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			String queryStr = " a.state = 0 and a.status = 1";
			if (query.getState().longValue() == 1L) {
				queryStr = " a.state = 2 and a.status = 2";
			}
			query.setQuery(queryStr);
			query.setState(null);
			query.setUsercode(user.getUsercode());
			result = this.examUserService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping(value = { "/savePaper.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@SystemLog(action = "提交试卷")
	public ResponseFlag savePaper(@RequestBody JSONObject param) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			String code = param.getString("code");
			param.remove("code");
			List<PaperEntity> list = new ArrayList<>();
			for (String key : param.keySet()) {
				if (StringUtils.isBlank(key)) {
					continue;
				}
				PaperEntity pe = new PaperEntity();
				pe.setId(Long.valueOf(Long.parseLong(key)));
				pe.setAnswer(StringUtils.trim(param.getString(key)));
				list.add(pe);
			}
			this.examService.savePaper(code, list, user);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/start.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag saveStart(@RequestBody ExamUserEntity ee) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ee.setUpdateUser(user.getUsercode());
			ee.setStatus(ExamUserEntity.STARTE);
			this.examUserService.updateEntity(ee);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/cancel.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@SystemLog(action = "取消测试")
	public ResponseFlag saveCancel(@RequestBody ExamUserEntity ee) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			String[] ids = ee.getIds().split(",");
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = ids).length, b = 0; b < i;) {
				String id = arrayOfString1[b];
				ExamUserEntity ee1 = new ExamUserEntity();
				ee1.setId(Long.valueOf(Long.parseLong(id)));
				ee1.setUpdateUser(user.getUsercode());
				ee1.setStatus(ExamUserEntity.CANCLE);
				this.examUserService.updateEntity(ee1);
				b++;
			}

			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/finish.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@SystemLog(action = "结束试卷")
	public ResponseFlag saveFinish(@RequestBody ExamUserEntity ee) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ee.setUpdateUser(user.getUsercode());
			ee.setStatus(ExamUserEntity.FINISH);
			this.examUserService.updateEntity(ee);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/saveScore.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@SystemLog(action = "考核审核提交")
	public ResponseFlag saveScore(@RequestBody JSONObject jobj) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			jobj.put("usercode", user.getUsercode());
			this.examService.updateScore(jobj);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/fushen.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@SystemLog(action = "复审提交")
	public ResponseFlag saveFushen(@RequestBody JSONObject jobj) {
		ResponseFlag result = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			jobj.put("usercode", user.getUsercode());
			this.examService.createFushen(jobj);
			result.setFlag(ResponseFlag.Success);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setFlag(ResponseFlag.Failed);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(ExamUserEntity query, HttpServletRequest request, HttpServletResponse response) {
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
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
			List<ExamExportEntity> list = this.examUserService.findExamList(query);
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, ExamExportEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequiresPermissions({ "exam:hideOropen" })
	@SystemLog(action = "数据隐藏开启")
	@RequestMapping(value = { "/hideOropen.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag hideOropen(@RequestParam("ids") String ids,@RequestParam("state1") Integer state1) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ExamEntity query = new ExamEntity();
			query.setUpdateUser(user.getUsercode());
			query.setUpdateDate(new Date());
			query.setIds(ids);
			query.setState1(state1);
			examService.updateByIds(query);
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
