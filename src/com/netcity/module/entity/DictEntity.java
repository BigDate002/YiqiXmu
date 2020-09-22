package com.netcity.module.entity;

import com.netcity.base.entity.BaseEntity;

public class DictEntity extends BaseEntity {
	private static final long serialVersionUID = 7824219502745573807L;
	//字典名称  岗位类别
	private String dictName;
	//字典类型 sys_post_category
	private String dictType;
	//字典标签 特殊特性 检验员
	private String dictLabel;
	//字典键值 0 1
	private String dictValue;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictLabel() {
		return dictLabel;
	}

	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
		this.name = dictLabel;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
}
