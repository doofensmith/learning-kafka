package com.softlaboratory.transaction.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.transaction.kafka.producer.KafkaProducer;
import com.softlaboratory.transaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import notification.constant.NotificationConstant;
import notification.constant.NotificationTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;
import transaction.constant.TransactionStatus;
import transaction.constant.TransactionTopic;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;

import java.util.Map;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = TransactionTopic.NEW_TRANSACTION_REQUEST)
    void consumeNewTransactionRequest(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert message to object data.");
        Map<String, Object> dataObject = objectMapper.readValue(message, Map.class);

        log.debug("Get transaction id.");
        Long idTransaction = objectMapper.convertValue(dataObject.get("id_transaction"), Long.class);

        log.debug("Get transaction request.");
        TransactionRequest transactionRequest = objectMapper.convertValue(dataObject.get("transaction_request"), TransactionRequest.class);

        log.debug("Get product.");
        ProductDto product = objectMapper.convertValue(dataObject.get("product"), ProductDto.class);

        log.debug("Get request sender.");
        String sender = objectMapper.convertValue(dataObject.get("sender"), String.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = transactionService.updateTransactionTotal(idTransaction, product.getPrice());

        if (response.getStatusCode() == HttpStatus.OK) {
            log.debug("Update transaction status to PLACED.");
            UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
                    .status(TransactionStatus.PLACED)
                    .build();

            log.debug("Send message to update transaction status.");
            kafkaProducer.sendUpdateTransactionStatus(idTransaction, updateTransactionRequest);

            log.debug("Send message to notification.");
            kafkaProducer.sendNotification(
                    NotificationTopics.NOTIF_PUSH_NEW,
                    "New transaction succesfully placed.",
                    sender);

            log.debug("Send message to product service to update stock.");
            kafkaProducer.sendUpdateProductStock(product.getId(), (product.getStock()-transactionRequest.getQuantity()));

        }else {
            log.debug("Update transaction status to failed.");
            UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
                    .status(TransactionStatus.FAILED)
                    .build();

            log.debug("Send message to update transaction status to FAILED.");
            kafkaProducer.sendUpdateTransactionStatus(idTransaction, updateTransactionRequest);

            log.debug("Send message to notification.");
            kafkaProducer.sendNotification(
                    NotificationTopics.NOTIF_PUSH_NEW,
                    "New transaction failed to process.",
                    sender
            );

        }

    }

    @KafkaListener(topics = TransactionTopic.UPDATE_STATUS)
    void consumeUpdateTransactionStatus(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert message to object.");
        Map<String, Object> dataObject = objectMapper.readValue(message, Map.class);

        log.debug("Get id transaction.");
        Long idTransaction = objectMapper.convertValue(dataObject.get("id_transaction"), Long.class);

        log.debug("Get update status request.");
        UpdateTransactionRequest updateRequest = objectMapper.convertValue(dataObject.get("request"), UpdateTransactionRequest.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = transactionService.updateTransactionStatus(idTransaction, updateRequest);
    }

}
