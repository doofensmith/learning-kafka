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
public class UpdateCustomerRequest implements Serializable {

    private static final long serialVersionUID = 1470466714294587769L;

    private Double balance;
    private Double point;

}
