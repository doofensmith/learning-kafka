package payment.domain.dao;

import basecomponent.common.BaseDaoSoftDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_payment")
@SQLDelete(sql = "update t_payment set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=false")
public class PaymentDao extends BaseDaoSoftDelete implements Serializable {

    private static final long serialVersionUID = 3848753497774854284L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "req_amount", nullable = false)
    private Double reqAmount;

    @Column(name = "settle_amount", nullable = false)
    private Double settleAmount;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "paid_by", nullable = false, length = 50)
    private String paidBy;

    @Column(nullable = false)
    private Long idTransaction;

}
