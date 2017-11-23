package com.newland.financial.p2p.common.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "exceptionProp")
public class ExceptionMapping {
    @Getter
    @Setter
    private Map<String, String> exceptionMap = new HashMap<String, String>();
}
