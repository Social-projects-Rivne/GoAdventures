package io.softserve.goadventures.auth.filters;

import io.softserve.goadventures.auth.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;

    //@WebFilter("/reg/")
    @Component
    public class LoginFilter implements Filter {
        private final JWTService jwtService;

        @Autowired
        public LoginFilter(JWTService jwtService) {
            this.jwtService = jwtService;
        }

        @Override
        public void destroy() {}


        public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain)
                throws IOException, ServletException {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            String token = request.getHeader("Authorization");
            if (token != null) {
                if (jwtService.parseToken(token) != null) {
                    setOk(response);
                    filterchain.doFilter(request, response);
                } else {
                    setUnauthorized(response);
                }
            } else {
                setUnauthorized(response);
            }
        }

        public HttpServletResponse setOk(ServletResponse response) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(200);
            return httpResponse;
        }

        public HttpServletResponse setUnauthorized(ServletResponse response) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(401);
            return httpResponse;
        }

        @Override
        public void init(FilterConfig filterconfig) throws ServletException {}
    }