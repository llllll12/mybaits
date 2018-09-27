package com.dcpro.demo.controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class MyFilter
 */
@WebFilter("/*")
public class MyFilter implements Filter {

	public MyFilter() {

	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String requestURL = req.getRequestURI().toLowerCase();
		if (requestURL.indexOf("login.jsp") == -1 && requestURL.indexOf("csservlet") == -1 && requestURL.indexOf("captcha") == -1 && requestURL.indexOf("dc") == -1) {
			
			HttpSession session = req.getSession();
			
			String isLogin = (String) session.getAttribute("isLogin");
			
			if ("".equals(isLogin) || null == isLogin) {
				session.setAttribute("filtermsg", "你还没有登录，请登录后再访问");
				resp.sendRedirect("/dcpro/login.jsp");
				return;
			}
		}
		
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
