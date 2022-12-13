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
