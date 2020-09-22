package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.PositionDao;
import com.netcity.module.dao.QualificationDao;
import com.netcity.module.entity.*;
import com.netcity.module.service.*;
import com.netcity.service.impl.BaseServiceImpl;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("qualificationService")
public class QualificationServiceImpl<T extends BaseEntity> extends BaseServiceImpl<QualificationEntity>
		implements QualificationService {
	@Autowired
	QualificationDao qualificationDao;

	@Autowired
	UserService userService;

	public BaseDao<QualificationEntity> getDao() {
		return (BaseDao<QualificationEntity>) this.qualificationDao;
	}

	@Autowired
	PositionService positionService;

	@Autowired
	CodeService codeService;

	@Autowired
	PositionDao positionDao;

	public void insertImport(List<QualificationEntity> list, UserEntity us) throws ServiceException {
		String code = "";
		try {
			UserEntity u = new UserEntity();
			u.setId(us.getId());
			Map<String, PositionEntity> posts = this.positionDao.findPostions(u);//该人员id所在部门下所有岗位对应的课程
			List<String> ul = this.positionDao.findUsers(u);//该人员id所在部门下所有岗位对应的人员工号
			for (QualificationEntity qe : list) {//判断工号、岗位是否存在，存在则添加岗位id和复审次数为0

				if (!ul.contains(qe.getUserCode())) {
					throw new ServiceException(String.valueOf(qe.getUserCode()) + " 工号不存在");
				}
				String k = String.format("%s-%s-%s-%s",
						new Object[] { qe.getDepartment(), qe.getWorkShop(), qe.getWorkGroup(), qe.getPost() });
				PositionEntity pe = posts.get(k);
				if (pe == null) {
					throw new ServiceException(String.valueOf(k) + "岗位不存在");
				}
				qe.setPostionId(pe.getId());
				if (qe.getType() == null) {
					qe.setType(Long.valueOf(0L));
				}
			}
			for (QualificationEntity qe : list) {//删除资质表UserCode和PostionId匹配项，再添加资质
				DateFormat df = new SimpleDateFormat("yyyyMM");
				String prefix = "BDVM" + qe.getPostionId() + df.format(new Date());
				int no = this.codeService.createNextNo(prefix);//证书编码(BDVM+岗位id+年月+5位流水号)
				qe.setCode(String.format("%s%05d", new Object[] { prefix, Integer.valueOf(no) }));
				Calendar c = Calendar.getInstance();//生效时间加上有效期得到失效时间
				c.setTime(qe.getBeginDate());
				c.add(2, qe.getEffectiveDate().intValue());//月份加EffectiveDate数
				qe.setEndDate(c.getTime());
				QualificationEntity qq = new QualificationEntity();
				qq.setUserCode(qe.getUserCode());
				qq.setPostionId(qe.getPostionId());
				List<QualificationEntity> ql = findList(qq);
				for (QualificationEntity qu : ql) {
					deleteById(qu.getId());
				}

				insert(qe);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof org.springframework.dao.DuplicateKeyException) {
				throw new ServiceException("证书编号重复" + code);
			}
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void updateZizhi() {
		qualificationDao.updateZizhi();
	}

	@Override
	public void certificateRecovery(QualificationEntity query) throws ServiceException {
		//需删除该资质获取过程的培训记录和测试记录
		query.setState(1L);
		qualificationDao.updateByIds(query);
	}
}
