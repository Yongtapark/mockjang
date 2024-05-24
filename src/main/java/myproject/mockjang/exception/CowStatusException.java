package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public class CowStatusException extends IllegalArgumentException{

  private final String messageKey;

  public CowStatusException(String messageKey) {
    super(messageKey);
    this.messageKey = messageKey;
  }

}
