package product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 4394251879864299642L;
    
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;

}
