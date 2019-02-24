package io.softserve.goadventures.auth.filters;

import io.softserve.goadventures.auth.providers.JWTProvider;
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

//@WebFilter("/reg/")
@Component
public class LoginFilter implements Filter {

  @Autowired
  JWTProvider jwtProvider;

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterchain)
          throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    String token = request.getHeader("Authorization");
    if (token != null) {
      if (jwtProvider.parseToken(token) != null) {
        filterchain.doFilter(request, response);
      } else {
        response.sendRedirect("/auth/sign-in");
      }
    }
//            HttpSession session = request.getSession(false);
//            String loginURI = request.getContextPath() + "/auth/login";
//            boolean loggedIn = session != null && session.getAttribute("user") != null;
//            boolean loginRequest = request.getRequestURI().equals(loginURI);
//
//            if (loggedIn || loginRequest) {
//                filterchain.doFilter(request, response);
//            } else {
//                response.sendRedirect(loginURI);
//            }
    // filterchain.doFilter(request, response);
  }

  @Override
  public void init(FilterConfig filterconfig) throws ServletException {
  }
}