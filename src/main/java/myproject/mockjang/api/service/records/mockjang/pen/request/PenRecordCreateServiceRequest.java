package myproject.mockjang.api.service.records.mockjang.pen.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class PenRecordCreateServiceRequest {
    private String penCodeId;
    private String memo;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private PenRecordCreateServiceRequest(LocalDateTime date, String memo, String penCodeId, RecordType recordType) {
        this.date = date;
        this.memo = memo;
        this.penCodeId = penCodeId;
        this.recordType = recordType;
    }
}
