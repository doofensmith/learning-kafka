package com.softlaboratory.auth.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Embeddable
public class AccountRolesKey implements Serializable {

    private static final long serialVersionUID = 3462095315128669043L;

    @Column(name = "id_account")
    private Long idAccount;

    @Column(name = "id_role")
    private Long idRole;

}
