//package com.softlaboratory.customer.kafka.producer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import notification.kafka.producer.NotificationProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//import transaction.constant.TransactionTopic;
//import transaction.domain.request.UpdateTransactionRequest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class KafkaProducer extends NotificationProducer{
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    public KafkaProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
//        super(objectMapper, kafkaTemplate);
//    }
//
//    public void sendMessageToCheckProductService(String message) {
//        kafkaTemplate.send(TransactionTopic.NEW_TRANSACTION_CHECK_PRODUCT, message);
//    }
//
//    public void sendMessageToUpdateTransactionStatus(Long idTransaction, UpdateTransactionRequest request) throws JsonProcessingException {
//        Map<String, Object> data = new HashMap<>();
//        data.put("id_transaction", idTransaction);
//        data.put("request", request);
//
//        String message = objectMapper.writeValueAsString(data);
//        kafkaTemplate.send(TransactionTopic.UPDATE_STATUS, message);
//
//    }
//
//}
