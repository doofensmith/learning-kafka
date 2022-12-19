package product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import product.domain.dao.ProductDao;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, Long> {

    @Transactional
    @Modifying
    @Query("update ProductDao p set p.stock = ?1 where p.id = ?2")
    int updateStockByIdEquals(int stock, Long id);

}
