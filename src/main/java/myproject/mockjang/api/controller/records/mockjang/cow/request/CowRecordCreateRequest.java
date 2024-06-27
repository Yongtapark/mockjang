package myproject.mockjang.api.controller.records.mockjang.cow.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class CowRecordCreateRequest {

    @NotBlank(message = "{exception.cow.codeId.blank}")
    private String codeId;
    @NotNull(message = "{exception.recordType.null}")
    private RecordType recordType;
    @NotNull(message = "{exception.date.null}")
    private LocalDateTime date;
    @NotBlank(message = "{exception.record.blank}")
    private String record;

    @Builder
    private CowRecordCreateRequest(String codeId, RecordType recordType, LocalDateTime date,
                                   String record) {
        this.codeId = codeId;
        this.recordType = recordType;
        this.date = date;
        this.record = record;
    }

    public CowRecordCreateServiceRequest toServiceRequest() {
        return CowRecordCreateServiceRequest.builder()
                .cowCodeId(codeId)
                .recordType(recordType)
                .date(date)
                .memo(record)
                .build();
    }
}
