package auth.domain.dao;

import basedomain.common.BaseDaoSoftDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_profile")
@SQLDelete(sql = "update t_profile set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=false")
public class ProfileDao extends BaseDaoSoftDelete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String fullname;

    @Column(length = 50)
    private String email;

    @Column(length = 20)
    private String phoneNumber;

}