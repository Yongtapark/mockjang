package myproject.mockjang.api.controller.records.mockjang.barn.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.barn.request.BarnRecordUpdateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class BarnRecordUpdateRequest {
    @NotNull(message = "{exception.id.null}")
    private Long id;
    private String codeId;
    private String record;
    private LocalDateTime date;
    private RecordType recordType;

    @Builder
    private BarnRecordUpdateRequest(Long id, String codeId, LocalDateTime date, String record, RecordType recordType) {
        this.id = id;
        this.codeId = codeId;
        this.date = date;
        this.record = record;
        this.recordType = recordType;
    }

    public BarnRecordUpdateServiceRequest toServiceRequest() {
        return BarnRecordUpdateServiceRequest.builder()
                .id(id)
                .codeId(codeId)
                .recordType(recordType)
                .record(record)
                .date(date)
                .build();
    }
}
