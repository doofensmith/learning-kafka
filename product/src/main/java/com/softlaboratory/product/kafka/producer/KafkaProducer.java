package com.softlaboratory.product.kafka.producer;

import basecomponent.common.ApiResponse;
import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;
import product.kafka.topics.ProductTopics;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> template;

    @Autowired
    private ProductService service;

    @Autowired
    private ObjectMapper mapper;

//    public ResponseEntity<Object> sendResponseOfGetAll() {
//        try {
//            ResponseEntity<Object> responseEntity = service.getAll();
//            ApiResponse response = (ApiResponse) responseEntity.getBody();
//            String message = mapper.writeValueAsString(response);
//            template.send(ProductTopics.GET_ALL.topic, message);
//
//            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public ResponseEntity<Object> sendResponseOfGetById(Long id) {
//        try {
//            ResponseEntity<Object> responseEntity = service.getById(id);
//            ApiResponse response = (ApiResponse) responseEntity.getBody();
//            String message = mapper.writeValueAsString(response);
//            template.send(ProductTopics.GET_BY_ID.topic, message);
//
//            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Response published.");
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public ResponseEntity<Object> sendResponseOfCreate(ProductDto request) {
        try {
            String token = SecurityContextHolder.getContext().toString();
            log.info("Token : {}", token);

            Map<String, Object> map = new HashMap<>();
            String message = mapper.writeValueAsString(request);
            template.send(ProductTopics.ADD_NEW, message);

            return ResponseUtil.build(HttpStatus.OK, "Add product request sent.", null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendResponseOfUpdateById(Long id, ProductDto request) {
        try {
            Map<String, Object> req = new HashMap<>();
            req.put("id", id);
            req.put("request", request);
            String message = mapper.writeValueAsString(req);
            template.send(ProductTopics.UPDATE, message);

            return ResponseUtil.build(HttpStatus.OK, "Update product request sent.", null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendResponseOfDeleteById(Long id) {
        try {
            String message = mapper.writeValueAsString(id);
            template.send(ProductTopics.DELETE, message);

            return ResponseUtil.build(HttpStatus.OK, "Delete product request sent.", null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
