package transaction.domain.dao;

import basecomponent.common.BaseDaoSoftDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_transaction")
@SQLDelete(sql = "update t_transaction set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=false")
public class TransactionDao extends BaseDaoSoftDelete implements Serializable {

    private static final long serialVersionUID = 6709603699631032923L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "settle_at")
    private LocalDateTime settleAt;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private String status;

    @Column(name = "id_product", nullable = false)
    private Long idProduct;

    @Column(name = "id_account", nullable = false)
    private Long idAccount;

}
