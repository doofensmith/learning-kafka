package com.softlaboratory.auth.kafka.producer;

import auth.domain.request.RegisterRequest;
import auth.kafka.topic.AuthTopic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishRegisterResponse(Long idAccount, RegisterRequest request) throws JsonProcessingException {
        ProfileDto profileDto = ProfileDto.builder()
                .fullname(request.getFullname())
                .profilePic("-")
                .idAccount(idAccount)
                .build();

        CustomerDto customerDto = CustomerDto.builder()
                .balance(0D)
                .point(0D)
                .idAccount(idAccount)
                .build();

        Map<String, Object> data = new HashMap<>();
        data.put("profile", profileDto);
        data.put("customer", customerDto);
        data.put("username", request.getUsername());

        String message = objectMapper.writeValueAsString(data);
        kafkaTemplate.send(AuthTopic.AUTH_REGISTER_ACCOUNT, message);
    }

}
