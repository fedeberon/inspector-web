package com.ideaas.services.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by federicoberon on 04/12/2019.
 */

@Component
@ConfigurationProperties(prefix = "google")
public class GoogleMapsProperties {


    private String mapKey;

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }
}
