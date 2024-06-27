package myproject.mockjang.api.controller.note_parser.simple.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleNoteParserCreateRequest {

    @NotBlank(message = "{exception.context.blank}")
    private String context;
    @NotNull(message = "{exception.recordType.null}")
    private RecordType recordType;
    @NotNull(message = "{exception.date.null}")
    private LocalDateTime date;

    @Builder
    public SimpleNoteParserCreateRequest(String context, RecordType recordType, LocalDateTime date) {
        this.context = context;
        this.recordType = recordType;
        this.date = date;
    }

    public SimpleNoteParserCreateServiceRequest toServiceRequest(HashMap<String, Integer> names) {
        return SimpleNoteParserCreateServiceRequest.builder().date(date).recordType(recordType).context(context)
                .names(names).build();
    }
}
