package io.softserve.goadventures.configurations;

import io.softserve.goadventures.filters.LoginFilter;
import io.softserve.goadventures.services.JWTService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationConfiguration {
    @Bean
    public FilterRegistrationBean dawsonApiFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter(new JWTService()));
        registration.addUrlPatterns("/res/reg");
        return registration;
    }
}