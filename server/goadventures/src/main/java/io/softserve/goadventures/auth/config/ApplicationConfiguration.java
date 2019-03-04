package io.softserve.goadventures.auth.config;

//import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import io.softserve.goadventures.auth.filters.LoginFilter;
import io.softserve.goadventures.auth.service.JWTService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ApplicationConfiguration {
    @Bean
    public FilterRegistrationBean dawsonApiFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LoginFilter(new JWTService()));
// specific URl patterns
        registration.addUrlPatterns("/res/reg");
        //registration.addUrlPatterns("/content/*");
        return registration;
    }
}