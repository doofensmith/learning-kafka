package com.softlaboratory.product.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;

@Service
public interface ProductService {

    ResponseEntity<Object> getAll();
    ResponseEntity<Object> getById(Long id);
    ResponseEntity<Object> create(ProductDto request);
    ResponseEntity<Object> updateById(Long id, ProductDto request);
    ResponseEntity<Object> deleteById(Long id);

}
