package myproject.mockjang.exception.common;

import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

public class ThereIsNoGroupException extends MockjangException {

    public ThereIsNoGroupException(Exceptions exception, Mockjang mockjang) {
        super(mockjang, exception);
    }
}
