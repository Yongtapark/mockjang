package myproject.mockjang.exception.cow;

import lombok.Getter;
import myproject.mockjang.exception.Exceptions;

@Getter
public class CowStatusException extends IllegalArgumentException{

  public CowStatusException(Exceptions exception) {
    super(exception.getMessage());
  }

}
