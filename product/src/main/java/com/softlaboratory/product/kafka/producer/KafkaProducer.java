package com.softlaboratory.product.kafka.producer;

import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import notification.constant.NotificationConstant;
import notification.constant.NotificationTopics;
import notification.domain.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;
import product.kafka.topics.ProductTopics;
import transaction.constant.TransactionTopic;

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

    public ResponseEntity<Object> sendCreateDataRequest(ProductDto request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            log.info("Request sender username : {}", username);

            Map<String, Object> data = new HashMap<>();
            data.put("username", username);
            data.put("request", request);

            String message = mapper.writeValueAsString(data);
            template.send(ProductTopics.ADD_NEW, message);

            return ResponseUtil.build(HttpStatus.OK, "Add product request sent.", null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendUpdateByIdRequest(Long id, ProductDto request) {
        try {
            String sender = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            log.info("Request sender username : {}", sender);

            Map<String, Object> req = new HashMap<>();
            req.put("id", id);
            req.put("sender", sender);
            req.put("request", request);

            String message = mapper.writeValueAsString(req);
            template.send(ProductTopics.UPDATE, message);

            return ResponseUtil.build(HttpStatus.OK, "Update product request sent.", null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> sendDeleteByIdRequest(Long id) {
        try {
            String sender = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            log.info("Request sender username : {}", sender);

            Map<String, Object> data = new HashMap<>();
            data.put("sender", sender);
            data.put("id", id);

            String message = mapper.writeValueAsString(data);
            template.send(ProductTopics.DELETE, message);

            return ResponseUtil.build(HttpStatus.OK, "Delete product request sent.", null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendNotificationRequest(String content, String receiver) throws JsonProcessingException {
        NotificationDto notifReq = NotificationDto.builder()
                .content(content)
                .publisher(NotificationConstant.defaultPublisher)
                .receiver(receiver)
                .build();

        String message = mapper.writeValueAsString(notifReq);
        template.send(NotificationTopics.NOTIF_ADD_PRODUCT, message);
    }

    public void sendProductByIdToTransaction(Long idTransaction, ProductDto productDto) throws JsonProcessingException {
        Map<String, Object> data = new HashMap<>();
        data.put("id_transaction", idTransaction);
        data.put("product", productDto);

        String message = mapper.writeValueAsString(data);
        template.send(TransactionTopic.NEW_TRANSACTION_REQUEST, message);

    }

}
