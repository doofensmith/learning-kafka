package com.softlaboratory.transaction.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import transaction.constant.TransactionTopic;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic newTransactionTopic() {
        return TopicBuilder
                .name(TransactionTopic.NEW_TRANSACTION_REQUEST)
                .build();
    }

    @Bean
    public NewTopic newTransactionCheckAccountTopic() {
        return TopicBuilder
                .name(TransactionTopic.NEW_TRANSACTION_CHECK_ACCOUNT)
                .build();
    }

    @Bean
    public NewTopic newTransactionCheckProductTopic() {
        return TopicBuilder
                .name(TransactionTopic.NEW_TRANSACTION_CHECK_PRODUCT)
                .build();
    }

}
