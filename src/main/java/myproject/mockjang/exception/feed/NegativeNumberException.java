package myproject.mockjang.exception.feed;

import lombok.Getter;
import myproject.mockjang.exception.Exceptions;

@Getter
public class NegativeNumberException extends IllegalArgumentException {

  public NegativeNumberException(Exceptions exception) {
    super(exception.getMessage());
  }

}
