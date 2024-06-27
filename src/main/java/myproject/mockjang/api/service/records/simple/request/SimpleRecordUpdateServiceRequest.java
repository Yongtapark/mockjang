package myproject.mockjang.api.service.records.simple.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class SimpleRecordUpdateServiceRequest {
    private Long id;
    private String codeId;
    private RecordType recordType;
    private LocalDateTime date;
    private String record;

    @Builder
    private SimpleRecordUpdateServiceRequest(Long id, String codeId, RecordType recordType,
                                             LocalDateTime date, String record) {
        this.id = id;
        this.codeId = codeId;
        this.recordType = recordType;
        this.date = date;
        this.record = record;
    }
}
