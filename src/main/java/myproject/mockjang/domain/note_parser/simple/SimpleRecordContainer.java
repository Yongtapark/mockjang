package myproject.mockjang.domain.note_parser.simple;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.note_parser.NoteContainer;
import myproject.mockjang.domain.note_parser.mockjang.RecordAndCodeId;
import myproject.mockjang.domain.records.RecordType;

@NoArgsConstructor
@Getter
public class SimpleRecordContainer implements NoteContainer {

    private List<RecordAndCodeId> notes;
    private RecordType recordType;
    private LocalDateTime date;

    public void registerNoteAndIds(List<RecordAndCodeId> recordAndCodeIds) {
        this.notes = recordAndCodeIds;
    }

    public List<RecordAndCodeId> getNotes() {
        return List.copyOf(notes);
    }

    @Builder
    private SimpleRecordContainer(List<RecordAndCodeId> notes, RecordType recordType, LocalDateTime date) {
        this.notes = notes;
        this.recordType = recordType;
        this.date = date;
    }
}
