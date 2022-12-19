package com.softlaboratory.product.controller;

import com.softlaboratory.product.kafka.producer.KafkaProducer;
import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import product.domain.dto.ProductDto;

@Log4j2
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private KafkaProducer producer;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        try {
            return service.getPage(page, size);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            return service.getById(id);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<Object> create(@RequestBody ProductDto request) {
        try {
            return producer.sendCreateDataRequest(request);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateById(@PathVariable Long id, @RequestBody ProductDto request) {
        try {
            return producer.sendUpdateByIdRequest(id, request);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        try {
            return producer.sendDeleteByIdRequest(id);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

}
