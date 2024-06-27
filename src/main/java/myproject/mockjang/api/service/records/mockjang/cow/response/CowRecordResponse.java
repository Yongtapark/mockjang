package myproject.mockjang.api.service.records.mockjang.cow.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;

@Getter
@NoArgsConstructor
public class CowRecordResponse {

    private Long id;
    private Long cowId;
    private String memo;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private CowRecordResponse(Long id, Long cowId, String memo, LocalDateTime date,
                              RecordType recordType) {
        this.id = id;
        this.cowId = cowId;
        this.memo = memo;
        this.date = date;
        this.recordType = recordType;
    }

    public static CowRecordResponse of(CowRecord record) {
        return CowRecordResponse.builder()
                .id(record.getId())
                .cowId(record.getCowId())
                .memo(record.getRecord())
                .date(record.getDate())
                .recordType(record.getRecordType())
                .build();
    }
}
