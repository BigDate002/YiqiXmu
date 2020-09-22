package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.CourseDao;
import com.netcity.module.dao.CoursePositionDao;
import com.netcity.module.dao.PositionDao;
import com.netcity.module.entity.CourseEntity;
import com.netcity.module.entity.CoursePositionEntity;
import com.netcity.module.entity.PositionEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.CourseService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("courseService")
public class CourseServiceImpl<T extends BaseEntity> extends BaseServiceImpl<CourseEntity> implements CourseService {
	@Autowired
	CourseDao CourseDao;
	@Autowired
	PositionDao positionDao;
	@Autowired
	CoursePositionDao coursePositionDao;

	public BaseDao<CourseEntity> getDao() {
		return (BaseDao<CourseEntity>) this.CourseDao;
	}

	public void insertRow(CourseEntity course) throws ServiceException {
		try {
			insert(course);
			Long id = course.getId();
			if (CollectionUtils.isNotEmpty(course.getPositions())) {
				for (PositionEntity p : course.getPositions()) {
					CoursePositionEntity cp = new CoursePositionEntity();
					cp.setCourseId(id);
					cp.setPositionId(p.getId());
					this.coursePositionDao.insert(cp);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void updateRow(CourseEntity course) throws ServiceException {
		try {
			updateEntity(course);
			Long id = course.getId();
			this.coursePositionDao.deleteById(id);
			if (CollectionUtils.isNotEmpty(course.getPositions())) {
				for (PositionEntity p : course.getPositions()) {
					CoursePositionEntity cp = new CoursePositionEntity();
					cp.setCourseId(id);
					cp.setPositionId(p.getId());
					this.coursePositionDao.insert(cp);
				}
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void insertImport(List<CourseEntity> list, UserEntity user) throws ServiceException {
		try {
			List<CoursePositionEntity> cplist = new ArrayList<>();
			Map<String, CourseEntity> courseMap = new HashMap<>();
			for (CourseEntity c : list) {//判断资质培训岗位不为空，替换岗位名称中的中文逗号、空格换行，方框内问号
				if (c.getType().longValue() == 1L && StringUtils.isBlank(c.getPositionName())) {
					throw new ServiceException("岗位不能空");
				}
				if (c.getType().longValue() == 1L) {
					String sss = c.getPositionName().replaceAll("，", ",").replaceAll("(\r\n|\r|\n|\n\r)", "");
					for (int i = 10; i < 14; i++) {
						sss = sss.replaceAll(String.valueOf((char) i), "");
					}
					c.setPositionName(sss);
				}
			}
			UserEntity u = new UserEntity();
			u.setId(user.getId());//根据人员id查询出部门车间科室班组岗位对应课程（即该人部门下的所用岗位对应课程）
			Map<String, PositionEntity> posts = this.positionDao.findPostions(u);
			for (CourseEntity ce : list) { 
				String posname = ce.getPositionName();
				String key = ce.getWorkShopId() + "-" + ce.getName();
				String group = ce.getWorkGroup();
				List<PositionEntity> positions = null;
				if (!courseMap.containsKey(key)) {
					courseMap.put(key, ce);
					if (ce.getType().longValue() == 0L)
						continue;
					if (ce.getId() == null) {
						positions = new ArrayList<>();
						ce.setPositions(positions);
					}
				} else {
					ce = courseMap.get(key);
					positions = ce.getPositions();
				}
				if (StringUtils.isNotBlank(posname)) {//判断传入岗位是否都存在
					String[] arr1 = posname.replaceAll("丨", "|").split("\\|");
					byte b;
					int i;
					String[] arrayOfString1;
					for (i = (arrayOfString1 = arr1).length, b = 0; b < i;) {
						String s = arrayOfString1[b];
						String k = String.format("%s-%s-%s-%s",
								new Object[] { ce.getDepartment(), ce.getWorkShop(), group, s });
						PositionEntity p = posts.get(k);
						if (p == null) {
							throw new ServiceException(String.format("岗位[%s]不存在", new Object[] { k }));
						}
						String courseIds = p.getIds();
						if (!StringUtils.isNotBlank(courseIds) || ce.getId() == null
								|| !Arrays.<String>asList(courseIds.split(",")).contains(ce.getId().toString())) {
							//传入id和数据库id匹配
							if (ce.getId() != null) {
								CoursePositionEntity cp = new CoursePositionEntity();
								cp.setCourseId(ce.getId());
								cp.setPositionId(p.getId());
								cplist.add(cp);
							}
							if (positions != null)
								positions.add(p);
						}
						b++;
					}

				}
			}
			list = new ArrayList<>();
			for (CourseEntity ce : courseMap.values()) {
				if (ce.getId() == null)
					list.add(ce);
			}
			if (CollectionUtils.isNotEmpty(list)) {
				insertBatchOnce(list);
				for (CourseEntity course : list) {
					Long id = course.getId();
					if (CollectionUtils.isNotEmpty(course.getPositions())) {
						for (PositionEntity p : course.getPositions()) {
							CoursePositionEntity cp = new CoursePositionEntity();
							cp.setCourseId(id);
							cp.setPositionId(p.getId());
							cplist.add(cp);
						}
					}
				}
			}
			if (cplist.size() > 0) {
				this.coursePositionDao.insertBatchOnce(cplist);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	public List<CourseEntity> findExportList(CourseEntity query) {
		return this.CourseDao.findExportList(query);
	}
}