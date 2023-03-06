//package com.softlaboratory.customer.kafka.consumer;
//
//import auth.constant.AuthTopics;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.softlaboratory.customer.kafka.producer.KafkaProducer;
//import com.softlaboratory.customer.service.CustomerService;
//import customer.domain.dto.CustomerDto;
//import customer.domain.dto.ProfileDto;
//import lombok.extern.log4j.Log4j2;
//import notification.constant.NotificationConstant;
//import notification.constant.NotificationTopics;
//import notification.domain.request.NotificationRequest;
//import notification.kafka.producer.NotificationProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import transaction.constant.TransactionStatus;
//import transaction.constant.TransactionTopic;
//import transaction.domain.request.TransactionRequest;
//import transaction.domain.request.UpdateTransactionRequest;
//
//import java.util.Map;
//
//@Log4j2
//@Service
//public class KafkaConsumer {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private KafkaProducer kafkaProducer;
//
//    @KafkaListener(topics = AuthTopics.AUTH_REGISTER_ACCOUNT)
//    void consumeRegisterAccount(String message) throws JsonProcessingException {
//        log.debug("Received message : {}", message);
//
//        log.debug("Convert message to object.");
//        Map<String, Object> data = objectMapper.readValue(message, Map.class);
//
//        log.debug("Get profile request.");
//        ProfileDto profileReq = objectMapper.convertValue(data.get("profile"), ProfileDto.class);
//
//        log.debug("Get customer request.");
//        CustomerDto customerReq = objectMapper.convertValue(data.get("customer"), CustomerDto.class);
//
//        log.debug("Get user sender.");
//        String username = objectMapper.convertValue(data.get("username"), String.class);
//
//        log.debug("Execute service.");
//        ResponseEntity<Object> response = customerService.newCustomer(profileReq, customerReq);
//        log.debug("Response : {}", response.getBody());
//
//        log.debug("Send notification.");
//        if (response.getStatusCode() == HttpStatus.OK) {
//            kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Successful register your new account.", username);
//        }
//
//    }
//
//    @KafkaListener(topics = TransactionTopic.NEW_TRANSACTION_CHECK_ACCOUNT)
//    void consumeTransactionCheckAccount(String message) throws JsonProcessingException {
//        log.debug("Received message : {}", message);
//
//        log.debug("Convert message to object.");
//        Map<String, Object> messageObject = objectMapper.readValue(message, Map.class);
//
//        log.debug("Get transaction request.");
//        TransactionRequest request = objectMapper.convertValue(messageObject.get("transaction_request"), TransactionRequest.class);
//
//        log.debug("Get id account.");
//        Long idAccount = objectMapper.convertValue(request.getIdAccount(), Long.class);
//
//        log.debug("Execute service.");
//        ResponseEntity<Object> response = customerService.getCustomerByIdAccount(idAccount);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            log.debug("Send message to product service.");
//            kafkaProducer.sendMessageToCheckProductService(message);
//        }else {
//            log.debug("Customer account id {} not found.", idAccount);
//
//            log.debug("Get transaction id.");
//            Long idTransaction = objectMapper.convertValue(messageObject.get("id_transaction"), Long.class);
//
//            log.debug("Transaction status : FAILED");
//            UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
//                    .status(TransactionStatus.FAILED)
//                    .build();
//
//            log.debug("Send message to transaction service to update status.");
//            kafkaProducer.sendMessageToUpdateTransactionStatus(idTransaction, updateTransactionRequest);
//
//            log.debug("Send message to notification service.");
//            kafkaProducer.sendNotification(
//                    NotificationTopics.NOTIF_PUSH_NEW,
//                    NotificationConstant.TRANSACTION_FAILED_ACCOUNT_NOT_FOUND+" Id account : "+idAccount,
//                    NotificationConstant.ADMIN_RECEIVER);
//
//        }
//
//    }
//
//}
