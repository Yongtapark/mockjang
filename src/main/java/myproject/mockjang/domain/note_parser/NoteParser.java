package myproject.mockjang.domain.note_parser;


public interface NoteParser<T extends NoteContainer> {

  T extractAndSaveNotes(T noteContainer, String content);

}
