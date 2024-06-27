package myproject.mockjang.exception.cow;

import lombok.Getter;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.MockjangException;

@Getter
public class CowStatusException extends MockjangException {

    public CowStatusException(Exceptions exception) {
        super(exception.getMessage());
    }

}
