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
                .name(NotificationTopics.NOTIF_REGISTER_ACCOUNT)
                .build();
    }

    @Bean
    public NewTopic notifAddProduct() {
        return TopicBuilder
                .name(NotificationTopics.NOTIF_ADD_PRODUCT)
                .build();
    }

    @Bean
    public NewTopic notifUpdateProduct() {
        return TopicBuilder
                .name(NotificationTopics.NOTIF_UPDATE_PRODUCT)
                .build();
    }

    @Bean
    public NewTopic notifDeleteProduct() {
        return TopicBuilder
                .name(NotificationTopics.NOTIF_DELETE_PRODUCT)
                .build();
    }

    @Bean
    public NewTopic notifNewTransaction() {
        return TopicBuilder
                .name(NotificationTopics.NOTIF_NEW_TRANSACTION)
                .build();
    }


}
