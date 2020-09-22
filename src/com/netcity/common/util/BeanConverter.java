package com.netcity.common.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.netcity.base.entity.BaseEntity;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;

public class BeanConverter extends JsonDeserializer<BaseEntity> {
	private Class<? extends BaseEntity> targetClass;

	public BeanConverter() {
	}

	public BeanConverter(Class<? extends BaseEntity> targetClass) {
		this.targetClass = targetClass;
	}

	public BaseEntity deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		String source = p.getText().trim();
		System.out.println(source);
		source = StringEscapeUtils.escapeHtml4(source);
		BaseEntity clazz = JSON.parseObject(source, this.targetClass);
		return clazz;
	}
}