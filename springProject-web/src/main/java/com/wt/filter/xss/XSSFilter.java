/*
 * @(#)XSSFilter.java 2016年5月21日  
 */
package com.wt.filter.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * @author Sally
 * @date 2016年5月21日
 */
public class XSSFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
