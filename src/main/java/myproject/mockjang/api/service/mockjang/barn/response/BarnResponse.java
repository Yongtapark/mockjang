package myproject.mockjang.api.service.mockjang.barn.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecord;

@Getter
public class BarnResponse {

    private final Long id;

    private final String codeId;

    private final List<Long> pens;

    private final List<Long> records;

    @Builder
    public BarnResponse(Long id, String codeId, List<Long> pens, List<Long> records) {
        this.id = id;
        this.codeId = codeId;
        this.pens = pens;
        this.records = records;
    }

    public static BarnResponse of(Barn barn) {
        return BarnResponse.builder()
                .id(barn.getId())
                .codeId(barn.getCodeId())
                .pens(barn.getPens()==null?List.of():barn.getPens().stream().mapToLong(Pen::getId).boxed().toList())
                .records(barn.getRecords()==null?List.of():barn.getRecords().stream().mapToLong(BarnRecord::getId).boxed().toList())
                .build();
    }
}
