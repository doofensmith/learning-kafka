package com.softlaboratory.customer.repository;

import com.softlaboratory.customer.domain.dao.ProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileDao, Long> {

    @Query("select p from ProfileDao p where p.idAccount = ?1")
    Optional<ProfileDao> findByIdAccountEquals(Long idAccount);

}
