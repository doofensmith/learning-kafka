package com.softlaboratory.customer.controller;

import com.softlaboratory.customer.service.CustomerService;
import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import customer.domain.request.UpdateCustomerRequest;
import customer.domain.request.UpdateProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "/new-customer")
    public ResponseEntity<Object> newCustomer(
            @RequestParam(value = "profile_req") ProfileDto profileReq,
            @RequestParam(value = "customer_req") CustomerDto customerReq) {
        try {
            return customerService.newCustomer(profileReq, customerReq);
        }catch (Exception e) {
            throw e;
        }
    }

    @PatchMapping(value = "update-profile/{idAccount}")
    public ResponseEntity<Object> updateProfile(
            @PathVariable Long idAccount,
            @RequestBody UpdateProfileRequest requestBody) {
        try {
            return customerService.updateProfile(idAccount, requestBody);
        }catch (Exception e) {
            throw e;
        }
    }

    @PatchMapping(value = "update-customer/{idAccount}")
    public ResponseEntity<Object> updateCustomerData(
            @PathVariable Long idAccount,
            @RequestBody UpdateCustomerRequest requestBody) {
        try {
            return customerService.updateCustomerData(idAccount, requestBody);
        }catch (Exception e) {
            throw e;
        }
    }

}
