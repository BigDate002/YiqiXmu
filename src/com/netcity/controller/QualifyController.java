package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.QualificationEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.QualificationService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.ResponseFlag;
import gui.ava.html.image.generator.HtmlImageGenerator;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({ "/qualify" })
public class QualifyController {
	@Autowired
	QualificationService qualificationService;

	@RequestMapping({ "/temp.html" })
	public String temp() {
		return "qualify/temp";
	}

	@RequestMapping({ "/index.html" })
	public String index() {
		return "qualify/index";
	}

	@RequestMapping({ "/mine.html" })
	public String mine() {
		return "qualify/mine";
	}

	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			QualificationEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
			result = this.qualificationService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(QualificationEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "资质证书导出数据.xlsx";
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
			List<QualificationEntity> list = this.qualificationService.findList(query);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			ExportParams params = new ExportParams("导入模板", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, QualificationEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/exportMineData.do" }, method = { RequestMethod.POST })
	public void exportMineData(QualificationEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "资质证书导出数据.xlsx";
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setUserCode(user.getUsercode());
			query.setRoleId(user.getId());
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			List<QualificationEntity> list = this.qualificationService.findList(query);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			ExportParams params = new ExportParams("导出数据", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, QualificationEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/downloadTemplate.do" })
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "资质导入模板.xlsx";
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
			Workbook workbook = ExcelExportUtil.exportExcel(params, QualificationEntity.class,
					new ArrayList<QualificationEntity>());
			workbook.getSheetAt(0).setColumnHidden(8, true);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequiresPermissions({ "qualify:importData" })
	@SystemLog(action = "导入人员资质")
	@RequestMapping(value = { "/importData.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			List<QualificationEntity> list = ExcelImportUtil.importExcel(file.getInputStream(),
					QualificationEntity.class, params);
			for (QualificationEntity qe : list) {
				qe.setCreateUser(user.getUsercode());
			}
			this.qualificationService.insertImport(list, user);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@RequestMapping({ "/zizhi.html" })
	public String zizhi(@RequestParam("id") Long id) {
		return "/mobile/zizhi0.html";
	}

	@RequestMapping(value = { "/downloadimg.do" }, method = { RequestMethod.GET })
	public void downloadimg(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment; filename=qualify.jpg");
			DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			QualificationEntity q = (QualificationEntity) this.qualificationService.getById(id);
			HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
			File zizhi = new File(request.getServletContext().getRealPath("mobile/zizhi0.html"));
			if (q.getType().longValue() > 0L) {
				zizhi = new File(request.getServletContext().getRealPath("mobile/zizhi1.html"));
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(zizhi), "utf-8"));
			String html = "";
			String str = null;
			while ((str = br.readLine()) != null) {
				html = String.valueOf(html) + str;
			}
			br.close();
			html = html.replaceAll("ditu",
					request.getServletContext().getRealPath("images/back.jpg").replaceAll("\\\\", "/"));
			html = html.replaceAll("seal",
					request.getServletContext().getRealPath("/WEB-INF/seal.png").replaceAll("\\\\", "/"));
			html = html.replaceAll("username", q.getUsername());
			html = html.replaceAll("usercode", q.getUserCode());
			html = html.replaceAll("department", q.getDepartment());
			html = html.replaceAll("workShop", q.getWorkShop());
			html = html.replaceAll("workGroup", (q.getWorkGroup() == null) ? "" : q.getWorkGroup());
			html = html.replaceAll("youxiaoqi", q.getEffectiveDate().toString());
			html = html.replaceAll("post", q.getPost());
			html = html.replaceAll("code", q.getCode());
			html = html.replaceAll("date", df.format(q.getBeginDate()));
			html = html.replaceAll("createDate", df.format(q.getCreateDate()));
			html = html.replaceAll("count", q.getType().toString());

			imageGenerator.loadHtml(html);
			BufferedImage ditu = imageGenerator.getBufferedImage();
			ImageIO.write(ditu, "PNG", (OutputStream) response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = { "/queryMinePage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryMinePage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			QualificationEntity exam) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			exam.setUserCode(user.getUsercode());
			result = this.qualificationService.listByPage(exam, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}


	@RequiresPermissions({ "qualify:certificateRecovery" })
	@SystemLog(action = "证件收回")
	@RequestMapping(value = { "/certificateRecovery.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag certificateRecovery(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			QualificationEntity query = new QualificationEntity();
			query.setUpdateUser(user.getUsercode());
			query.setUpdateDate(new Date());
			query.setIds(ids);
			this.qualificationService.certificateRecovery(query);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("证件收回成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}


}
