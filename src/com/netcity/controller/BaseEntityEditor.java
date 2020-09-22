package com.netcity.controller;

import com.netcity.common.util.HtmlFilter;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Field;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

public class BaseEntityEditor extends PropertyEditorSupport {
	public void setValue(Object value) {
		Field[] fields = value.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getType().getName().equals(String.class.getName())) {

				fields[i].setAccessible(true);
				try {
					Object val = fields[i].get(value);
					if (val != null) {
						String text = val.toString();
						if (!StringUtils.isBlank(text)) {
							fields[i].set(value, HtmlFilter.xssClean(text));
						}
						System.out.println("替换后-----" + fields[i].get(value));
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		super.setValue(value);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			return;
		}

		try {
			super.setValue(HtmlUtils.htmlEscape(text));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}