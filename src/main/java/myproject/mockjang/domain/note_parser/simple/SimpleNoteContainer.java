package myproject.mockjang.domain.note_parser.simple;

import java.util.List;
import myproject.mockjang.domain.note_parser.NoteContainer;
import myproject.mockjang.domain.note_parser.mockjang.NoteAndCodeId;

public class SimpleNoteContainer implements NoteContainer {

  private List<NoteAndCodeId> notes;

  public void registerNoteAndIds(List<NoteAndCodeId> noteAndCodeIds) {
    this.notes = noteAndCodeIds;
  }

  public List<NoteAndCodeId> getImmutable() {
    return List.copyOf(notes);
  }
}
