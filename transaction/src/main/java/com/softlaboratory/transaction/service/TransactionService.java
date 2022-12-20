package com.softlaboratory.transaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;

@Service
public interface TransactionService {

    ResponseEntity<Object> getPageTransaction(Integer reqPage, Integer reqSize);
    ResponseEntity<Object> getTransactionById(Long id);
    ResponseEntity<Object> newTransaction(TransactionRequest request) throws JsonProcessingException;
    ResponseEntity<Object> updateTransactionStatus(Long idTransaction, UpdateTransactionRequest request);
    ResponseEntity<Object> updateTransactionTotal(Long idTransaction, Double price);
    //TODO: create service cancel transaction

}
