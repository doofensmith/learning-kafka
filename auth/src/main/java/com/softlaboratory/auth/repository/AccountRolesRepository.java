package com.softlaboratory.auth.repository;

import com.softlaboratory.auth.domain.dao.AccountRolesDao;
import com.softlaboratory.auth.domain.dao.AccountRolesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRolesRepository extends JpaRepository<AccountRolesDao, AccountRolesKey> {
}
