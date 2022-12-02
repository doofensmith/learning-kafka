package basecomponent.common;

import basecomponent.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public class BaseDao {

    @Column(name = "created_at", nullable = false, length = 50)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_at", length = 50)
    private LocalDateTime updatedAt;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now(ZoneId.of(AppConstant.APP_TIMEZONE));
        this.createdBy = "SYSTEM";
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now(ZoneId.of(AppConstant.APP_TIMEZONE));
        this.updatedBy = "SYSTEM";
    }

}
