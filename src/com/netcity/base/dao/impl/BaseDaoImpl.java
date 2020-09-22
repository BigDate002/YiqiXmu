package com.netcity.base.dao.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.util.GenericsUtils;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {
	Logger logger;

	protected Class<?> entityClass;

	protected String sqlMapNamespace;

	@Autowired
	@Qualifier("sqlsessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate;

	public static final String SQL_SELECTBYID = "selectById";

	public static final String SQL_SELECTBYENTITY = "selectByEntity";

	public static final String SQL_SELECTBYCOND = "selectByCond";

	public static final String SQL_UPDATEROW = "updateById";

	public static final String SQL_UPDATEROWS = "updateByIds";

	public static final String SQL_DELETEROW = "deleteById";

	public static final String SQL_DELETEROWS = "deleteByIds";

	public static final String SQL_INSERT = "insert";

	public static final String SQL_INSERTBATCHONCE = "insertBatchOnce";

	public BaseDaoImpl() {
		this.entityClass = GenericsUtils.getSuperClassGenricType(getClass());
		this.sqlMapNamespace = this.entityClass.getName();
		this.sqlMapNamespace = this.sqlMapNamespace.substring(0, this.sqlMapNamespace.length() - 6);
		this.logger = Logger.getLogger(getClass());
	}

	public String getSQL(String SQL_ID) {
		return this.sqlMapNamespace + "." + SQL_ID;
	}

	public T getEntity(Long id) {
		return this.sqlSessionTemplate.selectOne(getSQL("selectById"), id);
	}

	public List<T> findList(T t) {
		return this.sqlSessionTemplate.selectList(getSQL("selectByEntity"), t);
	}

	public List<T> findList(Map<String, Object> cond) {
		return this.sqlSessionTemplate.selectList(getSQL("selectByCond"), cond);
	}

	public void updateRow(T t) {
		this.sqlSessionTemplate.update(getSQL("updateById"), t);
	}

	public void deleteById(Long id) {
		this.sqlSessionTemplate.delete(getSQL("deleteById"), id);
	}

	public void insert(T t) {
		this.sqlSessionTemplate.insert(getSQL("insert"), t);
	}

	public PageInfo<T> listByPage(T t, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = this.sqlSessionTemplate.selectList(getSQL("selectByEntity"), t);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}

	public void deleteByIds(String ids) {
		this.sqlSessionTemplate.delete(getSQL("deleteByIds"), ids);
	}

	public void updateByIds(T t) {
		this.sqlSessionTemplate.delete(getSQL("updateByIds"), t);
	}

	public void insertBatchOnce(List<T> list) {
		this.sqlSessionTemplate.insert(getSQL("insertBatchOnce"), list);
	}
}
