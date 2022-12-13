package com.softlaboratory.customer.kafka.consumer;

import auth.constant.AuthTopics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.customer.kafka.producer.KafkaProducer;
import com.softlaboratory.customer.service.CustomerService;
import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = AuthTopics.AUTH_REGISTER_ACCOUNT)
    void consumeRegisterAccount(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert message to object.");
        Map<String, Object> data = objectMapper.readValue(message, Map.class);

        log.debug("Get profile request.");
        ProfileDto profileReq = objectMapper.convertValue(data.get("profile"), ProfileDto.class);

        log.debug("Get customer request.");
        CustomerDto customerReq = objectMapper.convertValue(data.get("customer"), CustomerDto.class);

        log.debug("Get user sender.");
        String username = objectMapper.convertValue(data.get("username"), String.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = customerService.newCustomer(profileReq, customerReq);
        log.debug("Response : {}", response.getBody());

        log.debug("Send notification.");
        if (response.getStatusCode() == HttpStatus.OK) {
            kafkaProducer.sendMessageToNotification("Successful register your new account.", username);
        }

    }

}
