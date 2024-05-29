package myproject.mockjang.domain.note_parser;


import static myproject.mockjang.exception.Exceptions.DOMAIN_NOTE_FORMAT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import myproject.mockjang.exception.note_parser.NoteFormatException;
import org.springframework.stereotype.Component;

@Component
public class NoteParserV0 implements NoteParser {

  @Override
  public NoteContainer extractAndSaveNotes(NoteContainer noteContainer, String content) {
    String[] split = content.split(System.lineSeparator());
    for (String eachContent : split) {
      Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContent);
      if (extractIdAndNote.matches()) {
        String ids = extractIdAndNote.group(1);
        String note = extractIdAndNote.group(2);
        String[] idArray = ids.split(",");

        findEachRegexAndSaveNotes(noteContainer, ids, idArray, note);
        checkDissmatchIds(eachContent, idArray);
      } else {
        throw new NoteFormatException(DOMAIN_NOTE_FORMAT, content);
      }
    }
    return noteContainer;
  }

  private void findEachRegexAndSaveNotes(NoteContainer noteContainer, String ids, String[] idArray,
      String note) {
    for (NoteRegex regex : NoteRegex.values()) {
      Matcher regexMatcher = regex.getCompile().matcher(ids);
      saveIfRegexMatch(noteContainer, idArray, note, regex, regexMatcher);
    }
  }

  private void saveIfRegexMatch(NoteContainer noteContainer, String[] idArray,
      String note, NoteRegex regex, Matcher regexMatcher) {
    ArrayList<NoteAndCodeId> noteAndCodeIds = new ArrayList<>();
    if (regexMatcher.matches()) {
      for (int i = 0; i < idArray.length; i++) {
        noteAndCodeIds.add(new NoteAndCodeId(idArray[i], note));
        idArray[i] = null;
      }
      noteContainer.putNotes(regex, noteAndCodeIds);
    }
  }

  private void checkDissmatchIds(String eachContent, String[] idArray) {
    for (int i = 0; i < idArray.length; i++) {
      if (idArray[i] != null) {
        throw new NoteFormatException(DOMAIN_NOTE_FORMAT, eachContent);
      }
    }
  }
}
