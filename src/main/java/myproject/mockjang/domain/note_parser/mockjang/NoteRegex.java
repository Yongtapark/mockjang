package myproject.mockjang.domain.note_parser.mockjang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;

public enum NoteRegex {
  BARN(Pattern.compile("^(\\d+번축사)(,\\d+번축사)*$")),
  PEN(Pattern.compile("^(\\d+-\\d+)(,\\d+-\\d+)*$")),
  COW(Pattern.compile("^((\\d{4})(,\\d{4})*)$"));

  private final Pattern pattern;

  @Getter
  private static String frameRegex = "\\[\\[([^\\]]+)\\]\\]\\s*(.*)";

  NoteRegex(Pattern pattern) {
    this.pattern = pattern;
  }

  public Matcher extractString(String strPattern) {
    return getCompile().matcher(strPattern);
  }

  public Pattern getCompile() {
    return pattern;
  }

  public static Matcher getNoteFormMatcher(String strPattern) {
    return Pattern.compile(frameRegex).matcher(strPattern);
  }

}
