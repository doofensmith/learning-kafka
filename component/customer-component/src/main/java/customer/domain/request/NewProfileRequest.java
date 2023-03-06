package customer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NewProfileRequest implements Serializable {

    private static final long serialVersionUID = -2564105030162112693L;

    private Long idAccount;
    private String fullname;
    private String profilePic;
    private String email;
    private String phoneNumber;
    private Double balance;
    private Double point;

}
