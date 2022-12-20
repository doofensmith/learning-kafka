package com.softlaboratory.transaction.service.impl;

import basecomponent.utility.ResponseUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.softlaboratory.transaction.kafka.producer.KafkaProducer;
import com.softlaboratory.transaction.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import transaction.constant.TransactionStatus;
import transaction.domain.dao.TransactionDao;
import transaction.domain.dto.TransactionDto;
import transaction.domain.request.TransactionRequest;
import transaction.domain.request.UpdateTransactionRequest;
import transaction.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public ResponseEntity<Object> getPageTransaction(Integer reqPage, Integer reqSize) {
        log.debug("Starting get transaction pagination.");

        log.debug("Request page : {}, size : {}.", reqPage, reqSize);

        log.debug("Default page and size.");
        Pageable pageable = PageRequest.of(
                Objects.requireNonNullElse(reqPage, 0),
                Objects.requireNonNullElse(reqSize, 25),
                Sort.by("id").descending()
        );

        log.debug("Fetch data with repository.");
        Page<TransactionDao> transactionPage = transactionRepository.findAll(pageable);

        log.debug("Convert result to data presentation.");
        List<TransactionDto> transactionDto = new ArrayList<>();
        transactionPage.getContent()
                .forEach(transactionDao -> transactionDto.add(
                        TransactionDto.builder()
                                .id(transactionDao.getId())
                                .issuedAt(transactionDao.getIssuedAt())
                                .status(transactionDao.getStatus())
                                .quantity(transactionDao.getQuantity())
                                .total(transactionDao.getTotal())
                                .settleAt(transactionDao.getSettleAt())
                                .build()
                ));

        log.debug("Get all transaction data success.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), transactionDto);
    }

    @Override
    public ResponseEntity<Object> getTransactionById(Long id) {
        log.debug("Starting get transaction by id.");

        log.debug("Request id : {}", id);

        log.debug("Fetching data with repository.");
        Optional<TransactionDao> transactionDao = transactionRepository.findById(id);
        if (transactionDao.isPresent()) {
            log.debug("Transaction id : {} found. Convert to data presentation.", id);
            TransactionDto transactionDto = TransactionDto.builder()
                    .id(transactionDao.get().getId())
                    .status(transactionDao.get().getStatus())
                    .quantity(transactionDao.get().getQuantity())
                    .issuedAt(transactionDao.get().getIssuedAt())
                    .settleAt(transactionDao.get().getSettleAt())
                    .total(transactionDao.get().getTotal())
                    .build();

            log.debug("Get transaction by id success.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), transactionDto);
        } else {
            log.debug("Transaction with id : {} not found.", id);

            log.debug("Get transaction by id failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

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
