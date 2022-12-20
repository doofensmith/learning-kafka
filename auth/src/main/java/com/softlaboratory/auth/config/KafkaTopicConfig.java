package com.softlaboratory.auth.config;

import auth.kafka.topic.AuthTopic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic registerTopic() {
        return TopicBuilder
                .name(AuthTopic.AUTH_REGISTER_ACCOUNT)
                .build();
    }

}
