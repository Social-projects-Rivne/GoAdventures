package io.softserve.goadventures.auth.config;

import io.softserve.goadventures.auth.filters.LoginFilter;
import io.softserve.goadventures.auth.service.JWTService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAsync
@EnableScheduling
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