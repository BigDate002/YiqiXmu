package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.controller.SpecialCertificatesController;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.SpecialDao;
import com.netcity.module.entity.SpecialAttachementsEntity;
import com.netcity.module.entity.SpecialCertificatesEntity;
import com.netcity.module.service.SpecialCertificatesService;
import com.netcity.service.impl.BaseServiceImpl;
import com.netcity.util.ZipUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 描述
 *
 * @outhor sunshaojun
 * @Create 2020-08-16:34
 */
@Service("specialCertificatesService")
public class SpecialCertificatesServiceImpl<T extends BaseEntity> extends BaseServiceImpl<SpecialCertificatesEntity> implements SpecialCertificatesService {
    private final static Logger log = LoggerFactory.getLogger(SpecialCertificatesServiceImpl.class);

    @Autowired
    SpecialDao specialDao;
    @Autowired
    @Qualifier("sqlsessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate;
    @Value("${uploadPath}")
    private String uploadPath;
    @Override
    public BaseDao<SpecialCertificatesEntity> getDao() {
        return (BaseDao<SpecialCertificatesEntity>) this.specialDao;
    }

    @Override
    public int insertSpecialAttachements(SpecialAttachementsEntity specialAttachementsEntity) {
        return this.sqlSessionTemplate.insert("com.netcity.module.entity.SpecialAttachements.insert", specialAttachementsEntity);
    }

    @Override
    public void downloadFiles(SpecialCertificatesEntity query, HttpServletRequest request, HttpServletResponse response) {
        List<SpecialCertificatesEntity> list = this.findList(query);
        if (!CollectionUtils.isNotEmpty(list)){
            return;
        }
        //响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");

        //设置压缩包的名字
        //解决不同浏览器压缩包名字含有中文时乱码的问题
        String downloadName = "特种工证件图片资料.zip";
        try {
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                downloadName = new String(downloadName.getBytes("UTF-8"), "ISO8859-1");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                downloadName = URLEncoder.encode(downloadName, "UTF-8");
            } else if (request.getHeader("User-Agent").toUpperCase().indexOf("CHROME") > 0) {
                downloadName = new String(downloadName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (Exception e) {
            log.error("格式化下载名字错误!",e);
            e.printStackTrace();
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + downloadName);
        //设置压缩流：直接写入response，实现边压缩边下载
        ZipOutputStream zipos = null;
        try {
            zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
            //设置压缩方法
            zipos.setMethod(ZipOutputStream.DEFLATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //文件移动临时目录 用完删除
        String tmpPath = this.uploadPath + File.separator + "downloadFilesTmp"+ File.separator + System.currentTimeMillis() + File.separator + "特种工证件图片资料";
        for (SpecialCertificatesEntity specialCertificatesEntity :list){
            //文件夹要求: 张三GW00000000
            String userPath = tmpPath + File.separator + specialCertificatesEntity.getUsername()+ specialCertificatesEntity.getUsercode();
            File userFile = new File(userPath);
            if (CollectionUtils.isNotEmpty(specialCertificatesEntity.getFiles())) {
                if (!userFile.exists()) {
                    userFile.mkdirs();
                }
                //移动个人图片资料到 userFile 临时文件夹
                for (SpecialAttachementsEntity attachments : specialCertificatesEntity.getFiles()){
                    try {
                        ZipUtils.copy(new File(attachments.getUrl()), new File(userFile + File.separator +attachments.getFileName()));
                    } catch (Exception e) {
                        log.error("文件copy错误!",e);
                        e.printStackTrace();
                    }
                }
            }
        }
        String zipPath = this.uploadPath + File.separator + "zipTmp";

        File zipFile = new File(zipPath);
        if (!zipFile.exists()) {
            zipFile.mkdirs();
        }

        ZipUtils.createZipFile(tmpPath,zipPath,downloadName);

        //循环将文件写入压缩流
        DataOutputStream os = null;
        try {
            //添加ZipEntry，并ZipEntry中写入文件流
            File file = new File(zipPath + File.separator + downloadName);
            zipos.putNextEntry(new ZipEntry(downloadName));
            os = new DataOutputStream(zipos);
            InputStream is = new FileInputStream(file);
            byte[] b = new byte[100];
            int length;
            while((length = is.read(b))!= -1){
                os.write(b, 0, length);
            }
            is.close();
            zipos.closeEntry();
        } catch (IOException e) {
            log.error("文件压缩失败哦!",e);
            e.printStackTrace();
        }
        //关闭流
        try {
            os.flush();
            os.close();
            zipos.close();
        } catch (IOException e) {
            log.error("关闭流失败哦!",e);
            e.printStackTrace();
        }

        // 清空文件夹
        ZipUtils.deleteFile(zipFile);
        ZipUtils.deleteFile(new File(this.uploadPath + File.separator + "downloadFilesTmp"));
    }

    @Override
    public void certUpdateById(SpecialCertificatesEntity query) {
        this.sqlSessionTemplate.update("com.netcity.module.entity.SpecialCertificates.certUpdateById", query);
    }

    @Override
    public void updateSpecialAttachementsById(Long specialId,List<SpecialAttachementsEntity> list) {
        //先删除所有附件,然后在插入
        this.sqlSessionTemplate.delete("com.netcity.module.entity.SpecialAttachements.deleteByBusinessId",specialId);
        for (SpecialAttachementsEntity f : list) {
            f.setBusinessId(specialId);
            insertSpecialAttachements(f);
        }
    }
}
