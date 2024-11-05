package waRestaurant.commons;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<CustomErrorMessage> handleGlobalError(Exception ex) {

    if (ex instanceof CustomException) {
      CustomException customException = (CustomException) ex;
      log.error("Error de aplicacion ", ex);
      return ResponseEntity.status(customException.getStatus())
          .body(CustomErrorMessage.builder()
              .code(customException.getCode())
              .message(customException.getMessage()).build());
    } else if (ex instanceof MethodArgumentNotValidException) {
      log.error("Argumentos invalidos ", ex);
      MethodArgumentNotValidException argError = (MethodArgumentNotValidException) ex;
      BindingResult result = argError.getBindingResult();
      List<FieldError> fieldErrors = result.getFieldErrors();
      StringBuilder errorMessage = new StringBuilder();
      fieldErrors.forEach(error ->
          errorMessage.append(
              "Argumento [" + error.getField() + "]: " + error.getDefaultMessage() + ". "));
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(CustomErrorMessage.builder().code("EG001").message(errorMessage.toString()).build());
    }

    log.error("Error general ", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(CustomErrorMessage.builder().code("EG001").message("Error General").build());
  }
}
