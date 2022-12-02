package auth.domain.dao;

import basecomponent.common.BaseDaoSoftDelete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "t_account_master")
@SQLDelete(sql = "update t_account_master set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=false")
public class AccountDao extends BaseDaoSoftDelete implements UserDetails, Serializable {

    private static final long serialVersionUID = -8806133360169738965L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isActive;

    @Column(length = 1, columnDefinition = "smallint default 0")
    private Integer loginAttemp;

    @OneToOne
    @JoinColumn(name = "id_profile", nullable = false)
    private ProfileDao profile;

    @ManyToMany
    @JoinTable(
            name = "bt_account_roles",
            joinColumns = @JoinColumn(name = "id_account"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private List<RoleDao> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

}
