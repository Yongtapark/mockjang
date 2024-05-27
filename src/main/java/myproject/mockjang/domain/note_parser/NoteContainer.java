package myproject.mockjang.domain.note_parser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class NoteContainer {
    HashMap<NoteRegex, NoteAndIdList> container;

    public NoteContainer() {
        this.container = new HashMap<>();
    }

    public void putNotes(NoteRegex regex, NoteAndIdList noteAndIdList){
        container.put(regex, noteAndIdList);
    }

    public NoteAndIdList getNotes(NoteRegex regex){
       return container.get(regex);
    }

    public Collection<NoteAndIdList> values(){
        return container.values();
    }

    public Map<NoteRegex, NoteAndIdList> getImmutableMap() {
        Map<NoteRegex, NoteAndIdList> immutableMap = new HashMap<>();
        for (Map.Entry<NoteRegex, NoteAndIdList> entry : container.entrySet()) {
            immutableMap.put(entry.getKey(), entry.getValue().getImmutableNoteAndIdList());
        }
        return Collections.unmodifiableMap(immutableMap);
    }
}
