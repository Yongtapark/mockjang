package myproject.mockjang.exception.note_parser;

import myproject.mockjang.exception.Exceptions;

public class NoteFormatException extends IllegalArgumentException {

  public NoteFormatException(Exceptions exception) {
    super(exception.getMessage());
  }

  public NoteFormatException(Exceptions exception, String content) {
    super(String.format(exception.getMessage(), content));
  }

}
