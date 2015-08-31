package com.nosoftskills.predcomposer.user;

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
@WebFilter(urlPatterns = { "*.xhtml" })
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
        if (!reqURI.contains("login.xhtml") && userContext.getLoggedUser() == null) {
            ((HttpServletResponse) response).sendRedirect("login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
