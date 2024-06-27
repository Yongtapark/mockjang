package myproject.mockjang.api.controller.records.mockjang.barn.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.barn.request.BarnRecordCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;

@Getter
@NoArgsConstructor
public class BarnRecordCreateRequest {

    @NotBlank(message = "{exception.cow.codeId.blank}")
    private String codeId;
    @NotNull(message = "{exception.recordType.null}")
    private RecordType recordType;
    @NotNull(message = "{exception.date.null}")
    private LocalDateTime date;
    @NotBlank(message = "{exception.record.blank}")
    private String record;

    @Builder
    private BarnRecordCreateRequest(String codeId, RecordType recordType, LocalDateTime date,
                                    String record) {
        this.codeId = codeId;
        this.recordType = recordType;
        this.date = date;
        this.record = record;
    }

    public BarnRecordCreateServiceRequest toServiceRequest() {
        return BarnRecordCreateServiceRequest.builder()
                .barnCodeId(codeId)
                .recordType(recordType)
                .date(date)
                .memo(record)
                .build();
    }
}
