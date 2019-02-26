package io.softserve.goadventures.auth.filters;

import io.softserve.goadventures.auth.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    //@WebFilter("/reg/")
    @Component
    public class LoginFilter implements Filter {
        @Autowired
        private JWTService jwtService = new JWTService();

        @Override
        public void destroy() {}

        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain)
                throws IOException, ServletException {

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String loginURI = request.getContextPath() + "/auth/signin";
            String token = request.getHeader("Authorization");
            System.out.println("In filter " + token);
            if (token != null) {
                if (jwtService.parseToken(token) != null) {
                    filterchain.doFilter(request, response);
                } else {
                    response.sendRedirect(loginURI);
                }
            } else {
                response.sendRedirect(loginURI);
            }
        }

            /*
            //HttpSession session = request.getSession(false);
            //boolean loggedIn = session != null && session.getAttribute("user") != null;
            //boolean loginRequest = request.getRequestURI().equals(loginURI);
            if (loggedIn || loginRequest) {
                filterchain.doFilter(request, response);
            } else {
                response.sendRedirect(loginURI);
            }
            //filterchain.doFilter(request, response);
        }*/

        @Override
        public void init(FilterConfig filterconfig) throws ServletException {}
    }