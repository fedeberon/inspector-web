package com.ideaas.web;

/**
 * Created by federicoberon on 24/10/2019.
 */


import com.ideaas.services.bean.FileStorageProperties;
import com.ideaas.services.bean.GoogleMapsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Slf4j
@EnableTransactionManagement
@EnableConfigurationProperties({
        FileStorageProperties.class, GoogleMapsProperties.class
})
@SpringBootApplication(scanBasePackages = {"com.ideaas"})
public class ScopesiWebApplication {

    @Value("${spring.profiles.active:unknown}")
    private String activeProfile;

    // Timezone adjustment for reservation testing
    @PostConstruct
    public void setTimeZone() {
        if("dev".equals(activeProfile)||"prod-py".equals(activeProfile)) {
            log.warn("BE CAREFUL!!! The default UTC time is being set to UTC-00:00");
            TimeZone.setDefault(TimeZone.getTimeZone("UTC-00:00"));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ScopesiWebApplication.class, args);
    }

}
