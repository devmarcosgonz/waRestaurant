package waRestaurant.order.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class CommandsDto {

    private String title;
    private Long mesaId;
    private String product;
    private Integer quantity;
    private String observation;

}