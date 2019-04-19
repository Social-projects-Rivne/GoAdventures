package io.softserve.goadventures;

import io.softserve.goadventures.configurations.ApplicationConfiguration;
import io.softserve.goadventures.configurations.CorsConfiguration;
import io.softserve.goadventures.configurations.FileStorageProperties;
import io.softserve.goadventures.configurations.WebAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({CorsConfiguration.class, WebAppConfig.class, ApplicationConfiguration.class})
@EnableConfigurationProperties({
        FileStorageProperties.class
})

public class GoAdventuresApplication {
  public static void main(String[] args) {
    SpringApplication.run(GoAdventuresApplication.class, args);
  }
}

// TODO the files module should a folder in the resources folder

//TODO change all the logic with throwing exceptions. Service method can return null. Do not throw exception to create some logic. You will produce lots of trash in console output and log file.
