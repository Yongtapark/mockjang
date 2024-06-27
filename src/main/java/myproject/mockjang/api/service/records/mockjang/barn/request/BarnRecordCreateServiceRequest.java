package myproject.mockjang.api.service.records.mockjang.barn.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class BarnRecordCreateServiceRequest {

    private String barnCodeId;
    private String memo;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private BarnRecordCreateServiceRequest(String barnCodeId, String memo, LocalDateTime date,
                                           RecordType recordType) {
        this.barnCodeId = barnCodeId;
        this.memo = memo;
        this.date = date;
        this.recordType = recordType;
    }
}
