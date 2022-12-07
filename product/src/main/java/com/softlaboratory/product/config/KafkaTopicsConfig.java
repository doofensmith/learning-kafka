//package com.softlaboratory.product.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.TopicBuilder;
//import product.kafka.topics.ProductTopics;
//
//@Configuration
//public class KafkaTopicsConfig {
//
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
//
//    @Bean
//    public NewTopic addNewProductTopic() {
//        return TopicBuilder
//                .name(ProductTopics.ADD_NEW.topic)
//                .replicas(3)
//                .partitions(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic updateProductTopic() {
//        return TopicBuilder
//                .name(ProductTopics.UPDATE.topic)
//                .replicas(3)
//                .partitions(3)
//                .build();
//    }
//
//    @Bean
//    public NewTopic deleteProductTopic() {
//        return TopicBuilder
//                .name(ProductTopics.DELETE.topic)
//                .replicas(3)
//                .partitions(3)
//                .build();
//    }
//
//}
