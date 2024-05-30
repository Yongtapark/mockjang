package myproject.mockjang.api.service.mockjang.pen.response;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.PenRecord;

@Getter
@NoArgsConstructor
public class PenResponse {
  private Long id;
  private String codeId;
  private Barn barn;
  private List<Cow> cows;
  private List<PenRecord> records;

  @Builder
  private PenResponse(Long id, String codeId, Barn barn, List<Cow> cows, List<PenRecord> records) {
    this.id = id;
    this.codeId = codeId;
    this.barn = barn;
    this.cows = cows;
    this.records = records;
  }

  public static PenResponse of(Pen pen) {
    return PenResponse.builder()
        .id(pen.getId())
        .codeId(pen.getCodeId())
        .barn(pen.getBarn())
        .cows(pen.getCows())
        .records(pen.getRecords())
        .build();
  }
}
