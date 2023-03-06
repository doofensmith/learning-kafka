package com.softlaboratory.auth.service.impl;

import auth.domain.dto.RoleDto;
import auth.domain.dto.UserDto;
import auth.domain.dto.UserInfoDto;
import auth.domain.request.GrantRoleRequest;
import basecomponent.common.PageResponse;
import basecomponent.query.SearchRequest;
import basecomponent.query.SearchSpecification;
import basecomponent.utility.ResponseUtil;
import com.softlaboratory.auth.domain.dao.AccountDao;
import com.softlaboratory.auth.domain.dao.AccountRolesDao;
import com.softlaboratory.auth.domain.dao.AccountRolesKey;
import com.softlaboratory.auth.domain.dao.RoleDao;
import com.softlaboratory.auth.repository.AccountRepository;
import com.softlaboratory.auth.repository.AccountRolesRepository;
import com.softlaboratory.auth.repository.RoleRepository;
import com.softlaboratory.auth.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRolesRepository accountRolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountDao account = accountRepository.getDistinctTopByUsername(username);
        if (account != null) {
            return account;
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    @Override
    public ResponseEntity<Object> getAllUserPagination(Integer page, Integer size) {
        log.debug("Starting get all user with page.");
        log.debug("Request page : {}, size : {}.", page, size);

        log.debug("Default page and size.");
        Pageable pageable = PageRequest.of(
                Objects.requireNonNullElse(page, 0),
                Objects.requireNonNullElse(size, 10)
        );

        log.debug("Fetch list user with repository.");
        Page<AccountDao> accounts = accountRepository.findAll(pageable);

        log.debug("Convert to data presentation.");
        List<UserDto> users = new ArrayList<>();
        accounts.forEach(accountDao -> {
            List<String> roles = new ArrayList<>();
            accountDao.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
            users.add(
                    UserDto.builder()
                            .id(accountDao.getId())
                            .username(accountDao.getUsername())
                            .isActive(accountDao.getIsActive())
                            .loginAttemp(accountDao.getLoginAttemp())
                            .roles(roles)
                            .build()
            );
        });

        log.debug("Creating page response.");
        PageResponse pageResponse = PageResponse.builder()
                .totalElements(accounts.getTotalElements())
                .totalPages(accounts.getTotalPages())
                .data(users)
                .build();

        log.debug("Get all users pagination success.");
        return ResponseUtil.build(HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), pageResponse);
    }

    @Override
    public ResponseEntity<Object> searchAllUser(SearchRequest request) {
        log.debug("Starting search users.");

        log.debug("Setting search specification.");
        SearchSpecification<AccountDao> specification = new SearchSpecification<>(request);
        Pageable pageable = SearchSpecification.getPageable(request.getPage(), request.getSize());

        log.debug("Fetch data with repository.");
        Page<AccountDao> usersDao = accountRepository.findAll(specification, pageable);

        log.debug("Convert to data presentation.");
        List<UserDto> usersDto = new ArrayList<>();
        usersDao.forEach(accountDao -> {
            List<String> roles = new ArrayList<>();
            accountDao.getAuthorities().forEach(authority -> {
                roles.add(authority.getAuthority());
            });
            usersDto.add(
                    UserDto.builder()
                            .id(accountDao.getId())
                            .username(accountDao.getUsername())
                            .isActive(accountDao.getIsActive())
                            .loginAttemp(accountDao.getLoginAttemp())
                            .roles(roles)
                            .build()
            );
        });

        log.debug("Creating page response.");
        PageResponse pageResponse = PageResponse.builder()
                .totalPages(usersDao.getTotalPages())
                .totalElements(usersDao.getTotalElements())
                .data(usersDto)
                .build();

        log.debug("Search all users success.");
        return ResponseUtil.build(HttpStatus.OK, "Search all users success.", pageResponse);
    }

    @Override
    public ResponseEntity<Object> getUsersInfo() {
        log.debug("Starting get users stat info.");

        log.debug("Get total users with repository.");
        Long totalUsers = accountRepository.countTotalUser();

        log.debug("Get total active users with repository.");
        Long totalActiveUsers = accountRepository.getTotalActiveUser();

        log.debug("Get total inactive users with repository.");
        Long totalInactiveUsers = accountRepository.getTotalInactiveUser();

        log.debug("Convert to data presentation.");
        UserInfoDto usersStatusInfo = UserInfoDto.builder()
                .totalUsers(totalUsers)
                .totalActiveUsers(totalActiveUsers)
                .totalInactiveUsers(totalInactiveUsers)
                .build();

        log.debug("Get users stat info success.");
        return ResponseUtil.build(HttpStatus.OK, "Get users status success", usersStatusInfo);
    }

    @Override
    public ResponseEntity<Object> getAllRole() {
        log.debug("Starting get all role data.");

        log.debug("Fetching data with repository.");
        List<RoleDao> roleDaoList = roleRepository.findAll();

        log.debug("Converting to data presentation.");
        List<RoleDto> roleDtoList = new ArrayList<>();
        roleDaoList.forEach(roleDao -> {
            roleDtoList.add(RoleDto.builder()
                    .id(roleDao.getId())
                    .role(roleDao.getRole())
                    .build());
        });

        log.debug("Get all role success.");
        return ResponseUtil.build(HttpStatus.OK, "Get all role data success.", roleDtoList);
    }

    @Override
    public ResponseEntity<Object> grantRole(GrantRoleRequest request) {
        log.debug("Starting granting role to user.");

        log.debug("Check user is exist.");
        Optional<AccountDao> accountDao = accountRepository.findById(request.getIdUser());
        if (accountDao.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }

        log.debug("Get role data.");
        RoleDao roleDao = roleRepository.findByRoleEqualsIgnoreCase(request.getRole().name());

        log.debug("Adding role to user data.");
        AccountRolesDao accountRolesDao = AccountRolesDao.builder()
                .id(AccountRolesKey.builder()
                        .idAccount(accountDao.get().getId())
                        .idRole(roleDao.getId())
                        .build())
                .build();

        log.debug("Save added role with repository.");
        accountRolesRepository.save(accountRolesDao);

        log.debug("Grant role to user success.");
        return ResponseUtil.build(HttpStatus.OK, "Grant role success.", null);
    }

    @Override
    public ResponseEntity<Object> removeRole() {
        return null;
    }

    @Override
    public ResponseEntity<Object> activateUser() {
        return null;
    }

    @Override
    public ResponseEntity<Object> deactivateUser() {
        return null;
    }
}
