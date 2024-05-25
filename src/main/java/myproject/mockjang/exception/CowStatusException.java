package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public class CowStatusException extends IllegalArgumentException{

  public CowStatusException(String messageKey) {
    super(messageKey);
  }

}
