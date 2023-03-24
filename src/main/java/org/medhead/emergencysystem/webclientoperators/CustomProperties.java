package org.medhead.emergencysystem.webclientoperators;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="org.medhead.emergencysystem.webclientoperators")
public class CustomProperties {

    private String apiUrlOperators;
    private String apiUrlIncidents;
    private String apiUrlHospitals;

}

