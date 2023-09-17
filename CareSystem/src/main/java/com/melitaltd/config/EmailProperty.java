package com.melitaltd.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.mail.common")
public class EmailProperty {
    private String subjectAutoApprove;
    private String subjectManualApprove;
    private String autoApprovalBody;
    private String manualApprovalBody;
    private String from;
    private String to;

}
