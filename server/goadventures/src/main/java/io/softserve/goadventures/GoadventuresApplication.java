package io.softserve.goadventures;

import io.softserve.goadventures.configs.CorsConfiguration;
import io.softserve.goadventures.configs.WebAppConfig;
import io.softserve.goadventures.avatarUploadDownload.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;



@SpringBootApplication
@Import({CorsConfiguration.class, WebAppConfig.class})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class GoadventuresApplication {
  public static void main(String[] args) {
    SpringApplication.run(GoadventuresApplication.class, args);
  }
}
