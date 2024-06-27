package myproject.mockjang.api.controller.records.mockjang.barn.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.barn.request.BarnRecordSearchServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class BarnRecordSearchRequest {
    private String codeId;
    private String record;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private BarnRecordSearchRequest(String codeId, LocalDateTime date, String record, RecordType recordType) {
        this.codeId = codeId;
        this.date = date;
        this.record = record;
        this.recordType = recordType;
    }

    public BarnRecordSearchServiceRequest toServiceRequest() {
        return BarnRecordSearchServiceRequest.builder()
                .codeId(codeId)
                .recordType(recordType)
                .record(record)
                .date(date)
                .build();
    }
}
