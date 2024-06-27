package myproject.mockjang.api.service.note_parser.mockjang.request;

import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class NoteParserCreateServiceRequest {

    private String context;
    private LocalDateTime date;
    private RecordType recordType;
    private HashMap<String, Integer> names;

    @Builder
    private NoteParserCreateServiceRequest(String context, LocalDateTime date, RecordType recordType,
                                           HashMap<String, Integer> names) {
        this.context = context;
        this.date = date;
        this.recordType = recordType;
        this.names = names;
    }

}
