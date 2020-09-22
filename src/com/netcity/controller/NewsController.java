package com.netcity.controller;

import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.AttachementsEntity;
import com.netcity.module.entity.NewsEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.AttachementsService;
import com.netcity.module.service.NewsService;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.ResponseFlag;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({ "/news" })
public class NewsController {
	@Autowired
	NewsService newsService;
	@Autowired
	AttachementsService attachementsService;
	@Value("${uploadPath}")
	private String uploadPath;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "news/index";
	}

	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			NewsEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			result = this.newsService.listByPage(query, pageNum, pageSize);
		} catch (Exception e) {
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "news:create" })
	@SystemLog(action = "添加公告")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody NewsEntity pos) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity usr = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			pos.setCreateUser(usr.getUsercode());
			this.newsService.insert(pos);
			if (CollectionUtils.isNotEmpty(pos.getFiles())) {
				for (AttachementsEntity f : pos.getFiles()) {
					f.setNewsId(pos.getId());
					this.attachementsService.insert(f);
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

	@RequiresPermissions({ "news:delete" })
	@SystemLog(action = "删除公告")
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestParam("id") Long id) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.newsService.deleteById(id);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@SystemLog(action = "上传附件")
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

	@RequestMapping(value = { "/downloadFile.do" }, method = { RequestMethod.GET })
	@ResponseBody
	public void importData(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse res) {
		try {
			AttachementsEntity file = (AttachementsEntity) this.attachementsService.getById(id);
			String fileName = file.getFileName();
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			res.setContentType("application/octet-stream;charset=utf-8");
			File f = new File(file.getUrl());
			InputStream in = new BufferedInputStream(new FileInputStream(f));
			FileCopyUtils.copy(in, (OutputStream) res.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}