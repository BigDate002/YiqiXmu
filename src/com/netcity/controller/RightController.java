package com.netcity.controller;

import com.netcity.module.entity.ColumnEntity;
import com.netcity.module.entity.RightEntity;
import com.netcity.module.service.ColumnService;
import com.netcity.module.service.RightService;
import com.netcity.util.LayuiPageInfo;
import java.util.List;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/right" })
public class RightController {
	@Autowired
	RightService rightService;
	@Autowired
	ColumnService columnService;

	@RequestMapping(value = { "/query.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestBody ColumnEntity query) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			query.setChecked(Boolean.valueOf(true));
			List<ColumnEntity> list = this.columnService.findList(query);
			list.forEach(e -> {
				if (e.getParentId().longValue() > 0L || !"#".equals(e.getUrl())) {
					RightEntity r = new RightEntity();
					r.setColumnId(e.getId());
					r.setRoleId(query.getRoleId());
					List<RightEntity> rights = this.rightService.findList(r);
					e.setChecked(Boolean.valueOf(false));
					e.setChildren(rights);
				}
			});
			list.removeIf(x -> CollectionUtils.isEmpty(x.getChildren()));
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