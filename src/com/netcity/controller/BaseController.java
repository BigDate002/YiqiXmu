package com.netcity.controller;

import com.netcity.base.entity.BaseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class BaseController {
	@InitBinder
	public void webInitBinder(WebDataBinder binder) {
		binder.registerCustomEditor(BaseEntity.class, new BaseEntityEditor());
	}
}