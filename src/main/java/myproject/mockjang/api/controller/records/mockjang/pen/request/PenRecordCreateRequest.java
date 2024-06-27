package myproject.mockjang.api.controller.records.mockjang.pen.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.pen.request.PenRecordCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class PenRecordCreateRequest {

    @NotBlank(message = "{exception.pen.codeId.blank}")
    private String codeId;
    @NotNull(message = "{exception.recordType.null}")
    private RecordType recordType;
    @NotNull(message = "{exception.date.null}")
    private LocalDateTime date;
    @NotBlank(message = "{exception.record.blank}")
    private String record;

    @Builder
    private PenRecordCreateRequest(String codeId, RecordType recordType, LocalDateTime date,
                                   String record) {
        this.codeId = codeId;
        this.recordType = recordType;
        this.date = date;
        this.record = record;
    }

    public PenRecordCreateServiceRequest toServiceRequest() {
        return PenRecordCreateServiceRequest.builder()
                .penCodeId(codeId)
                .recordType(recordType)
                .date(date)
                .memo(record)
                .build();
    }
}
