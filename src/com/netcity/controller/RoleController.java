package com.netcity.controller;

import com.netcity.aspect.SystemLog;
import com.netcity.module.entity.RoleColumnEntity;
import com.netcity.module.entity.RoleDepartmentEntity;
import com.netcity.module.entity.RoleEntity;
import com.netcity.module.entity.RoleRightEntity;
import com.netcity.module.entity.UserEntity;
import com.netcity.module.service.RoleDepartmentService;
import com.netcity.module.service.RoleService;
import com.netcity.shiro.MyShiroRealm;
import com.netcity.util.LayuiPageInfo;
import com.netcity.util.QueryResult;
import com.netcity.util.ResponseFlag;
import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/role" })
public class RoleController extends BaseController {
	@Autowired
	MyShiroRealm myShiroRealm;
	@Autowired
	RoleService roleService;
	@Autowired
	RoleDepartmentService deptService;

	@RequestMapping({ "/index.html" })
	public String index() {
		return "role/index";
	}

	@RequiresPermissions({ "role:query" })
	@RequestMapping(value = { "/queryPage.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public LayuiPageInfo queryPage(@RequestParam("page") int pageNum, @RequestParam("limit") int pageSize,
			RoleEntity role) {
		LayuiPageInfo result = new LayuiPageInfo();
		try {
			result = this.roleService.listByPage(role, pageNum, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			result.setMsg("查询失败");
			result.setCode(1);
			result.setCount(Long.valueOf(0L));
		}
		return result;
	}

	@RequiresPermissions({ "role:grant" })
	@SystemLog(action = "启用角色")
	@RequestMapping(value = { "/grant.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag grant(@RequestBody RoleEntity role) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.roleService.updateEntity(role);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "role:create" })
	@SystemLog(action = "增加角色")
	@RequestMapping(value = { "/create.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag create(@RequestBody RoleEntity role) {
		ResponseFlag res = new ResponseFlag();
		try {
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			role.setCreateUser(user.getUsercode());
			this.roleService.insert(role);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "role:update" })
	@SystemLog(action = "编辑角色")
	@RequestMapping(value = { "/update.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag update(@RequestBody RoleEntity role) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.roleService.updateEntity(role);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "role:delete" })
	@SystemLog(action = "禁用角色")
	@RequestMapping(value = { "/delete.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag delete(@RequestParam("ids") String ids) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.roleService.deleteByIds(ids);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "user:addDept" })
	@SystemLog(action = "给用户关联部门")
	@RequestMapping(value = { "/addDept.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag addDept(@RequestParam("ids") String ids, @RequestParam("id") Long id) {
		ResponseFlag res = new ResponseFlag();
		try {
			List<RoleDepartmentEntity> list = new ArrayList<>();
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			String[] idlist = ids.split(",");
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = idlist).length, b = 0; b < i;) {
				String string = arrayOfString1[b];
				if (!string.isEmpty()) {

					RoleDepartmentEntity e = new RoleDepartmentEntity();
					e.setRoleId(id);
					e.setDeptId(Long.valueOf(string));
					e.setCreateUser(user.getUsercode());
					list.add(e);
				}
				b++;
			}
			this.deptService.addDept(id, list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "role:addColumn" })
	@SystemLog(action = "给角色关联栏目")
	@RequestMapping(value = { "/addColumn.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag addColumn(@RequestParam("ids") String ids, @RequestParam("id") Long id) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.myShiroRealm.clearCachedAuthorizationInfoByRoleId(id);
			List<RoleColumnEntity> list = new ArrayList<>();
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			String[] idlist = ids.split(",");
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = idlist).length, b = 0; b < i;) {
				String string = arrayOfString1[b];
				if (!string.isEmpty()) {

					RoleColumnEntity e = new RoleColumnEntity();
					e.setRoleId(id);
					e.setColumnId(Long.valueOf(string));
					e.setCreateUser(user.getUsercode());
					list.add(e);
				}
				b++;
			}
			this.roleService.addColumn(id, list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequiresPermissions({ "role:addRight" })
	@SystemLog(action = "给角色关联操作权限")
	@RequestMapping(value = { "/addRight.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public ResponseFlag addRight(@RequestParam("ids") String ids, @RequestParam("id") Long id) {
		ResponseFlag res = new ResponseFlag();
		try {
			this.myShiroRealm.clearCachedAuthorizationInfoByRoleId(id);
			List<RoleRightEntity> list = new ArrayList<>();
			UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
			String[] idlist = ids.split(",");
			byte b;
			int i;
			String[] arrayOfString1;
			for (i = (arrayOfString1 = idlist).length, b = 0; b < i;) {
				String string = arrayOfString1[b];
				if (!string.isEmpty()) {

					RoleRightEntity e = new RoleRightEntity();
					e.setRoleId(id);
					e.setRightId(Long.valueOf(string));
					e.setCreateUser(user.getUsercode());
					list.add(e);
				}
				b++;
			}
			this.roleService.addRight(id, list);
			res.setFlag(ResponseFlag.Success);
			res.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setFlag(ResponseFlag.Failed);
			res.setMessage("保存失败");
		}
		return res;
	}

	@RequestMapping(value = { "/queryall.do" }, method = { RequestMethod.POST })
	@ResponseBody
	public QueryResult queryClass() {
		QueryResult result = new QueryResult();
		try {
			RoleEntity query = new RoleEntity();
			query.setState(Long.valueOf(1L));
			List<RoleEntity> list = this.roleService.findList(query);
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