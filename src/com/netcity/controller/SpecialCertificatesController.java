package com.netcity.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.ReviewEntity;
import com.netcity.module.entity.SpecialAttachementsEntity;
import com.netcity.module.entity.SpecialCertificatesEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.SpecialCertificatesService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.ResponseFlag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author sunshaojun
 */
@Controller
@RequestMapping({ "/specialCertificates" })
public class SpecialCertificatesController {
	private final static Logger log = LoggerFactory.getLogger(SpecialCertificatesController.class);

	@Autowired
	private SpecialCertificatesService specialCertificatesService;

	@Value("${uploadPath}")
	private String uploadPath;

	@RequestMapping({ "/newClaim.html" })
	public String newClaim() {
		return "specialCertificates/newClaim";
	}

	@RequestMapping({ "/review.html" })
	public String review() {
		return "specialCertificates/review";
	}

	@RequestMapping({ "/cert.html" })
	public String cert() {
		return "specialCertificates/cert";
	}

	@RequestMapping(value = { "/newClaim/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize, SpecialCertificatesEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
			if (!"admin".equals(user.getUsercode())) {
				query.setUsercode(user.getUsercode());
			}
			result = this.specialCertificatesService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequestMapping(value = { "/newClaim/queryById.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag queryById(SpecialCertificatesEntity query) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
			if (!"admin".equals(user.getUsercode())) {
				query.setUsercode(user.getUsercode());
			}
			List<SpecialCertificatesEntity> list = this.specialCertificatesService.findList(query);
			if (CollectionUtils.isNotEmpty(list)){
				res.setFlag(ResponseFlag.Success);
				res.setData(list.get(0));
			}else {
				res.setFlag(ResponseFlag.Failed);
				res.setMessage("查不到数据啊");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}


	@RequestMapping(value = { "/exportData.do" }, method = { RequestMethod.POST })
	public void exportData(SpecialCertificatesEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "特种工资料提报导出数据.xlsx";
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			List<SpecialCertificatesEntity> list = this.specialCertificatesService.findList(query);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			ExportParams params = new ExportParams("特种工证件资料提报", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, SpecialCertificatesEntity.class, list);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping(value = { "/exportReviewData.do" }, method = { RequestMethod.POST })
	public void exportReviewData(SpecialCertificatesEntity query, HttpServletRequest request, HttpServletResponse response) {
		String fileName = "特种工资料提报导出数据.xlsx";
		try {
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			List<SpecialCertificatesEntity> list = this.specialCertificatesService.findList(query);
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ServletOutputStream out = response.getOutputStream();
			response.setCharacterEncoding("utf-8");
			List<ReviewEntity> dataList = Lists.newArrayList();
			for (SpecialCertificatesEntity specialCertificatesEntity : list){
				ReviewEntity reviewEntity = new ReviewEntity();
				BeanUtils.copyProperties(specialCertificatesEntity,reviewEntity);
				dataList.add(reviewEntity);
			}
			ExportParams params = new ExportParams("特种工证件资料提报", "sheet1", ExcelType.XSSF);
			Workbook workbook = ExcelExportUtil.exportExcel(params, ReviewEntity.class, dataList);
			workbook.write((OutputStream) out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping(value = { "/upload.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag importData(@RequestParam("filename") MultipartFile file) {
		ResponseFlag res = new ResponseFlag();
		try {
			DateFormat df = new SimpleDateFormat("yyyy/MM");
			Date time = new Date();
			String path = String.valueOf(this.uploadPath) + File.separator + df.format(time);
			File file2 = new File(path);
			if (!file2.exists()) {
				file2.mkdirs();
			}
			String fpath = String.valueOf(path) + File.separator + time.getTime() + "."
					+ file.getOriginalFilename().split("\\.")[1];
			File newFile = new File(fpath);
			file.transferTo(newFile);
			res.setFlag(ResponseFlag.Success);
			res.setMessage(file.getOriginalFilename());
			res.setMsg(fpath);
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage(e.getMessage());
		}
		return res;
	}

	@RequestMapping(value = { "/downloadimg.do" }, method = { RequestMethod.GET })
	public void downloadImg(@RequestParam("path") String path, HttpServletRequest request, HttpServletResponse response) {
		FileInputStream fis = null;
		response.setHeader("Content-Type", "image/jpeg");
		try {
			OutputStream out = response.getOutputStream();
			File file = new File(path);
			fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public ResponseFlag create(@RequestBody SpecialCertificatesEntity specialCertificatesEntity) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			specialCertificatesEntity.setCreateUser(usr.getUsercode());
			specialCertificatesEntity.setUsercode(usr.getUsercode());
			this.specialCertificatesService.insert(specialCertificatesEntity);
			if (CollectionUtils.isNotEmpty(specialCertificatesEntity.getFiles())) {
				for (SpecialAttachementsEntity f : specialCertificatesEntity.getFiles()) {
					f.setBusinessId(specialCertificatesEntity.getId());
					this.specialCertificatesService.insertSpecialAttachements(f);
				}
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

	@RequestMapping(value = { "/downloadFiles.do" }, method = { RequestMethod.POST })
	public void downloadFiles(SpecialCertificatesEntity query, HttpServletRequest request, HttpServletResponse response) {
		specialCertificatesService.downloadFiles(query, request, response);
	}


	@SystemLog(action = "编辑特种工资料")
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody SpecialCertificatesEntity specialCertificatesEntity) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			specialCertificatesEntity.setUpdateUser(user.getUsercode());
			this.specialCertificatesService.updateEntity(specialCertificatesEntity);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequestMapping(value = { "/cert/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo certQueryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize, SpecialCertificatesEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			query.setRoleId(user.getId());
			result = this.specialCertificatesService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@SystemLog(action = "编辑特种工证件资料")
	@RequestMapping(value = { "/certUpdate.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag certUpdate(@RequestBody SpecialCertificatesEntity specialCertificatesEntity) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			specialCertificatesEntity.setUpdateUser(user.getUsercode());
			if (null != specialCertificatesEntity.getCertificationTimeStr()){
				specialCertificatesEntity.setCertificationTime(DateUtil.parse(specialCertificatesEntity.getCertificationTimeStr()));
			}
			this.specialCertificatesService.certUpdateById(specialCertificatesEntity);
			//更新附件内容
			if (CollectionUtils.isNotEmpty(specialCertificatesEntity.getFiles())) {
				this.specialCertificatesService.updateSpecialAttachementsById(specialCertificatesEntity.getId(),specialCertificatesEntity.getFiles());
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


	/**
	 * 手机上传
	 * @param specialCertificatesEntity
	 * @return
	 */
	@RequestMapping(value = { "/mobileCreate.do" }, method = { RequestMethod.POST })
	@ResponseBody
	@Transactional
	public ResponseFlag mobileCreate(@RequestBody SpecialCertificatesEntity specialCertificatesEntity) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			specialCertificatesEntity.setCreateUser(usr.getUsercode());
			specialCertificatesEntity.setUsercode(usr.getUsercode());
			if (null != usr.getDeptId()){
				specialCertificatesEntity.setDeptId(usr.getDeptId());
			}else if (null != usr.getDepartmentId()){
				specialCertificatesEntity.setDeptId(usr.getDepartmentId());
			}
			this.specialCertificatesService.insert(specialCertificatesEntity);

			if (null != specialCertificatesEntity.getFrontOfIDCard()){
				SpecialAttachementsEntity specialAttachementsEntity = new SpecialAttachementsEntity();
				specialAttachementsEntity.setBusinessId(specialCertificatesEntity.getId());
				specialAttachementsEntity.setFileName(specialCertificatesEntity.getFrontOfIDCardName());
				specialAttachementsEntity.setUrl(specialCertificatesEntity.getFrontOfIDCard());
				specialAttachementsEntity.setFileType(0);
				this.specialCertificatesService.insertSpecialAttachements(specialAttachementsEntity);
			}
			if (null != specialCertificatesEntity.getReverseSideOfIDCard()){
				SpecialAttachementsEntity specialAttachementsEntity = new SpecialAttachementsEntity();
				specialAttachementsEntity.setBusinessId(specialCertificatesEntity.getId());
				specialAttachementsEntity.setFileName(specialCertificatesEntity.getReverseSideOfIDCardName());
				specialAttachementsEntity.setUrl(specialCertificatesEntity.getReverseSideOfIDCard());
				specialAttachementsEntity.setFileType(1);
				this.specialCertificatesService.insertSpecialAttachements(specialAttachementsEntity);
			}
			if (null != specialCertificatesEntity.getEducationCertificate()){
				SpecialAttachementsEntity specialAttachementsEntity = new SpecialAttachementsEntity();
				specialAttachementsEntity.setBusinessId(specialCertificatesEntity.getId());
				specialAttachementsEntity.setFileName(specialCertificatesEntity.getEducationCertificateName());
				specialAttachementsEntity.setUrl(specialCertificatesEntity.getEducationCertificate());
				specialAttachementsEntity.setFileType(2);
				this.specialCertificatesService.insertSpecialAttachements(specialAttachementsEntity);
			}
			if (null != specialCertificatesEntity.getOtherMaterials()){
				SpecialAttachementsEntity specialAttachementsEntity = new SpecialAttachementsEntity();
				specialAttachementsEntity.setBusinessId(specialCertificatesEntity.getId());
				specialAttachementsEntity.setFileName(specialCertificatesEntity.getOtherMaterialsName());
				specialAttachementsEntity.setUrl(specialCertificatesEntity.getOtherMaterials());
				specialAttachementsEntity.setFileType(3);
				this.specialCertificatesService.insertSpecialAttachements(specialAttachementsEntity);
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


}
