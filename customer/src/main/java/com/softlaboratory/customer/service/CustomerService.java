package com.softlaboratory.customer.service;

import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    ResponseEntity<Object> newCustomer(ProfileDto profileReq, CustomerDto customerReq);

}
