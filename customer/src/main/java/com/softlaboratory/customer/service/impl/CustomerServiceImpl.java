package com.softlaboratory.customer.service.impl;

import basecomponent.utility.ResponseUtil;
import com.softlaboratory.customer.service.CustomerService;
import customer.domain.dao.CustomerDao;
import customer.domain.dao.ProfileDao;
import customer.domain.dto.CustomerDto;
import customer.domain.dto.ProfileDto;
import customer.domain.request.UpdateCustomerRequest;
import customer.domain.request.UpdateProfileRequest;
import customer.repository.CustomerRepository;
import customer.repository.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public ResponseEntity<Object> getCustomerByIdAccount(Long idAccount) {
        log.debug("Starting get customer by id account.");
        log.debug("Id account request : {}", idAccount);

        log.debug("Find customer by id account with repository.");
        Optional<CustomerDao> customerDao = customerRepository.findByIdAccountEquals(idAccount);

        if (customerDao.isPresent()) {
            log.debug("Convert to DTO.");
            CustomerDto customerDto = CustomerDto.builder()
                    .id(customerDao.get().getIdAccount())
                    .balance(customerDao.get().getBalance())
                    .point(customerDao.get().getPoint())
                    .idAccount(customerDao.get().getIdAccount())
                    .build();

            log.debug("Get customer by id account success.");
            return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), customerDto);
        }else {
            log.debug("Get customer by id account failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

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

    @Override
    public ResponseEntity<Object> updateProfile(Long idAccount, UpdateProfileRequest requestBody) {
        log.debug("Starting update profile.");
        log.debug("Account id : {}, Request body : {}", idAccount, requestBody);

        log.debug("Find old profile with repository.");
        Optional<ProfileDao> oldProfile = profileRepository.findByIdAccountEquals(idAccount);

        if (oldProfile.isPresent()) {
            log.debug("Prepare new profile.");
            ProfileDao newProfile = oldProfile.get();
            newProfile.setFullname(requestBody.getFullname());
            newProfile.setProfilePic(requestBody.getProfilePic());
            newProfile.setEmail(requestBody.getEmail());
            newProfile.setPhoneNumber(requestBody.getPhoneNumber());

            log.debug("Save new profile with repository.");
            newProfile = profileRepository.save(newProfile);

            log.debug("Update profile success.");
            return ResponseUtil.build(HttpStatus.OK, "Profile updated.", null);
        }else {
            log.debug("Update profile failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

    @Override
    public ResponseEntity<Object> updateCustomerData(Long idAccount, UpdateCustomerRequest requestBody) {
        log.debug("Starting update customer data.");
        log.debug("Account id : {}, Request body : {}", idAccount, requestBody);

        log.debug("Find old customer data with repository.");
        Optional<CustomerDao> oldCustomerData = customerRepository.findByIdAccountEquals(idAccount);

        if (oldCustomerData.isPresent()) {
            log.debug("Prepare new customer data.");
            CustomerDao newCustomerData = oldCustomerData.get();
            newCustomerData.setBalance(requestBody.getBalance());
            newCustomerData.setPoint(requestBody.getPoint());

            log.debug("Save new customer data with repository.");
            newCustomerData = customerRepository.save(newCustomerData);

            log.debug("Update customer data success.");
            return ResponseUtil.build(HttpStatus.OK, "Customer data updated.", null);
        }else {
            log.debug("Update customer data failed.");
            return ResponseUtil.build(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }
    }

}
