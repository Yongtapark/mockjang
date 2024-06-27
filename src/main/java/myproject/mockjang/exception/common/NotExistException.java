package myproject.mockjang.exception.common;

import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

public class NotExistException extends MockjangException {

    public NotExistException(String message) {
        super(message);
    }

    public NotExistException(Exceptions exception, String message) {
        super(exception, message);
    }
}
