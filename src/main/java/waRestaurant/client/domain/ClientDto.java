package waRestaurant.client.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ClientDto {

  private Long clientId;
  private String name;
  private String phone;
  private String email;
}