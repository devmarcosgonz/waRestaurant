package waRestaurant.order.domain;

import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class CommandsDto {

    private String title;
    private Long mesaId;
    private String product;
    private Integer quantity;
    private String observation;

}