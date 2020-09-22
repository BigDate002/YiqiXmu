package com.netcity.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;

public class StringXssDeserializer extends JsonDeserializer<Object> implements ContextualDeserializer {
	private Class<?> targetClass;

	public StringXssDeserializer() {
	}

	public StringXssDeserializer(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String source = p.getText().trim();

		source = StringEscapeUtils.escapeHtml4(source);
		Object clazz = JSON.parseObject(source, this.targetClass);
		return clazz;
	}

	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
			throws JsonMappingException {
		this.targetClass = ctxt.getContextualType().getRawClass();
		return new StringXssDeserializer(this.targetClass);
	}
}