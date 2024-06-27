package myproject.mockjang.api.service.records.mockjang.pen.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.pen.PenRecord;

@Getter
@NoArgsConstructor
public class PenRecordResponse {

    private Long id;
    private Long penId;
    private String memo;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private PenRecordResponse(Long id, Long penId, String memo, LocalDateTime date,
                              RecordType recordType) {
        this.id = id;
        this.penId = penId;
        this.memo = memo;
        this.date = date;
        this.recordType = recordType;
    }

    public static PenRecordResponse of(PenRecord record) {
        return PenRecordResponse.builder()
                .id(record.getId())
                .memo(record.getRecord())
                .date(record.getDate())
                .penId(record.getPenId())
                .recordType(record.getRecordType())
                .build();
    }
}
