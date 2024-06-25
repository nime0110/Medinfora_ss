package com.spring.app.common;

import javax.servlet.http.HttpServletRequest;

public class Myutil {

	/**
	 * include parameter URL
	 * @param {HttpSevletRequest} SevletRequest
	 * @return {String} inculde request parameter URL
	*/
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		
		String queryString = request.getQueryString();

		if(queryString != null) {
			currentURL += '?' + queryString;
		}
		
		String ctxPath = request.getContextPath();
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();	
		
		currentURL = currentURL.substring(beginIndex);

		return currentURL;
	}
	
}
