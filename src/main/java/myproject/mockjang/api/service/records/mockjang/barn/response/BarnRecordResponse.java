package myproject.mockjang.api.service.records.mockjang.barn.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecord;

@Getter
@NoArgsConstructor
public class BarnRecordResponse {

    private Long id;
    private Long barnId;
    private String memo;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private BarnRecordResponse(Long id, Long barnId, String memo, LocalDateTime date,
                               RecordType recordType) {
        this.id = id;
        this.barnId = barnId;
        this.memo = memo;
        this.date = date;
        this.recordType = recordType;
    }

    public static BarnRecordResponse of(BarnRecord record) {
        return BarnRecordResponse.builder()
                .id(record.getId())
                .barnId(record.getBarnId())
                .memo(record.getRecord())
                .date(record.getDate())
                .recordType(record.getRecordType())
                .build();
    }
}
