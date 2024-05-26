package myproject.mockjang.exception.record;

import myproject.mockjang.exception.Exceptions;

public class RecordException extends IllegalArgumentException{

  public RecordException(Exceptions exceptions) {
    super(exceptions.getMessage());
  }
}
