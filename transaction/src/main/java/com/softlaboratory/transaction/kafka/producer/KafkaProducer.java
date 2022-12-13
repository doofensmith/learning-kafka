package com.softlaboratory.transaction.kafka.producer;

import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import transaction.constant.TransactionTopic;
import transaction.domain.request.TransactionRequest;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseEntity<Object> sendTransactionRequest(TransactionRequest request) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(request);
        kafkaTemplate.send(TransactionTopic.NEW_TRANSACTION_CHECK_ACCOUNT, message);

        return ResponseUtil.build(HttpStatus.OK, "New transaction request published.", null);
    }

}
