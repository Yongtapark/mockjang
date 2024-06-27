package myproject.mockjang.exception.common;

import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

public class UpperGroupAlreadyExistException extends MockjangException {

    public UpperGroupAlreadyExistException(Mockjang mockjang, Exceptions exception) {
        super(mockjang, exception);
    }
}
