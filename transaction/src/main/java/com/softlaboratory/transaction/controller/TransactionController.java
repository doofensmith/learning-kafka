package com.softlaboratory.transaction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softlaboratory.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/query")
    public ResponseEntity<Object> getPageTransaction(
            @RequestParam(value = "page", required = false) Integer reqPage,
            @RequestParam(value = "size", required = false) Integer reqSize) {
        try {
            return transactionService.getPageTransaction(reqPage, reqSize);
        }catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Long id) {
        try {
            return transactionService.getTransactionById(id);
        }catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/new-transaction")
    public ResponseEntity<Object> newTransaction(@RequestBody TransactionRequest request) throws JsonProcessingException {
        try {
            return transactionService.newTransaction(request);
        }catch (Exception e) {
            throw e;
        }
    }

    @PatchMapping(value = "/update-status/{idTransaction}")
    public ResponseEntity<Object> updateTransactionStatus(
            @PathVariable Long idTransaction,
            @RequestBody UpdateTransactionRequest request) {
        try {
            return transactionService.updateTransactionStatus(idTransaction, request);
        }catch (Exception e) {
            throw e;
        }
    }

}
