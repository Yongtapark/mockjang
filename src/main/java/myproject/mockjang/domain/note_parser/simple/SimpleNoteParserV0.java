package myproject.mockjang.domain.note_parser.simple;


import static myproject.mockjang.exception.Exceptions.DOMAIN_NOTE_FORMAT;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.RecordAndCodeId;
import myproject.mockjang.domain.note_parser.NoteRegex;
import myproject.mockjang.exception.note_parser.NoteFormatException;
import org.springframework.stereotype.Component;

@Component
public class SimpleNoteParserV0 implements NoteParser<SimpleRecordContainer> {

  @Override
  public SimpleRecordContainer extractAndSaveNotes(SimpleRecordContainer noteContainer,
                                                   String content) {
    String[] split = content.split("\\r?\\n");
    ArrayList<RecordAndCodeId> recordAndCodeIds = new ArrayList<>();
    for (String eachContent : split) {
      if (isEmptyLine(eachContent)) {
        continue;
      }
      Matcher annotationFormMatcher = NoteRegex.getAnnotationFormMatcher(eachContent);
      if(annotationFormMatcher.matches()) {
        continue;
      }
      Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContent);
      if (extractIdAndNote.matches()) {
        String[] idArray = extractIdAndNote.group(1).split(",");
        String note = extractIdAndNote.group(2);

        saveEach(idArray, note, recordAndCodeIds);
        noteContainer.registerNoteAndIds(recordAndCodeIds);
        checkDissmatchIds(eachContent, idArray);
      } else {
        throw new NoteFormatException(DOMAIN_NOTE_FORMAT, content);
      }
    }
    return noteContainer;
  }

  private static boolean isEmptyLine(String eachContent) {
    if(eachContent.isBlank()){
      return true;
    }
    return false;
  }

  private void saveEach(String[] idArray, String note,
      List<RecordAndCodeId> recordAndCodeIds) {
    for (int i = 0; i < idArray.length; i++) {
      recordAndCodeIds.add(new RecordAndCodeId(idArray[i], note));
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
