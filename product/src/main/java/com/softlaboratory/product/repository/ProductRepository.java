package com.softlaboratory.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import product.dao.ProductDao;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, Long> {

    @Query("select distinct p from ProductDao p where upper(p.name) like upper(?1) order by p.id")
    List<ProductDao> findDistinctByNameLikeIgnoreCaseOrderByIdAsc(String name);

}
