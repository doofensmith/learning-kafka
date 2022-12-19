package customer.repository;

import customer.domain.dao.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDao, Long> {

    @Query("select c from CustomerDao c where c.idAccount = ?1")
    Optional<CustomerDao> findByIdAccountEquals(Long idAccount);

}
