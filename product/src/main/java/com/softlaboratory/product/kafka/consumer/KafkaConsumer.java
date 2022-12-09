package com.softlaboratory.product.kafka.consumer;

import basecomponent.common.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import notification.domain.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import product.domain.dto.ProductDto;
import reactor.core.publisher.Mono;

import java.util.Map;

import static product.kafka.topics.ProductTopics.*;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebClient webClient;

    @KafkaListener(topics = ADD_NEW, groupId = "product_consumer_addnew_group_1")
    void consumeAddProductRequest(String request) throws JsonProcessingException {
        try {
            log.info("Request received : {}", request);
            ProductDto dto = objectMapper.readValue(request, ProductDto.class);

            ResponseEntity<Object> apiResponse = service.create(dto);
            log.info("Response : {}", apiResponse.getBody());

            if (apiResponse.getStatusCode() == HttpStatus.OK) {
                log.info("");
                NotificationDto notifReq = NotificationDto.builder()
                        .content("Add new product success.")
                        .publisher("SYSTEM")
                        .receiver("EVERYONE")
                        .build();


                String token = SecurityContextHolder.getContext().getAuthentication().getName();
                log.info(token);
//                ApiResponse notif = webClient.post()
//                        .uri("/api/notification/push")
//                        .headers(httpHeaders -> {
//                            httpHeaders.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbjEiLCJyb2xlcyI6IlVTRVIsQURNSU4iLCJpYXQiOjE2NzA1NzM1NjEsImV4cCI6MTY3MDU3NDE2MX0.haFqGeWKO3nnqcAizhnZWidosCJZbOtxXket_9KqKYI");
//                        })
//                        .body(Mono.just(notifReq), NotificationDto.class)
//                        .retrieve()
//                        .bodyToMono(ApiResponse.class)
//                        .block();

            }
        }catch (Exception e) {
            throw e;
        }
    }

    @KafkaListener(topics = UPDATE, groupId = "product_consumer_update_group_1")
    void consumeUpdateProductRequest(String request) throws JsonProcessingException {
        log.info("Received request : {}", request);
        Map<String, Object> map = objectMapper.readValue(request, Map.class);
        Long id = objectMapper.convertValue(map.get("id"), Long.class);
        ProductDto dto = objectMapper.convertValue(map.get("request"), ProductDto.class);

        ResponseEntity<Object> response = service.updateById(id, dto);
        log.info("Response : {}", response.getBody());
    }

    @KafkaListener(topics = DELETE, groupId = "product_consumer_delete_group_1")
    void consumeDeleteProductRequest(String request) {
        log.info("Received request : {}", request);
        Long id = objectMapper.convertValue(request, Long.class);

        ResponseEntity<Object> response = service.deleteById(id);
        log.info("Response : {}", response.getBody());
    }

}
