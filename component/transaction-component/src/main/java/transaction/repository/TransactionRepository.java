package transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import transaction.domain.dao.TransactionDao;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionDao, Long> {

    @Transactional
    @Modifying
    @Query("update TransactionDao t set t.status = ?1 where t.id = ?2")
    void updateStatusByIdEquals(@NonNull String status, Long id);

    @Transactional
    @Modifying
    @Query("update TransactionDao t set t.total = ?1 where t.id = ?2")
    int updateTotalByIdEquals(@NonNull Double total, Long id);

}
