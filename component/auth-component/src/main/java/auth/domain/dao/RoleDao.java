package auth.domain.dao;

import basecomponent.common.BaseDaoSoftDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_role")
@SQLDelete(sql = "update t_role set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=true")
public class RoleDao extends BaseDaoSoftDelete implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -8783233358906752990L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String role;

    @Override
    public String getAuthority() {
        return this.role;
    }

}
