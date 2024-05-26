package myproject.mockjang.exception.common;

import myproject.mockjang.exception.Exceptions;

public class UpperGroupAlreadyExistException extends IllegalArgumentException{

  public UpperGroupAlreadyExistException(Exceptions exception) {
    super(exception.getMessage());
  }
}
