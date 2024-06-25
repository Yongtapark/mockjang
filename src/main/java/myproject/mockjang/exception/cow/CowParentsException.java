package myproject.mockjang.exception.cow;

import lombok.Getter;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

@Getter
public class CowParentsException extends MockjangException {

  public CowParentsException(Exceptions exception) {
    super(exception.getMessage());
  }

  public CowParentsException(String message) {
    super(message);
  }
}
