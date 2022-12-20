package com.softlaboratory.payment.service;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import payment.domain.request.PaymentRequest;

@Service
public interface PaymentService {

    ResponseEntity<Object> insertPayment(PaymentRequest request);
    ResponseEntity<Object> update();

}
