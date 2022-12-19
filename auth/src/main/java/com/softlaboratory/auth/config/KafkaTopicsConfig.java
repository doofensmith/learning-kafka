package com.softlaboratory.auth.config;

import auth.constant.AuthTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic registerTopic() {
        return TopicBuilder
                .name(AuthTopics.AUTH_REGISTER_ACCOUNT)
                .build();
    }

}
