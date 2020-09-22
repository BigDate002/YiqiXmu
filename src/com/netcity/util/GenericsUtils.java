package com.netcity.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericsUtils {
	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		Type genType = clazz.getGenericSuperclass(); // 得到泛型公共父类Type
		if (!(genType instanceof ParameterizedType)) {// 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
			return Object.class;
		}
		// 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class
        // 如BuyerServiceBean extends DaoSupport<Buyer,Contact>就返回Buyer和Contact类型
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (!(params[0] instanceof Class<?>)) {// 如果返回的不是Class类型或其子类类型，直接返回Object.class
			return Object.class;
		}
		return (Class<?>) params[0];
	}
}