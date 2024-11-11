package waRestaurant.client.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientInput {

  @NotBlank
  private String name;
  private String phone;
  private String email;
}
