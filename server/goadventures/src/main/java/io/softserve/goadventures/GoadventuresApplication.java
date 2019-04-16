package io.softserve.goadventures;
// TODO remove unused imports
import io.softserve.goadventures.configurations.ApplicationConfiguration;
import io.softserve.goadventures.configurations.CorsConfiguration;
import io.softserve.goadventures.configurations.WebAppConfig;
import io.softserve.goadventures.configurations.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@Import({CorsConfiguration.class, WebAppConfig.class, ApplicationConfiguration.class})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
//TODO Use camel case
public class GoadventuresApplication {
  public static void main(String[] args) {
    SpringApplication.run(GoadventuresApplication.class, args);
  }
}

// TODO the files module should a folder in the resources folder

//TODO change all the logic with throwing exceptions. Service method can return null. Do not throw exception to create some logic. You will produce lots of trash in console output and log file.
