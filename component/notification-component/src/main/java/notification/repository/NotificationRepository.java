package notification.repository;

import notification.domain.dao.NotificationDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationDao, Long> {

    @Query("select n from NotificationDao n where n.receiver = 'EVERYONE' or n.receiver = ?1 order by n.id DESC")
    Page<NotificationDao> findByReceiverEqualsOrderByIdDesc(String receiver, Pageable pageable);

}
