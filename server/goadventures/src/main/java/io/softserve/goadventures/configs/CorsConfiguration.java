package io.softserve.goadventures.configs;

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
            .allowedOrigins("http://localhost:3000", "http://localhost:3001")
            .allowCredentials(false)
            .allowedHeaders("Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Csrf-Token, Authorization")
            .allowedMethods("POST, GET, PUT, OPTIONS, DELETE")
            .maxAge(3600);
  }
}