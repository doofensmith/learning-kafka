package com.softlaboratory.customer.service;

import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import customer.domain.request.UpdateCustomerRequest;
import customer.domain.request.UpdateProfileRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    ResponseEntity<Object> getCustomerByIdAccount(Long idAccount);
    ResponseEntity<Object> newCustomer(ProfileDto profileReq, CustomerDto customerReq);
    ResponseEntity<Object> updateProfile(Long idAccount, UpdateProfileRequest requestBody);
    ResponseEntity<Object> updateCustomerData(Long idAccount, UpdateCustomerRequest requestBody);

}
