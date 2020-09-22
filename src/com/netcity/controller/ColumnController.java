package com.netcity.controller;

import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.service.ColumnService;
import com.netcity.util.LayuiPageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/column" })
public class ColumnController {
	@Autowired
	ColumnService columnService;

	@RequestMapping(value = { "/query.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestBody ColumnEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			List<ColumnEntity> list = this.columnService.findList(query);

			long id = ((ColumnEntity) list.stream().filter(x -> x.getName().equals("个人中心")).findFirst().get()).getId().longValue();
			list.removeIf(x -> !(x.getId().longValue() != id && x.getParentId().longValue() != id));
			result.setCode(0);
			result.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("查询失败");
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}
}