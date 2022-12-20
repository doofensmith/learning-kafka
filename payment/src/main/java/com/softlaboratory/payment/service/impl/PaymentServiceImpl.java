package com.softlaboratory.payment.service.impl;

import basecomponent.utility.ResponseUtil;
import com.softlaboratory.payment.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import payment.constant.PaymentStatus;
import payment.domain.dao.PaymentDao;
import payment.domain.request.PaymentRequest;
import payment.repository.PaymentRepository;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<Object> insertPayment(PaymentRequest request) {
        log.debug("Starting new payment service.");
        log.debug("New payment request : {}", request);

        log.debug("Get user from token.");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        log.debug("Prepare payment data.");
        PaymentDao paymentDao = PaymentDao.builder()
                .reqAmount(request.getAmount())
                .settleAmount(0D)
                .status(PaymentStatus.PENDING.name())
                .paidBy(username)
                .idTransaction(request.getIdTransaction())
                .build();

        log.debug("Save data with repository.");
        paymentDao = paymentRepository.save(paymentDao);

        log.debug("New payment placed successfully.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null);
    }
}
