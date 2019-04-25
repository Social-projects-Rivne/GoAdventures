package io.softserve.goadventures.filters;

import io.softserve.goadventures.services.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    private HttpServletResponse setUnauthorized(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(401);
        return httpResponse;
    }

    @Override
    public void init(FilterConfig filterconfig) {}
}