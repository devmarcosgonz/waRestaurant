package waRestaurant.category.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CategoryDto {
  private Long categoryId;
  private String name;
}
