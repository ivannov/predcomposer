package com.nosoftskills.predcomposer.web;

import com.nosoftskills.predcomposer.model.User;
import com.nosoftskills.predcomposer.session.UserContext;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ivan St. Ivanov
 */
@WebFilter(urlPatterns = { "*.jsf", "*.xhtml", "*.faces" })
public class LoggedUserFilter implements Filter {

    @Inject
    private UserContext userContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String reqURI = ((HttpServletRequest) request).getRequestURI();
        User loggedUser = userContext.getLoggedUser();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (reqURI.contains("javax.faces.resource") || reqURI.contains("login.xhtml")) {
            chain.doFilter(request, response);
        } else if (loggedUser == null) {
            httpServletResponse.sendRedirect("login.xhtml");
        } else if (reqURI.contains("admin/") && !loggedUser.getIsAdmin()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.getWriter().write("Forbidden");
            httpServletResponse.getWriter().flush();
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
