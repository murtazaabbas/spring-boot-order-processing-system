package com.melitaltd.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "api.thirdparty")
public class SecurityProperties {
    private String key;
    private String secret;
}
