package myproject.mockjang.domain.note_parser.simple;


import static myproject.mockjang.exception.Exceptions.DOMAIN_NOTE_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.NoteAndCodeId;
import myproject.mockjang.domain.note_parser.mockjang.NoteRegex;
import myproject.mockjang.exception.note_parser.NoteFormatException;
import org.springframework.stereotype.Component;

@Component
public class SimpleNoteParserV0 implements NoteParser<SimpleNoteContainer> {

  @Override
  public SimpleNoteContainer extractAndSaveNotes(SimpleNoteContainer noteContainer,
      String content) {
    String[] split = content.split(System.lineSeparator());
    ArrayList<NoteAndCodeId> noteAndCodeIds = new ArrayList<>();
    for (String eachContent : split) {
      Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContent);
      if (extractIdAndNote.matches()) {
        String[] idArray = extractIdAndNote.group(1).split(",");
        String note = extractIdAndNote.group(2);

        saveEach(idArray, note, noteAndCodeIds);
        noteContainer.registerNoteAndIds(noteAndCodeIds);
        checkDissmatchIds(eachContent, idArray);
      } else {
        throw new NoteFormatException(DOMAIN_NOTE_FORMAT, content);
      }
    }
    return noteContainer;
  }

  private void saveEach(String[] idArray, String note,
      List<NoteAndCodeId> noteAndCodeIds) {
    for (int i = 0; i < idArray.length; i++) {
      noteAndCodeIds.add(new NoteAndCodeId(idArray[i], note));
      idArray[i] = null;
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
