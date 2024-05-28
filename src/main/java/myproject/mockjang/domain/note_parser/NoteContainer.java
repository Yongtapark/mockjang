package myproject.mockjang.domain.note_parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteContainer {

  private final HashMap<NoteRegex, List<NoteAndCodeId>> container;

  public NoteContainer() {
    this.container = new HashMap<>();
  }

  public void putNotes(NoteRegex regex, List<NoteAndCodeId> noteAndCodeIds) {
    if (container.containsKey(regex)) {
      List<NoteAndCodeId> currentNoteAndCodeIds = container.get(regex);
      currentNoteAndCodeIds.addAll(noteAndCodeIds);
      return;
    }
    container.put(regex, noteAndCodeIds);
  }

  public List<NoteAndCodeId> getNotes(NoteRegex regex) {
    return container.get(regex);
  }

  public Collection<List<NoteAndCodeId>> values() {
    return container.values();
  }

  public Map<NoteRegex, List<NoteAndCodeId>> getImmutableMap() {
    Map<NoteRegex, List<NoteAndCodeId>> immutableMap = new HashMap<>();
    for (Map.Entry<NoteRegex, List<NoteAndCodeId>> entry : container.entrySet()) {
      immutableMap.put(entry.getKey(), List.copyOf(entry.getValue()));
    }
    return Map.copyOf(immutableMap);
  }
}
