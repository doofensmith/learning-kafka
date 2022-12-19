package com.softlaboratory.product.kafka.consumer;

import basecomponent.common.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softlaboratory.product.kafka.producer.KafkaProducer;
import com.softlaboratory.product.service.ProductService;
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

import static product.kafka.topic.ProductTopic.*;

@Log4j2
@Service
public class KafkaConsumer {

    @Autowired
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaProducer kafkaProducer;

    @KafkaListener(topics = ADD_NEW)
    void consumeAddProductRequest(String request) throws JsonProcessingException {
        log.info("Request received : {}", request);

        log.debug("Convert request to map.");
        Map<String, Object> data = objectMapper.readValue(request, Map.class);

        log.debug("Convert sender username.");
        String username = objectMapper.convertValue(data.get("username"), String.class);

        log.debug("Convert request body.");
        ProductDto dto = objectMapper.convertValue(data.get("request"), ProductDto.class);

        try {
            log.debug("Execute service.");
            ResponseEntity<Object> apiResponse = service.create(dto);
            log.debug("Response : {}", apiResponse.getBody());

            log.debug("Send request to notification.");
            if (apiResponse.getStatusCode() == HttpStatus.OK) {
                kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW, "Add new product success.", username);
            }else {
                kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Add new product failed. Message "+apiResponse.getStatusCode(), username);
            }
        }catch (Exception e) {
            kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Add new product failed with error : "+e.getMessage(), username);
        }
    }

    @KafkaListener(topics = UPDATE)
    void consumeUpdateProductRequest(String request) throws JsonProcessingException {
        log.debug("Received request : {}", request);

        log.debug("Convert request to map.");
        Map<String, Object> map = objectMapper.readValue(request, Map.class);

        log.debug("Convert request id.");
        Long id = objectMapper.convertValue(map.get("id"), Long.class);

        log.debug("Convert request body.");
        ProductDto dto = objectMapper.convertValue(map.get("request"), ProductDto.class);

        log.debug("Get sender user.");
        String sender = objectMapper.convertValue(map.get("sender"), String.class);

        try {
            log.debug("Execute service.");
            ResponseEntity<Object> response = service.updateById(id, dto);
            log.debug("Response : {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Update product success.", sender);
            }else {
                kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Update product failed. Message "+response.getStatusCode(), sender);
            }

        }catch (Exception e) {
            kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Update product failed with error : "+e.getMessage(), sender);
        }
    }

    @KafkaListener(topics = DELETE)
    void consumeDeleteProductRequest(String request) throws JsonProcessingException {
        log.debug("Received request : {}", request);

        log.debug("Convert request to map.");
        Map<String, Object> data = objectMapper.readValue(request, Map.class);

        log.debug("Convert id request.");
        Long id = objectMapper.convertValue(data.get("id"), Long.class);

        log.debug("Convert sender user.");
        String sender = objectMapper.convertValue(data.get("sender"), String.class);

        try {
            log.debug("Execute service.");
            ResponseEntity<Object> response = service.deleteById(id);
            log.debug("Response : {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Delete product success.", sender);
            }else {
                kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Delete product failed. Message "+response.getStatusCode(), sender);
            }
        }catch (Exception e) {
            kafkaProducer.sendNotification(NotificationTopics.NOTIF_PUSH_NEW,"Delete product failed with error : "+e.getMessage(), sender);
        }
    }

    @KafkaListener(topics = TransactionTopic.NEW_TRANSACTION_CHECK_PRODUCT)
    void consumeCustomerCheckProduct(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert message to object data.");
        Map<String, Object> dataObject = objectMapper.readValue(message, Map.class);

        log.debug("Get transaction id.");
        Long idTransaction = objectMapper.convertValue(dataObject.get("id_transaction"), Long.class);

        log.debug("Get transaction request.");
        TransactionRequest transactionRequest = objectMapper.convertValue(dataObject.get("transaction_request"), TransactionRequest.class);

        log.debug("Get product id.");
        Long idProduct = objectMapper.convertValue(transactionRequest.getIdProduct(), Long.class);

        log.debug("Execute service get product by id.");
        ResponseEntity<Object> response = service.getById(idProduct);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.debug("Get data from service.");
            ApiResponse apiResponse = objectMapper.convertValue(response.getBody(), ApiResponse.class);
            ProductDto productDto = objectMapper.convertValue(apiResponse.getData(), ProductDto.class);

            if (productDto.getStock() >= transactionRequest.getQuantity()) {
                log.debug("Send message to transaction.");
                dataObject.put("product", productDto);
                kafkaProducer.sendProductCheckResultToTransaction(dataObject);
            }else {
                log.debug("Insufficient stock.");

                log.debug("Update transaction status to failed.");
                UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
                        .status(TransactionStatus.FAILED)
                        .build();

                log.debug("Send message to transaction service to update transaction status.");
                kafkaProducer.sendTransactionStatusToTransactionService(idTransaction, updateTransactionRequest);

                log.debug("Send message to notification service.");
                kafkaProducer.sendNotification(
                        NotificationTopics.NOTIF_PUSH_NEW,
                        "Transaction failed. Insufficient stock.",
                        NotificationConstant.ADMIN_RECEIVER);

            }

        }else {
            log.debug("Product not found.");

            log.debug("Update transaction status to failed.");
            UpdateTransactionRequest updateTransactionRequest = UpdateTransactionRequest.builder()
                    .status(TransactionStatus.FAILED)
                    .build();

            log.debug("Send message to transaction service to update transaction status.");
            kafkaProducer.sendTransactionStatusToTransactionService(idTransaction, updateTransactionRequest);

            log.debug("Send message to notification service.");
            kafkaProducer.sendNotification(
                    NotificationTopics.NOTIF_PUSH_NEW,
                    "Transaction failed. Product not found.",
                    NotificationConstant.ADMIN_RECEIVER);
        }

    }

    @KafkaListener(topics = UPDATE_STOCK)
    void consumeUpdateProductStock(String message) throws JsonProcessingException {
        log.debug("Received message : {}", message);

        log.debug("Convert message to data object.");
        Map<String, Object> dataObject = objectMapper.readValue(message, Map.class);

        log.debug("Get id product.");
        Long idProduct = objectMapper.convertValue(dataObject.get("id_product"), Long.class);

        log.debug("Get new product stock.");
        Integer newStock = objectMapper.convertValue(dataObject.get("new_stock"), Integer.class);

        log.debug("Execute service.");
        ResponseEntity<Object> response = service.updateStock(idProduct, newStock);
    }

}
