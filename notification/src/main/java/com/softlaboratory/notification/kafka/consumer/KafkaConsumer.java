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

    @KafkaListener(topics = NotificationTopics.NOTIF_PUSH_NEW)
    void consumeNotifRegisterAccount(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert to request body.");
        NotificationDto dto = objectMapper.readValue(message, NotificationDto.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = service.pushNotification(dto);
        log.debug("Response : {}", response.getBody());

    }

}
