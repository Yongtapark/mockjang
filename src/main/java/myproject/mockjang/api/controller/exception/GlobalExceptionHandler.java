package myproject.mockjang.api.controller.exception;

import lombok.RequiredArgsConstructor;
import myproject.mockjang.exception.CowStatusException;
import myproject.mockjang.exception.NegativeNumberException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler({NegativeNumberException.class, CowStatusException.class})
  public ResponseEntity<String> handleException(IllegalArgumentException ex) {
    String errorMessage = getErrorMessage(ex.getMessage());
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  private String getErrorMessage(String messageKey) {
    return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
  }
}
