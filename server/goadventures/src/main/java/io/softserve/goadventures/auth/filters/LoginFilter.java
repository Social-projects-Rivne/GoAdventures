package io.softserve.goadventures.auth.filters;

import io.softserve.goadventures.auth.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
                    filterchain.doFilter(request, response);
                } else {
                    setUnauthorized(response);
                }
            } else {
                setUnauthorized(response);
            }
        }

        public HttpServletResponse setUnauthorized(ServletResponse response) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(401);
            return httpResponse;
        }

        @Override
        public void init(FilterConfig filterconfig) throws ServletException {}
    }