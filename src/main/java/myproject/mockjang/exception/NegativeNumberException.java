package myproject.mockjang.exception;

import lombok.Getter;
import myproject.mockjang.domain.Exceptions;

@Getter
public class NegativeNumberException extends IllegalArgumentException {

  public NegativeNumberException(Exceptions exception) {
    super(exception.getMessage());
  }

}
