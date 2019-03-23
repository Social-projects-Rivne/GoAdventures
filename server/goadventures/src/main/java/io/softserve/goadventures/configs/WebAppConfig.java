package io.softserve.goadventures.configs;

import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Properties;

@Configuration
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

}
