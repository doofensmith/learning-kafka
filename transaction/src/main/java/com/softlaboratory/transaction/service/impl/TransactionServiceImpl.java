package com.softlaboratory.transaction.service.impl;

import basecomponent.utility.ResponseUtil;
import com.softlaboratory.transaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import transaction.constant.TransactionStatus;
import transaction.domain.dao.TransactionDao;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;
import transaction.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Object> newTransaction(TransactionRequest request) {
        log.debug("Starting new transaction.");
        log.debug("Transaction request : {}", request);

        log.debug("Create new transaction data.");
        TransactionDao transactionDao = TransactionDao.builder()
                .issuedAt(LocalDateTime.now())
                .status(TransactionStatus.PROCESSING.name())
                .quantity(request.getQuantity())
                .total(request.getTotal())
                .idProduct(request.getIdProduct())
                .idAccount(request.getIdAccount())
                .build();

        log.debug("Save new transaction data with repository.");
        transactionDao = transactionRepository.save(transactionDao);

        log.debug("Add new transaction success.");
        return ResponseUtil.build(HttpStatus.OK, "Transaction issued.", null);
    }

    @Override
    public ResponseEntity<Object> updateTransactionStatus(Long idTransaction, UpdateTransactionRequest request) {
        log.debug("Starting update transaction status.");
        log.debug("Transaction id : {}, Request body : {}", idTransaction, request);

        log.debug("Find old transaction data with repository.");
        Optional<TransactionDao> oldTransactionData = transactionRepository.findById(idTransaction);

        if (oldTransactionData.isPresent()) {
            log.debug("Update transaction status with repository.");
            transactionRepository.updateStatusByIdEquals(request.getStatus().name(), idTransaction);

            log.debug("Update transaction status success.");
            return ResponseUtil.build(HttpStatus.OK, "Transaction status updated.", null);
        }else {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }

    }

}
