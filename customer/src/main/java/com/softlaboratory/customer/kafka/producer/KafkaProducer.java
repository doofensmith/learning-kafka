package com.softlaboratory.customer.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import notification.constant.NotificationConstant;
import notification.constant.NotificationTopics;
import notification.domain.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import transaction.constant.TransactionTopic;

@Service
public class KafkaProducer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessageToNotification(String content, String receiver) throws JsonProcessingException {
        NotificationDto notificationDto = NotificationDto.builder()
                .content(content)
                .publisher(NotificationConstant.defaultPublisher)
                .receiver(receiver)
                .build();

        String message = objectMapper.writeValueAsString(notificationDto);
        kafkaTemplate.send(NotificationTopics.NOTIF_REGISTER_ACCOUNT, message);

    }

    public void sendMessageToCheckProductService(String message) {
        kafkaTemplate.send(TransactionTopic.NEW_TRANSACTION_CHECK_PRODUCT, message);
    }

}
