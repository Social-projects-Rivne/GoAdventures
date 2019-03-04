package io.softserve.goadventures.auth.filters;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import io.softserve.goadventures.user.model.User;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

    //@WebFilter("/reg/")
    @Component
    public class LoginFilter implements Filter {
        @Override
        public void destroy() {}

        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain)
                throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            HttpSession session = request.getSession(false);
            String loginURI = request.getContextPath() + "/auth/login";
            boolean loggedIn = session != null && session.getAttribute("user") != null;
            boolean loginRequest = request.getRequestURI().equals(loginURI);

            if (loggedIn || loginRequest) {
                filterchain.doFilter(request, response);
            } else {
                response.sendRedirect(loginURI);
            }
            //filterchain.doFilter(request, response);
        }

        @Override
        public void init(FilterConfig filterconfig) throws ServletException {}
    }