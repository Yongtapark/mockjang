package myproject.mockjang.domain.note_parser.mockjang;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import myproject.mockjang.domain.note_parser.NoteContainer;
import myproject.mockjang.domain.note_parser.NoteRegex;

public class MockjangNoteContainer implements NoteContainer {

    private final HashMap<NoteRegex, List<RecordAndCodeId>> container;

    public MockjangNoteContainer() {
        this.container = new HashMap<>();
    }

    public void putNotes(NoteRegex regex, List<RecordAndCodeId> recordAndCodeIds) {
        if (container.containsKey(regex)) {
            List<RecordAndCodeId> currentRecordAndCodeIds = container.get(regex);
            currentRecordAndCodeIds.addAll(recordAndCodeIds);
            return;
        }
        container.put(regex, recordAndCodeIds);
    }

    public List<RecordAndCodeId> getNotes(NoteRegex regex) {
        return container.get(regex);
    }

    public Collection<List<RecordAndCodeId>> values() {
        return container.values();
    }

    public Map<NoteRegex, List<RecordAndCodeId>> getImmutableMap() {
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = new HashMap<>();
        for (Map.Entry<NoteRegex, List<RecordAndCodeId>> entry : container.entrySet()) {
            immutableMap.put(entry.getKey(), List.copyOf(entry.getValue()));
        }
        return Map.copyOf(immutableMap);
    }
}
