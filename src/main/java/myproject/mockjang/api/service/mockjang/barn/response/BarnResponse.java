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

    private final List<Pen> pens;

    private final List<BarnRecord> records;

    private final List<Barn> barns;

    @Builder
    public BarnResponse(Long id, String codeId, List<Pen> pens, List<BarnRecord> records,
                        List<Barn> barns) {
        this.id = id;
        this.codeId = codeId;
        this.pens = pens;
        this.records = records;
        this.barns = barns;
    }

    public static BarnResponse of(Barn barn) {
        return BarnResponse.builder()
                .id(barn.getId())
                .codeId(barn.getCodeId())
                .pens(barn.getPens())
                .records(barn.getRecords())
                .build();
    }

    public static BarnResponse of(List<Barn> barns) {
        return BarnResponse.builder().barns(barns).build();
    }
}
