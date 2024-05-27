package myproject.mockjang.domain.note_parser;

public interface NoteParser {

  NoteContainer extractAndSaveNotes(NoteContainer noteContainer, String content);

}
