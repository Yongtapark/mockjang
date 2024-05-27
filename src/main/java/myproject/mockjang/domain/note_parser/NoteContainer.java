package myproject.mockjang.domain.note_parser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoteContainer {

  HashMap<NoteRegex, List<NoteAndId>> container;

  public NoteContainer() {
    this.container = new HashMap<>();
  }

  public void putNotes(NoteRegex regex, List<NoteAndId> noteAndIds) {
    if (container.containsKey(regex)) {
      List<NoteAndId> currentHashMap = container.get(regex);
      currentHashMap.addAll(noteAndIds);
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
    Map<NoteRegex, List<NoteAndId>> immutableMap = new HashMap<>(container);
    return Collections.unmodifiableMap(immutableMap);
  }
}
