package com.softlaboratory.transaction.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.transaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;
import transaction.constant.TransactionTopic;

import java.util.Map;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionService transactionService;

    @KafkaListener(topics = TransactionTopic.NEW_TRANSACTION_REQUEST)
    void consumeNewTransactionRequest(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert message to object data.");
        Map<String, Object> dataObject = objectMapper.readValue(message, Map.class);

        log.debug("Get transaction id.");
        Long idTransaction = objectMapper.convertValue(dataObject.get("id_transaction"), Long.class);

        log.debug("Get product data.");
        ProductDto productDto = objectMapper.convertValue(dataObject.get("product"), ProductDto.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = transactionService.updateTransactionTotal(idTransaction, productDto);

        if (response.getStatusCode() == HttpStatus.OK) {
            //TODO: send message to notification service
            //TODO: send message to product service (total quantity)
        }else {
            //TODO: update transaction status to failed and send message to notification service
        }

    }

}
