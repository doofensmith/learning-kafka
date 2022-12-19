package com.softlaboratory.transaction.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import notification.kafka.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import product.kafka.topic.ProductTopic;
import transaction.constant.TransactionTopic;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class KafkaProducer extends NotificationProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        super(objectMapper, kafkaTemplate);
    }

    public void sendTransactionRequest(Long idTransaction, TransactionRequest request) throws JsonProcessingException {
        String sender = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.debug("Sender request : {}", sender);

        Map<String, Object> data = new HashMap<>();
        data.put("id_transaction", idTransaction);
        data.put("transaction_request", request);
        data.put("sender", sender);

        String message = objectMapper.writeValueAsString(data);
        kafkaTemplate.send(TransactionTopic.NEW_TRANSACTION_CHECK_ACCOUNT, message);
    }

    public void sendUpdateTransactionStatus(Long idTransaction, UpdateTransactionRequest request) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("id_transaction", idTransaction);
        data.put("request", request);

        String message = objectMapper.writeValueAsString(data);
        kafkaTemplate.send(TransactionTopic.UPDATE_STATUS, message);
    }

    public void sendUpdateProductStock(Long idProduct, Integer newStock) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("id_product", idProduct);
        data.put("new_stock", newStock);

        String message = objectMapper.writeValueAsString(data);
        kafkaTemplate.send(ProductTopic.UPDATE_STOCK, message);
    }

}
