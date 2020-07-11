package com.github.imtiazea.taskmgr.config;

import com.github.imtiazea.SimpleNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    @Bean
    public SimpleNotifier simpleNotifier() {
        return new SimpleNotifier();
    }
}
