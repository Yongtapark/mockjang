package myproject.mockjang.exception.common;

import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

public class AlreadyExistException extends MockjangException {

  public AlreadyExistException(String message) {
    super(message);
  }

  public AlreadyExistException(Exceptions exception, String message) {
    super(exception, message);
  }
}
