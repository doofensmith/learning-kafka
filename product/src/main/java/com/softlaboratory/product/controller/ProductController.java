package com.softlaboratory.product.controller;

import com.softlaboratory.product.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.dto.ProductDto;

@Log4j2
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "")
    public ResponseEntity<Object> getAll() {
        try {
            return service.getAll();
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

    @PostMapping(value = "")
    public ResponseEntity<Object> create(@RequestBody ProductDto request) {
        try {
            return service.create(request);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateById(@PathVariable Long id, @RequestBody ProductDto request) {
        try {
            return service.updateById(id, request);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteById(@PathVariable Long id) {
        try {
            return service.deleteById(id);
        }catch (Exception e) {
            log.error("Error {}", e.getMessage());
            throw e;
        }
    }

}
