package auth.repository;

import auth.domain.dao.AccountDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDao, Long> {

    AccountDao getDistinctTopByUsername(String username);

}
