package myproject.mockjang.exception.record;

import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

public class RecordException extends MockjangException {

  public RecordException(Exceptions exceptions) {
    super(exceptions.getMessage());
  }

  public RecordException(String message) {
    super(message);
  }
}
