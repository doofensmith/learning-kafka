package com.softlaboratory.product.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import product.kafka.topic.ProductTopic;

@Configuration
public class KafkaTopicConfig {

//    @Bean
//    public NewTopic getAllProductTopic() {
//        return TopicBuilder
//                .name(ProductTopics.GET_ALL.topic)
//                .replicas(3)
//                .partitions(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic getProductByIdTopic() {
//        return TopicBuilder
//                .name(ProductTopics.GET_BY_ID.topic)
//                .replicas(3)
//                .partitions(3)
//                .build();
//    }

    @Bean
    public NewTopic addNewProductTopic() {
        return TopicBuilder
                .name(ProductTopic.ADD_NEW)
//                .replicas(3)
//                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic updateProductTopic() {
        return TopicBuilder
                .name(ProductTopic.UPDATE)
//                .replicas(3)
//                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic deleteProductTopic() {
        return TopicBuilder
                .name(ProductTopic.DELETE)
//                .replicas(3)
//                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic updateProductStockTopic() {
        return TopicBuilder
                .name(ProductTopic.UPDATE_STOCK)
                .build();
    }

}
