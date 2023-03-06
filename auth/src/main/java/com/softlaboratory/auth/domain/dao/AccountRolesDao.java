package com.softlaboratory.auth.domain.dao;

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
@Table(name = "bt_account_roles")
@SQLDelete(sql = "update bt_account_roles set is_deleted=true, deleted_at=current_timestamp where id=?")
@Where(clause = "is_deleted=false")
public class AccountRolesDao extends BaseDaoSoftDelete implements Serializable {

    private static final long serialVersionUID = 1187799981826702452L;

    @EmbeddedId
    private AccountRolesKey id;

    @ManyToOne
    @MapsId("idAccount")
    @JoinColumn(name = "id_account")
    private AccountDao account;

    @ManyToOne
    @MapsId("idRole")
    @JoinColumn(name = "id_role")
    private RoleDao role;

}
