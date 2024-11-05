package waRestaurant.commons;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomErrorMessage {

  private String code;
  private String message;
}
