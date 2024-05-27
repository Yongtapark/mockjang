package myproject.mockjang.domain.note_parser;


import static myproject.mockjang.exception.Exceptions.DOMAIN_NOTE_FORMAT;
import static myproject.mockjang.exception.Exceptions.valueOf;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import myproject.mockjang.exception.note_parser.NoteFormatException;
import org.springframework.stereotype.Component;

@Component
public class NoteParserV0 implements NoteParser{

  @Override
  public NoteContainer extractAndSaveNotes(NoteContainer noteContainer, String content) {
    String[] split = content.split(System.lineSeparator());
    NoteAndIdList noteAndIdList = new NoteAndIdList(new ArrayList<>());
    for (String eachContent : split) {
      Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContent);
      if (extractIdAndNote.matches()) {
        String ids = extractIdAndNote.group(1);
        String note = extractIdAndNote.group(2);
        String[] idArray = ids.split(",");

        findEachRegexAndSaveNotes(noteContainer, ids, idArray, noteAndIdList, note);
        checkDissmatchIds(eachContent, idArray);
      } else {
        throw new NoteFormatException(DOMAIN_NOTE_FORMAT, content);
      }
    }
    return noteContainer;
  }

  private void findEachRegexAndSaveNotes(NoteContainer noteContainer, String ids, String[] idArray,
      NoteAndIdList noteAndIdList, String note) {
    for (NoteRegex regex : NoteRegex.values()) {
      Matcher regexMatcher = regex.getCompile().matcher(ids);
      saveIfRegexMatch(noteContainer, idArray, noteAndIdList, note, regex, regexMatcher);
    }
  }

  private void saveIfRegexMatch(NoteContainer noteContainer, String[] idArray,
      NoteAndIdList noteAndIdList, String note, NoteRegex regex, Matcher regexMatcher) {
    if (regexMatcher.matches()) {
      for (int i = 0; i < idArray.length; i++) {
        noteAndIdList.add(new NoteAndId(idArray[i], note));
        idArray[i] = null;
      }
      noteContainer.putNotes(regex, noteAndIdList.getImmutableNoteAndIdList());
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
