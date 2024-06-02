package myproject.mockjang.api.service.records.simple.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.simple.SimpleRecord;

@Getter
@NoArgsConstructor
public class SimpleRecordResponse {
    private Long id;

    private String codeId;

    private RecordType recordType;

    private LocalDateTime date;

    private String record;

    @Builder
    private SimpleRecordResponse(String codeId, LocalDateTime date, Long id, String record, RecordType recordType) {
        this.codeId = codeId;
        this.date = date;
        this.id = id;
        this.record = record;
        this.recordType = recordType;
    }

    public static SimpleRecordResponse of(SimpleRecord simpleRecord) {
        return SimpleRecordResponse.builder()
                .id(simpleRecord.getId())
                .codeId(simpleRecord.getCodeId())
                .recordType(simpleRecord.getRecordType())
                .date(simpleRecord.getDate())
                .record(simpleRecord.getRecord())
                .build();
    }
}
