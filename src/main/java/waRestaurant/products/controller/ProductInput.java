package waRestaurant.products.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInput {

  @NotBlank
  private String name;
  @NotNull
  private Double price;
  @NotNull
  private Long categoryId;
  private Integer stockActual;
  private Integer stockMinimo;
}
