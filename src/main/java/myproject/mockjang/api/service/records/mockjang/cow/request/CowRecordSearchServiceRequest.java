package myproject.mockjang.api.service.records.mockjang.cow.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordSearchServiceRequest {
    private String codeId;
    private String record;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private CowRecordSearchServiceRequest(String codeId, LocalDateTime date, String record, RecordType recordType) {
        this.codeId = codeId;
        this.date = date;
        this.record = record;
        this.recordType = recordType;
    }
}
