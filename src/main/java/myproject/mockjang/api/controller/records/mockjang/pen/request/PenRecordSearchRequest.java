package myproject.mockjang.api.controller.records.mockjang.pen.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.pen.request.PenRecordSearchServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class PenRecordSearchRequest {

    private String codeId;
    private String record;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private PenRecordSearchRequest(String codeId, RecordType recordType, LocalDateTime date,
                                   String record) {
        this.codeId = codeId;
        this.recordType = recordType;
        this.date = date;
        this.record = record;
    }

    public PenRecordSearchServiceRequest toServiceRequest() {
        return PenRecordSearchServiceRequest.builder()
                .codeId(codeId)
                .recordType(recordType)
                .record(record)
                .date(date)
                .build();
    }
}
