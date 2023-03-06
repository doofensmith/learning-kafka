package com.softlaboratory.auth.repository;

import com.softlaboratory.auth.domain.dao.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDao, Long>, JpaSpecificationExecutor<AccountDao> {

    AccountDao getDistinctTopByUsername(String username);

    @Query("select a from AccountDao a where a.username = ?1")
    Optional<AccountDao> findByUsernameEquals(String username);

    @Query("select count(a) from AccountDao a")
    Long countTotalUser();

    @Query("select count(a) from AccountDao a where a.isActive = true")
    Long getTotalActiveUser();

    @Query("select count(a) from AccountDao a where a.isActive = false")
    Long getTotalInactiveUser();

}
