package myproject.mockjang.api.service.records.mockjang.barn.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class BarnRecordUpdateServiceRequest {
    private Long id;
    private String codeId;
    private String record;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private BarnRecordUpdateServiceRequest(Long id, String codeId, LocalDateTime date, String record, RecordType recordType) {
        this.id = id;
        this.codeId = codeId;
        this.date = date;
        this.record = record;
        this.recordType = recordType;
    }
}
