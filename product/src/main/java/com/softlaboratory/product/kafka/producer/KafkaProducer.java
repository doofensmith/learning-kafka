package com.softlaboratory.product.kafka.producer;

import basedomain.common.ApiResponse;
import basedomain.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import product.dao.ProductDao;
import product.dto.ProductDto;
import product.kafka.topics.ProductTopics;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private ProductService service;

    @Autowired
    private ObjectMapper mapper;

    public ResponseEntity<Object> sendResponseOfGetAll() {
        try {
            ResponseEntity<Object> responseEntity = service.getAll();
            ApiResponse response = (ApiResponse) responseEntity.getBody();
            String message = mapper.writeValueAsString(response);
            template.send(ProductTopics.GET_ALL.topic, message);

            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendResponseOfGetById(Long id) {
        try {
            ResponseEntity<Object> responseEntity = service.getById(id);
            ApiResponse response = (ApiResponse) responseEntity.getBody();
            String message = mapper.writeValueAsString(response);
            template.send(ProductTopics.GET_BY_ID.topic, message);

            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendResponseOfCreate(ProductDto request) {
        try {
            ResponseEntity<Object> responseEntity = service.create(request);
            ApiResponse response = (ApiResponse) responseEntity.getBody();
            String message = mapper.writeValueAsString(response);
            template.send(ProductTopics.ADD_NEW.topic, message);

            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendResponseOfUpdateById(Long id, ProductDto request) {
        try {
            ResponseEntity<Object> responseEntity = service.updateById(id, request);
            ApiResponse response = (ApiResponse) responseEntity.getBody();
            String message = mapper.writeValueAsString(response);
            template.send(ProductTopics.UPDATE.topic, message);

            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendResponseOfDeleteById(Long id) {
        try {
            ResponseEntity<Object> responseEntity = service.deleteById(id);
            ApiResponse response = (ApiResponse) responseEntity.getBody();
            String message = mapper.writeValueAsString(response);
            template.send(ProductTopics.DELETE.topic, message);

            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
