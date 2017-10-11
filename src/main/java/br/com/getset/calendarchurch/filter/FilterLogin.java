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

import br.com.getset.calendarchurch.model.Usuario;

public class FilterLogin implements Filter {

    public FilterLogin() {}

	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Usuario usuario =(Usuario) request.getSession().getAttribute("usuario");
		
		if (!(request.getRequestURI().equals("/calendarChurch/login.jsf") || request.getRequestURI().equals("/calendarChurch/"))
				&& !request.getRequestURI().endsWith(".js.jsf")
				&& !request.getRequestURI().endsWith(".css.jsf")
				&& !request.getRequestURI().endsWith(".png.jsf")
				&& !request.getRequestURI().contains("/rest/")
				&& !request.getRequestURI().contains("/fonts/")
				&& !request.getRequestURI().contains("/img/")
				&& usuario == null) {
			System.out.println("URI Bloqueada no filtro login: " + request.getRequestURI());
			response.sendRedirect("/calendarChurch/login.jsf");
		}else{
			chain.doFilter(req, resp);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
