package transaction.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionDto implements Serializable {

    private static final long serialVersionUID = -3089954322335701987L;

    private Long id;
    private LocalDateTime issuedAt;
    private LocalDateTime settleAt;
    private String productName;
    private Integer quantity;
    private Double total;
    private String status;
    private String customer;
    private Long idProduct;
    private Long idAccount;

}
