package myproject.mockjang.exception;

import myproject.mockjang.domain.mockjang.Mockjang;

public class MockjangException extends IllegalArgumentException {

  public MockjangException(String message) {
    super(message);
  }

  public MockjangException(Exceptions exception, String message) {
    this(String.format(exception.getMessage(), message));

  }

  public MockjangException(Mockjang mockjang, Exceptions exception) {
    this(String.format(exception.getMessage(), mockjang.getClass().getSimpleName()));
  }
}
