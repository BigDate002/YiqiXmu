package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.CourseDao;
import com.netcity.module.dao.QuestionDao;
import com.netcity.module.entity.CourseEntity;
import com.netcity.module.entity.QuestionEntity;
import com.netcity.module.service.QuestionService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("questionService")
public class QuestionServiceImpl<T extends BaseEntity> extends BaseServiceImpl<QuestionEntity>
		implements QuestionService {
	@Autowired
	QuestionDao questionDao;
	@Autowired
	CourseDao courseDao;

	public BaseDao<QuestionEntity> getDao() {
		return (BaseDao<QuestionEntity>) this.questionDao;
	}

	//题库导入：从数据库中查询出课程，通过Department，WorkShop，课程名称和到入数据匹配，成功添加
	public void insertImport(List<QuestionEntity> list) throws ServiceException {
		try {
			Map<Long, CourseEntity> courses1 = this.courseDao.queryMap();
			Map<String, CourseEntity> courses = new HashMap<>();
			for (Long key : courses1.keySet()) {
				CourseEntity cour = courses1.get(key);
				String courseName = String.format("%s-%s-%s",
						new Object[] { cour.getDepartment(), cour.getWorkShop(), cour.getName() });
				courses.put(courseName, cour);
			}
			for (QuestionEntity qe : list) {
				String key = String.format("%s-%s-%s",
						new Object[] { qe.getDepartment(), qe.getWorkShop(), qe.getCourseName() });
				CourseEntity ce = courses.get(key);
				if (ce == null) {
					throw new ServiceException(String.valueOf(key) + "课程不存在!");
				}
				qe.setCourseId(ce.getId());
			}
			if (CollectionUtils.isNotEmpty(list)) {
				insertBatchOnce(list);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	  }
/*    */ }