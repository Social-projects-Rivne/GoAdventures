package io.softserve.goadventures.configs;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.MultipartConfigElement;
import java.util.Objects;
import java.util.Properties;


@Configuration
@ComponentScan
@EnableAutoConfiguration
public class WebAppConfig {
  // Model Mapper config
  private Properties properties;
  @Bean
  public ModelMapper modelMapper() {
    Condition notEmpty = ctx -> ctx.getSource() != "";
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STANDARD)
            .setPropertyCondition(notEmpty)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true);
    return mapper;
  }

  @Bean(name = "commonsMultipartResolver")
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }










}
