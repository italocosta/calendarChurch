package br.com.getset.calendarchurch.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterSession implements Filter {

	public FilterSession() {
	}

	public void destroy() {
	}		

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		try {
			// chain...
			if (isAjax(request) && !request.isRequestedSessionIdValid()) {
				// log.warn("Session expiration during ajax request, partial
				// redirect to login page");
				response.getWriter().print(xmlPartialRedirectToPage(request, "/login?session_expired=1"));
				response.flushBuffer();
			} else {
				chain.doFilter(req, resp);
			}
		} catch (Exception e) {
			// redirect to error page
			//request.getSession().setAttribute("lastException", e);
			//request.getSession().setAttribute("lastExceptionUniqueId", e.hashCode());
			// log.error("EXCEPTION unique id: " + e.hashCode(), e);
			if (!isAjax(request)) {
				response.sendRedirect(request.getContextPath() + request.getServletPath() + "/error");
			} else {
				// let's leverage jsf2 partial response
				response.getWriter().print(xmlPartialRedirectToPage(request, "/error"));
				response.flushBuffer();
			}
		}

	}

	private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<partial-response><redirect url=\"").append(request.getContextPath())
				.append(request.getServletPath()).append(page).append("\"/></partial-response>");
		return sb.toString();
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
