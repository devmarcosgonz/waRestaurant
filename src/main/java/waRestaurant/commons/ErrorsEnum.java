package waRestaurant.commons;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorsEnum {

  CATEGORY_NOT_FOUND("EC001", "Error not found category", HttpStatus.NOT_FOUND),
  CATEGORY_SAVE_ERROR("EC002", "Error while saving category", HttpStatus.INTERNAL_SERVER_ERROR),
  CATEGORY_UPDATE_ERROR("EC003", "Error while updating category", HttpStatus.INTERNAL_SERVER_ERROR),
  CATEGORY_DELETE_ERROR("EC004", "Error while deleting category", HttpStatus.INTERNAL_SERVER_ERROR),

  CLIENT_NOT_FOUND("ECL01", "Error not found client", HttpStatus.NOT_FOUND),
  CLIENT_SAVE_ERROR("ECL02", "Error while saving client", HttpStatus.INTERNAL_SERVER_ERROR),
  CLIENT_UPDATE_ERROR("ECL03", "Error while updating client", HttpStatus.INTERNAL_SERVER_ERROR),
  CLIENT_DELETE_ERROR("ECL04", "Error while deleting client", HttpStatus.INTERNAL_SERVER_ERROR),

  PRODUCT_NOT_FOUND("EP001", "Error not found product", HttpStatus.NOT_FOUND),
  PRODUCT_SAVE_ERROR("EP002", "Error while saving product", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_UPDATE_ERROR("EP003", "Error while updating product", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_DELETE_ERROR("EP004", "Error while deleting product", HttpStatus.INTERNAL_SERVER_ERROR),

  ORDER_NOT_FOUND("EOR01", "Error not found order", HttpStatus.NOT_FOUND),
  ORDER_SAVE_ERROR("EOR02", "Error while saving order", HttpStatus.INTERNAL_SERVER_ERROR),
  ORDER_DELETE_ERROR("EOR03", "Error while deleting order", HttpStatus.INTERNAL_SERVER_ERROR),

  MESA_NOT_FOUND("EME01", "Error not found mesa or is close", HttpStatus.NOT_FOUND),
  MESA_SAVE_ERROR("EME02", "Error while saving mesa", HttpStatus.INTERNAL_SERVER_ERROR),
  MESA_EXIST_ERROR("EME02", "Error mesa exist try with another number", HttpStatus.INTERNAL_SERVER_ERROR),
  MESA_CLOSE_ERROR("EME03", "Error while closing client", HttpStatus.INTERNAL_SERVER_ERROR),

  RESERVATION_NOT_FOUND("ERR01", "Error while reservation", HttpStatus.INTERNAL_SERVER_ERROR);

  String code;
  String message;
  HttpStatus status;
}
