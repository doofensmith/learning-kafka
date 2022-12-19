package transaction.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import transaction.constant.TransactionStatus;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateTransactionRequest implements Serializable {

    private static final long serialVersionUID = 8576644297608936085L;

    private TransactionStatus status;

}
