package com.softlaboratory.customer.service.impl;

import basecomponent.utility.ResponseUtil;
import com.softlaboratory.customer.service.CustomerService;
import customer.domain.dao.CustomerDao;
import customer.domain.dao.ProfileDao;
import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import customer.repository.CustomerRepository;
import customer.repository.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<Object> newCustomer(ProfileDto profileReq, CustomerDto customerReq) {
        log.info("Starting new customer service.");
        log.debug("New profile request : {}", profileReq);
        log.debug("New customer request : {}", customerReq);

        log.debug("Prepare new profile data.");
        ProfileDao profileDao = ProfileDao.builder()
                .fullname(profileReq.getFullname())
                .profilePic(profileReq.getProfilePic())
                .email(profileReq.getEmail())
                .phoneNumber(profileReq.getPhoneNumber())
                .idAccount(profileReq.getIdAccount())
                .build();

        log.debug("Save new profile with repository.");
        profileDao = profileRepository.save(profileDao);

        log.debug("Prepare new customer data.");
        CustomerDao customerDao = CustomerDao.builder()
                .balance(customerReq.getBalance())
                .point(customerReq.getPoint())
                .idAccount(customerReq.getIdAccount())
                .build();

        log.debug("Save new customer with repository.");
        customerDao = customerRepository.save(customerDao);

        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), null);
    }

}
