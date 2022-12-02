package auth.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 7850675210981009818L;

    private String fullname;
    private String username;
    private String password;

}
