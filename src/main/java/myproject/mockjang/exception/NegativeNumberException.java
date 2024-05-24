package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public class NegativeNumberException extends IllegalArgumentException {

  private final String messageKey;

  public NegativeNumberException(String messageKey) {
    super(messageKey);
    this.messageKey = messageKey;
  }

}
