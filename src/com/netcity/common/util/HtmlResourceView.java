package com.netcity.common.util;

import java.io.File;
import java.util.Locale;
import org.springframework.web.servlet.view.InternalResourceView;

public class HtmlResourceView extends InternalResourceView {
	public boolean checkResource(Locale locale) throws Exception {
		File file = new File(String.valueOf(getServletContext().getRealPath("/")) + getUrl());
		System.out.println(file.exists());
		return file.exists();
	}
}