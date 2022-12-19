package notification.domain.dao;

import basecomponent.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_notification")
public class NotificationDao extends BaseDao implements Serializable {

    private static final long serialVersionUID = -1956623930859358078L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 50, columnDefinition = "varchar(50) default 'SYSTEM'")
    private String publisher = "SYSTEM";

    @Column(length = 50, columnDefinition = "varchar(50) default 'EVERYONE'")
    private String receiver = "EVERYONE";

}
