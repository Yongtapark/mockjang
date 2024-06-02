package myproject.mockjang.domain.note_parser;


import myproject.mockjang.domain.note_parser.mockjang.MockjangNoteContainer;

public interface NoteParser<T extends NoteContainer> {

  T extractAndSaveNotes(T noteContainer, String content);

}
