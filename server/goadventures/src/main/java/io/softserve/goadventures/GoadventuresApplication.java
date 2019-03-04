package io.softserve.goadventures;

import io.softserve.goadventures.configs.CorsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CorsConfiguration.class)
public class GoadventuresApplication {
  public static void main(String[] args) {
    SpringApplication.run(GoadventuresApplication.class, args);
  }

//	@Bean
//	public FilterRegistrationBean corsFilterRegistration() {
//		FilterRegistrationBean registrationBean =
//				new FilterRegistrationBean(new CORSFilter());
//		registrationBean.setName("CORS Filter");
//		registrationBean.addUrlPatterns("/*");
//		registrationBean.setOrder(1);
//		return registrationBean;
//	}
}
