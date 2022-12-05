package product.domain.dao;

import basecomponent.common.BaseDaoSoftDelete;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "t_product")
@SQLDelete(sql = "update t_product set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=false")
public class ProductDao extends BaseDaoSoftDelete implements Serializable {

    private static final long serialVersionUID = -6721988046538752981L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT", length = 400)
    private String description;

    @Column(nullable = false, length = 20)
    private double price;

    @Column(nullable = false, length = 5)
    private int stock;

}
