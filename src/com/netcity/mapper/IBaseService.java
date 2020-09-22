package com.netcity.mapper;

import com.alibaba.fastjson.JSONObject;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface IBaseService {
	@Select({ "select * from ${table} where id = #{id}" })
	JSONObject find(@Param("table") String paramString, @Param("id") Long paramLong);

	@Select({ "select * from ${table}" })
	List<JSONObject> findList(@Param("table") String paramString);
}