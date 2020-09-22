package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;
import java.util.List;

public class ColumnEntity extends BaseEntity {
	private static final long serialVersionUID = 5972611552493688734L;
	private String code;
	private String name;
	private String url;
	private Long parentId;
	private String descript;
	private String icon;
	private Long visible;
	private Boolean checked;
	private Long roleId;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		setTitle(name);
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getChecked() {
		return this.checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	private boolean open = true;
	private List<RightEntity> children;

	public boolean isOpen() {
		return this.open;
	}

	private String title;
	private List<ColumnEntity> childrens;
	private Long sort;

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<RightEntity> getChildren() {
		return this.children;
	}

	public void setChildren(List<RightEntity> children) {
		this.children = children;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ColumnEntity> getChildrens() {
		return this.childrens;
	}

	public void setChildrens(List<ColumnEntity> childrens) {
		this.childrens = childrens;
	}

	public Long getVisible() {
		return this.visible;
	}

	public void setVisible(Long visible) {
		this.visible = visible;
	}

	public Long getSort() {
		return this.sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
}