package com.softlaboratory.auth.repository;

import com.softlaboratory.auth.domain.dao.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountDao, Long> {

    AccountDao getDistinctTopByUsername(String username);

    @Query("select a from AccountDao a where a.username = ?1")
    Optional<AccountDao> findByUsernameEquals(String username);

}
