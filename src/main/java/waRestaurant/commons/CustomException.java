package waRestaurant.commons;

import java.util.concurrent.CompletionException;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class CustomException extends RuntimeException {
  private HttpStatus status;
  private String code;
  private String message;

  public CustomException(ErrorsEnum error) {
    super();
    this.status = error.status;
    this.code = error.code;
    this.message = error.message;
  }

  public static Supplier<CustomException> errorSupplier(ErrorsEnum errorsEnum) {
    return () -> new CustomException(errorsEnum);
  }

  public static Supplier<CustomException> errorSupplier(ErrorsEnum errorsEnum, String message) {
    if (Strings.isNotBlank(message)) {
      log.error(message);
    }

    return () -> new CustomException(errorsEnum);
  }

  public static CustomException throwError(ErrorsEnum errorsEnum, String logMessage, Exception ex)
      throws CustomException {
    log.error(logMessage, ex);
    if (ex instanceof CustomException) {
      return (CustomException) ex;
    } else if (ex instanceof CompletionException && ex.getCause() instanceof CustomException) {
      return (CustomException) ex.getCause();
    }
    return errorSupplier(errorsEnum).get();
  }

  public static CustomException throwError(ErrorsEnum errorsEnum) throws CustomException {
    log.error("{}:{}", errorsEnum.code, errorsEnum.message);
    return errorSupplier(errorsEnum).get();
  }

}
