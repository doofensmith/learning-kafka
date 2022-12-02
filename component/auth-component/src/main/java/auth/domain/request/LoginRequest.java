package auth.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = -1546540182089069267L;

    private String username;
    private String password;

}
