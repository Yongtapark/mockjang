package myproject.mockjang.domain.note_parser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteContainer {

  private final HashMap<NoteRegex, List<NoteAndId>> container;

  public NoteContainer() {
    this.container = new HashMap<>();
  }

  public void putNotes(NoteRegex regex, List<NoteAndId> noteAndIds) {
    if (container.containsKey(regex)) {
      List<NoteAndId> currentNoteAndIds = container.get(regex);
      currentNoteAndIds.addAll(noteAndIds);
      return;
    }
    container.put(regex, noteAndIds);
  }

  public List<NoteAndId> getNotes(NoteRegex regex) {
    return container.get(regex);
  }

  public Collection<List<NoteAndId>> values() {
    return container.values();
  }

  public Map<NoteRegex, List<NoteAndId>> getImmutableMap() {
    Map<NoteRegex, List<NoteAndId>> immutableMap = new HashMap<>();
    for (Map.Entry<NoteRegex, List<NoteAndId>> entry : container.entrySet()) {
      immutableMap.put(entry.getKey(), List.copyOf(entry.getValue()));
    }
    return Map.copyOf(immutableMap);
  }
}
