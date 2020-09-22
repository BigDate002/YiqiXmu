package com.netcity.module.service.impl;

import com.netcity.base.dao.BaseDao;
import com.netcity.base.entity.BaseEntity;
import com.netcity.exception.ServiceException;
import com.netcity.module.dao.CodeDao;
import com.netcity.module.entity.CodeEntity;
import com.netcity.module.service.CodeService;
import com.netcity.service.impl.BaseServiceImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("codeService")
public class CodeServiceImpl<T extends BaseEntity> extends BaseServiceImpl<CodeEntity> implements CodeService {
	@Autowired
	CodeDao CodeDao;

	public BaseDao<CodeEntity> getDao() {
		return (BaseDao<CodeEntity>) this.CodeDao;
	}

	public int createNextNo(String prefix) throws ServiceException {
		int no = 0;
		try {
			CodeEntity code = new CodeEntity();
			code.setPrefix(prefix);
			List<CodeEntity> codelist = findList(code);
			if (codelist.size() > 0) {
				code = codelist.get(0);
				code.setNo(Long.valueOf(code.getNo().longValue() + 1L));
				updateEntity(code);
			} else {
				code.setNo(Long.valueOf(1L));
				insert(code);
			}
			no = code.getNo().intValue();
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return no;
	}
}