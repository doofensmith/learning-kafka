package com.softlaboratory.notification.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.notification.service.NotificationService;
import lombok.extern.log4j.Log4j2;
import notification.constant.NotificationTopics;
import notification.domain.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private NotificationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = NotificationTopics.NOTIF_ADD_PRODUCT)
    void consumeNotifAddProduct(String request) throws JsonProcessingException {
        log.debug("Received notification request : {}", request);

        log.debug("Convert request body.");
        NotificationDto dto = objectMapper.readValue(request, NotificationDto.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = service.pushNotification(dto);
        log.debug("Response : {}", response.getBody());
    }

    @KafkaListener(topics = NotificationTopics.NOTIF_UPDATE_PRODUCT)
    void consumeNotifUpdateProduct(String request) throws JsonProcessingException {
        log.debug("Received notification request : {}", request);

        log.debug("Convert request body.");
        NotificationDto dto = objectMapper.readValue(request, NotificationDto.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = service.pushNotification(dto);
        log.debug("Response : {}", response.getBody());
    }

    @KafkaListener(topics = NotificationTopics.NOTIF_DELETE_PRODUCT)
    void consumeNotifDeleteProduct(String request) throws JsonProcessingException {
        log.debug("Received notification request : {}", request);

        log.debug("Convert request body.");
        NotificationDto dto = objectMapper.readValue(request, NotificationDto.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = service.pushNotification(dto);
        log.debug("Response : {}", response.getBody());
    }

}
