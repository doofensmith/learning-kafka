package com.softlaboratory.product.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import product.domain.dao.ProductDao;
import product.domain.dto.ProductDto;
import product.kafka.topics.ProductTopics;

import static product.kafka.topics.ProductTopics.*;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService service;

    @KafkaListener(topics = ADD_NEW, groupId = "product_consumer_addnew_group_1")
    void consumeAddProductRequest(String request) throws JsonProcessingException {
        try {
            log.info(request.substring(1, request.length()-1));
            ProductDto dto = new ProductDto();
            dto = objectMapper.readValue(request.substring(1, request.length()-1), ProductDto.class);
            log.info(dto);
        }catch (Exception e) {
            throw e;
        }
    }

    @KafkaListener(topics = UPDATE, groupId = "product_consumer_update_group_1")
    void consumeUpdateProductRequest(String request) {

    }

    @KafkaListener(topics = DELETE, groupId = "product_consumer_delete_group_1")
    void consumeDeleteProductRequest(String request) {

    }

}
