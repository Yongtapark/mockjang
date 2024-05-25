package myproject.mockjang.exception;

import lombok.Getter;
import myproject.mockjang.domain.Exceptions;

@Getter
public class CowStatusException extends IllegalArgumentException{

  public CowStatusException(Exceptions exception) {
    super(exception.getMessage());
  }

}
