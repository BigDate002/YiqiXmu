package com.netcity.base.entity;

import java.io.Serializable;
import java.util.List;

public class LayTree extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 5157246944348522444L;
	private Long id;
	private String name;
	private String level;
	private Long pid;
	private List<LayTree> children;
	private Boolean spread = Boolean.valueOf(false);

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LayTree> getChildren() {
		return this.children;
	}

	public void setChildren(List<LayTree> children) {
		this.children = children;
	}

	public Boolean getSpread() {
		return this.spread;
	}

	public void setSpread(Boolean spread) {
		this.spread = spread;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
}