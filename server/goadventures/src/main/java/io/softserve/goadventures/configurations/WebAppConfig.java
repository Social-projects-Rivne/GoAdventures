package io.softserve.goadventures.configurations;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.modelmapper.config.Configuration.*;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebAppConfig {
  @Bean
  public ModelMapper modelMapper() {
    Condition notEmpty = ctx -> ctx.getSource() != "";
//    Condition notZero = ctx -> ctx.getSource() != 0;
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STANDARD)
            .setPropertyCondition(notEmpty)
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(AccessLevel.PRIVATE)
            .setSkipNullEnabled(true);
    return mapper;
  }

  @Bean(name = "commonsMultipartResolver")
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

}
