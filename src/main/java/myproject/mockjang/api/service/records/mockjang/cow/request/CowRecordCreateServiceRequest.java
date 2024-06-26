package myproject.mockjang.api.service.records.mockjang.cow.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordCreateServiceRequest {

    private String cowCodeId;
    private String memo;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private CowRecordCreateServiceRequest(String cowCodeId, String memo, LocalDateTime date,
                                          RecordType recordType) {
        this.cowCodeId = cowCodeId;
        this.memo = memo;
        this.date = date;
        this.recordType = recordType;
    }
}
