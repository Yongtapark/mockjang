package myproject.mockjang.api.service.records.simple_record.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleRecordSearchServiceRequest {
    private Long id;

    private String codeId;

    private RecordType recordType;

    private LocalDateTime date;

    private String record;

    @Builder
    private SimpleRecordSearchServiceRequest(String codeId, LocalDateTime date, Long id, String record,
                                            RecordType recordType) {
        this.codeId = codeId;
        this.date = date;
        this.id = id;
        this.record = record;
        this.recordType = recordType;
    }
}
