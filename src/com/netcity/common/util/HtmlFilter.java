package com.netcity.common.util;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class HtmlFilter {
	public static String xssClean(String value) {
		AntiSamy antiSamy = new AntiSamy();
		try {
			Policy policy = Policy.getInstance(
					String.valueOf(Thread.currentThread().getContextClassLoader().getResource("").getPath())
							+ "antisamy-myspace.xml");
			CleanResults cr = antiSamy.scan(value, policy);
			return cr.getCleanHTML();
		} catch (ScanException e) {
			e.printStackTrace();
		} catch (PolicyException e) {
			e.printStackTrace();
		}
		return value;
	}
}