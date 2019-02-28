package io.softserve.goadventures.auth.config;

//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//public class CORSFilter extends GenericFilterBean implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
////        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//
//        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
////        httpResponse.setHeader("Access-Control-Allow-Headers",
////                "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Csrf-Token, Authorization");
//
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
//        httpResponse.setHeader("Access-Control-Max-Age", "3600");
//
//        System.out.println("********** CORS Configuration Completed **********");
//
//        chain.doFilter(request, response);
//    }
//
//
//} // The End...

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("localhost:3001")
                .allowCredentials(false)
                .maxAge(3600);
    }
}