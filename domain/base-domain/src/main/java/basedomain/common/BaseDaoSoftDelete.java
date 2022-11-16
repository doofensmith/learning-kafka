package basedomain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public class BaseDaoSoftDelete extends BaseDao {

    @Column(name = "deleted_at", length = 50)
    private LocalDateTime deletedAt;

    @Column(name = "is_deleted", length = 6)
    private Boolean isDeleted;

    @Override
    void onCreate() {
        super.onCreate();
        this.isDeleted = Boolean.FALSE;
    }

}
