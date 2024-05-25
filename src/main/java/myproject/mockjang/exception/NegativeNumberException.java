package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public class NegativeNumberException extends IllegalArgumentException {

  public NegativeNumberException(String messageKey) {
    super(messageKey);
  }

}
