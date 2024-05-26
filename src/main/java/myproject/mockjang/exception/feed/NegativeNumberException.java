package myproject.mockjang.exception.feed;

import lombok.Getter;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

@Getter
public class NegativeNumberException extends MockjangException {

  public NegativeNumberException(Exceptions exception) {
    super(exception.getMessage());
  }

}
