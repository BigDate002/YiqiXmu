package com.netcity.controller;

import com.netcity.module.entity.DictEntity;
import com.netcity.module.service.DictService;
import com.netcity.util.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping({ "/dict" })
public class DictController {

	@Autowired
	DictService dictService;

	@RequestMapping(value = { "/queryDict.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult postCategory(@RequestParam("dictType")String dictType) {
		QueryResult result = new QueryResult();
		try {
			List<DictEntity> list = dictService.queryDictByDictType(dictType);
			result.setData(list);
			result.setFlag(QueryResult.Success);
			result.setMessage("查询成功");
		} catch (Exception e) {
			result.setFlag(QueryResult.Failed);
			result.setMessage("查询失败");
		}
		return result;
	}


}
