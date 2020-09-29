package com.offcn.order.config;

import com.offcn.util.OssTemplat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProjectConfig {
    @ConfigurationProperties(prefix = "oss")
    @Bean
    public OssTemplat ossTemplate(){
        return  new OssTemplat();
    }
}
