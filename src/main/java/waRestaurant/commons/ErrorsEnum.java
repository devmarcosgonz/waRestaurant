package waRestaurant.commons;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorsEnum {

  CATEGORY_NOT_FOUND("EC001", "Error not found category", HttpStatus.NOT_FOUND),
  CATEGORY_SAVE_ERROR("EC002", "Error while saving category", HttpStatus.INTERNAL_SERVER_ERROR),
  CATEGORY_UPDATE_ERROR("EC003", "Error while updating category", HttpStatus.INTERNAL_SERVER_ERROR),
  CATEGORY_DELETE_ERROR("EC004", "Error while deleting category", HttpStatus.INTERNAL_SERVER_ERROR),

  PRODUCT_NOT_FOUND("EP001", "Error not found product", HttpStatus.NOT_FOUND),
  PRODUCT_SAVE_ERROR("EP002", "Error while saving product", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_UPDATE_ERROR("EP003", "Error while updating product", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_DELETE_ERROR("EP004", "Error while deleting product", HttpStatus.INTERNAL_SERVER_ERROR);

  String code;
  String message;
  HttpStatus status;
}
