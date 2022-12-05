package auth.repository;

import auth.domain.dao.RoleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDao, Long> {

    @Query("select r from RoleDao r where upper(r.role) = upper(?1)")
    RoleDao findByRoleEqualsIgnoreCase(String role);

}
