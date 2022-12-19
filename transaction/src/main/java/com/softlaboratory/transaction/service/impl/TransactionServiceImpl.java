package com.softlaboratory.transaction.service.impl;

import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.softlaboratory.transaction.kafka.producer.KafkaProducer;
import com.softlaboratory.transaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import product.domain.dto.ProductDto;
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

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public ResponseEntity<Object> newTransaction(TransactionRequest request) throws JsonProcessingException {
        log.debug("Starting new transaction.");
        log.debug("Transaction request : {}", request);

        try {
            log.debug("Create new transaction data.");
            TransactionDao transactionDao = TransactionDao.builder()
                    .issuedAt(LocalDateTime.now())
                    .status(TransactionStatus.PROCESSING.name())
                    .quantity(request.getQuantity())
                    .total(0D)
                    .idProduct(request.getIdProduct())
                    .idAccount(request.getIdAccount())
                    .build();

            log.debug("Save new transaction data with repository.");
            transactionDao = transactionRepository.save(transactionDao);

            log.debug("Add new transaction success.");

            log.debug("Send message to broker check account.");
            kafkaProducer.sendTransactionRequest(transactionDao.getId(), request);

        }catch (Exception e) {
            throw e;
        }
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
            log.debug("Update transaction status failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }

    }

    @Override
    public ResponseEntity<Object> updateTransactionTotal(Long idTransaction, Double price) {
        log.debug("Starting update total price.");

        log.debug("Get transaction with repository.");
        Optional<TransactionDao> transactionDao = transactionRepository.findById(idTransaction);
        if (transactionDao.isPresent()) {
            log.debug("Count total price.");
            Double totalPrice = transactionDao.get().getQuantity() * price;

            log.debug("Update transaction total price with repository.");
            transactionRepository.updateTotalByIdEquals(totalPrice, idTransaction);

            log.debug("Update total price success.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null);
        }else {
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }

    }

}
