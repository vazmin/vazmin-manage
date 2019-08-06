package com.github.vazmin.manage.component.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Properties specific to application.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Map<String, String> txMap = new HashMap<>();

    public Map<String, String> getTxMap() {
        return txMap;
    }
}
