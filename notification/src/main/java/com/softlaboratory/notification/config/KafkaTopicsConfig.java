package com.softlaboratory.notification.config;

import notification.constant.NotificationTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic notifRegisterAccount() {
        return TopicBuilder
                .name(NotificationTopics.NOTIF_PUSH_NEW)
                .build();
    }

}
