package product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.domain.dao.ProductDao;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, Long> {
}
