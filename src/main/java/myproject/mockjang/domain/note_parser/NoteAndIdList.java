package myproject.mockjang.domain.note_parser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class NoteAndIdList {
    private List<NoteAndId> noteAndIds;

  public NoteAndIdList(List<NoteAndId> noteAndIds) {
    this.noteAndIds = noteAndIds;
  }

  public void reset(List<NoteAndId> noteAndIds){
      this.noteAndIds = noteAndIds;
  }
  public NoteAndId get(int index){
        return noteAndIds.get(index);
    }
    public void add(NoteAndId noteAndId){
        noteAndIds.add(noteAndId);
    }

    public void addAll(List<NoteAndId> noteAndIds) {
      this.noteAndIds.addAll(noteAndIds);
    }

    public List<NoteAndId> getNoteAndIds() {
        //return List.copyOf(noteAndIds);
        return noteAndIds;
    }

    @JsonIgnore
    public NoteAndIdList getImmutableNoteAndIdList(){
        return new NoteAndIdList(noteAndIds);
    }

    public void removeAll() {
        this.noteAndIds.iterator().forEachRemaining(noteAndIds::remove);
    }

    public Iterator<NoteAndId> iterator() {
        return noteAndIds.iterator();
    }
}
