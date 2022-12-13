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

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendTransactionRequest(Long idTransaction, TransactionRequest request) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("id_transaction", idTransaction);
        data.put("id_product", request.getIdProduct());
        data.put("id_account", request.getIdAccount());

        String message = objectMapper.writeValueAsString(data);
        kafkaTemplate.send(TransactionTopic.NEW_TRANSACTION_CHECK_ACCOUNT, message);
    }

}
