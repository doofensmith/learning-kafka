package payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payment.domain.dao.PaymentDao;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDao, Long> {

}
