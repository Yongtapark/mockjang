package myproject.mockjang.exception.schdules;

import myproject.mockjang.exception.Exceptions;

public class ScheduleFormException extends IllegalArgumentException{
    public ScheduleFormException(Exceptions exception) {
        super(exception.getMessage());
    }

    public ScheduleFormException(Exceptions exception, String content) {
        super(String.format(exception.getMessage(), content));
    }
}
