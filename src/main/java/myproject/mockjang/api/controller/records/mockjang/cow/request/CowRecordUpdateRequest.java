package myproject.mockjang.api.controller.records.mockjang.cow.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordUpdateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordUpdateRequest {
    @NotNull(message = "{exception.id.null}")
    private Long id;
    private String codeId;
    private String record;
    private LocalDateTime date;
    private RecordType recordType;


    @Builder
    private CowRecordUpdateRequest(Long id, String codeId, LocalDateTime date, String record, RecordType recordType) {
        this.id = id;
        this.codeId = codeId;
        this.date = date;
        this.record = record;
        this.recordType = recordType;
    }

    public CowRecordUpdateServiceRequest toServiceRequest() {
        return CowRecordUpdateServiceRequest.builder()
                .id(id)
                .codeId(codeId)
                .recordType(recordType)
                .record(record)
                .date(date)
                .build();
    }
}
