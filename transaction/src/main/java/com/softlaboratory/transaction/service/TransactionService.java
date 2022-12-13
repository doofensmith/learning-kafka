package com.softlaboratory.transaction.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;

@Service
public interface TransactionService {

    ResponseEntity<Object> newTransaction(TransactionRequest request);
    ResponseEntity<Object> updateTransactionStatus(Long idTransaction, UpdateTransactionRequest request);

}
