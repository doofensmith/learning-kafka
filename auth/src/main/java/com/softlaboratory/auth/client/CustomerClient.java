package com.softlaboratory.auth.client;

import customer.domain.request.NewProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "customer-service", path = "/api/customer")
public interface CustomerClient {

    @PostMapping(value = "/new-customer")
    ResponseEntity<Object> newCustomer(@RequestBody NewProfileRequest request);

}
