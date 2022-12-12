package com.softlaboratory.product.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import notification.constant.NotificationConstant;
import notification.constant.NotificationTopics;
import notification.domain.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;

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
    private KafkaTemplate<String, String> template;

    @KafkaListener(topics = ADD_NEW)
    void consumeAddProductRequest(String request) throws JsonProcessingException {
        log.info("Request received : {}", request);

        log.debug("Convert request to map.");
        Map<String, Object> data = objectMapper.readValue(request, Map.class);

        log.debug("Convert sender username.");
        String username = objectMapper.convertValue(data.get("username"), String.class);

        log.debug("Convert request body.");
        ProductDto dto = objectMapper.convertValue(data.get("request"), ProductDto.class);

        try {
            log.debug("Execute service.");
            ResponseEntity<Object> apiResponse = service.create(dto);
            log.debug("Response : {}", apiResponse.getBody());

            log.debug("Send request to notification.");
            if (apiResponse.getStatusCode() == HttpStatus.OK) {
                sendNotificationRequest("Add new product success.", username);
            }else {
                sendNotificationRequest("Add new product failed. Message "+apiResponse.getStatusCode(), username);
            }
        }catch (Exception e) {
            sendNotificationRequest("Add new product failed with error : "+e.getMessage(), username);
        }
    }

    @KafkaListener(topics = UPDATE)
    void consumeUpdateProductRequest(String request) throws JsonProcessingException {
        log.debug("Received request : {}", request);

        log.debug("Convert reqeust to map.");
        Map<String, Object> map = objectMapper.readValue(request, Map.class);

        log.debug("Convert request id.");
        Long id = objectMapper.convertValue(map.get("id"), Long.class);

        log.debug("Convert request body.");
        ProductDto dto = objectMapper.convertValue(map.get("request"), ProductDto.class);

        log.debug("Get sender user.");
        String sender = objectMapper.convertValue(map.get("sender"), String.class);

        try {
            log.debug("Execute service.");
            ResponseEntity<Object> response = service.updateById(id, dto);
            log.debug("Response : {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                sendNotificationRequest("Update product success.", sender);
            }else {
                sendNotificationRequest("Update product failed. Message "+response.getStatusCode(), sender);
            }

        }catch (Exception e) {
            sendNotificationRequest("Update product failed with error : "+e.getMessage(), sender);
        }
    }

    @KafkaListener(topics = DELETE)
    void consumeDeleteProductRequest(String request) throws JsonProcessingException {
        log.debug("Received request : {}", request);

        log.debug("Convert request to map.");
        Map<String, Object> data = objectMapper.readValue(request, Map.class);

        log.debug("Convert id request.");
        Long id = objectMapper.convertValue(data.get("id"), Long.class);

        log.debug("Convert sender user.");
        String sender = objectMapper.convertValue(data.get("sender"), String.class);

        try {
            log.debug("Execute service.");
            ResponseEntity<Object> response = service.deleteById(id);
            log.debug("Response : {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                sendNotificationRequest("Delete product success.", sender);
            }else {
                sendNotificationRequest("Delete product failed. Message "+response.getStatusCode(), sender);
            }
        }catch (Exception e) {
            sendNotificationRequest("Delete product failed with error : "+e.getMessage(), sender);
        }
    }

    private void sendNotificationRequest(String content, String receiver) throws JsonProcessingException {
        NotificationDto notifReq = NotificationDto.builder()
                .content(content)
                .publisher(NotificationConstant.defaultPublisher)
                .receiver(receiver)
                .build();

        String message = objectMapper.writeValueAsString(notifReq);
        template.send(NotificationTopics.NOTIF_ADD_PRODUCT, message);
    }

}