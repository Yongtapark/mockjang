package myproject.mockjang.domain.note_parser;

import org.springframework.stereotype.Component;

public interface NoteParser {

  NoteContainer extractAndSaveNotes(NoteContainer noteContainer, String content);

}
