package myproject.mockjang.api.service.mockjang.pen.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.mockjang.pen.PenRecord;

@Getter
@NoArgsConstructor
public class PenResponse {

    private Long id;
    private String codeId;
    private Long barnId;
    private List<Long> cows;
    private List<Long> records;

    @Builder
    private PenResponse(Long id, String codeId, Long barnId, List<Long> cows, List<Long> records) {
        this.id = id;
        this.codeId = codeId;
        this.barnId = barnId;
        this.cows = cows;
        this.records = records;
    }

    public static PenResponse of(Pen pen) {
        return PenResponse.builder()
                .id(pen.getId())
                .codeId(pen.getCodeId())
                .barnId(pen.getBarn().getId())
                .cows(pen.getCows()==null?List.of():pen.getCows().stream().mapToLong(Cow::getId).boxed().toList())
                .records(pen.getRecords()==null?List.of():pen.getRecords().stream().mapToLong(PenRecord::getId).boxed().toList())
                .build();
    }
}
