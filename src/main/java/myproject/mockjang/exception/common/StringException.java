package myproject.mockjang.exception.common;

import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

public class StringException extends MockjangException {

  public StringException(Exceptions exception) {
    super(exception.getMessage());
  }
}
