package myproject.mockjang.domain.note_parser.mockjang;


import static myproject.mockjang.exception.Exceptions.DOMAIN_NOTE_FORMAT;

import java.util.ArrayList;
import java.util.regex.Matcher;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.NoteRegex;
import myproject.mockjang.exception.note_parser.NoteFormatException;
import org.springframework.stereotype.Component;

@Component
public class NoteParserV0 implements NoteParser<MockjangNoteContainer> {

    @Override
    public MockjangNoteContainer extractAndSaveNotes(MockjangNoteContainer mockjangNoteContainer,
                                                     String content) {
        String[] split = content.split(System.lineSeparator());
        for (String eachContent : split) {
            Matcher extractIdAndNote = NoteRegex.getNoteFormMatcher(eachContent);
            if (extractIdAndNote.matches()) {
                String ids = extractIdAndNote.group(1);
                String note = extractIdAndNote.group(2);
                String[] idArray = ids.split(",");

                findEachRegexAndSaveNotes(mockjangNoteContainer, ids, idArray, note);
                checkDissmatchIds(eachContent, idArray);
            } else {
                throw new NoteFormatException(DOMAIN_NOTE_FORMAT, content);
            }
        }
        return mockjangNoteContainer;
    }

    private void findEachRegexAndSaveNotes(MockjangNoteContainer mockjangNoteContainer, String ids,
                                           String[] idArray,
                                           String note) {
        for (NoteRegex regex : NoteRegex.values()) {
            Matcher regexMatcher = regex.getCompile().matcher(ids);
            saveIfRegexMatch(mockjangNoteContainer, idArray, note, regex, regexMatcher);
        }
    }

    private void saveIfRegexMatch(MockjangNoteContainer mockjangNoteContainer, String[] idArray,
                                  String note, NoteRegex regex, Matcher regexMatcher) {
        ArrayList<RecordAndCodeId> recordAndCodeIds = new ArrayList<>();
        if (regexMatcher.matches()) {
            for (int i = 0; i < idArray.length; i++) {
                recordAndCodeIds.add(new RecordAndCodeId(idArray[i], note));
                idArray[i] = null;
            }
            mockjangNoteContainer.putNotes(regex, recordAndCodeIds);
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
