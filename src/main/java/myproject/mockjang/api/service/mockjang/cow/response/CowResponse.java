package myproject.mockjang.api.service.mockjang.cow.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;

@NoArgsConstructor
@Getter
public class CowResponse {

  private Long id;

  private String codeId;

  private LocalDateTime birthDate;

  private Gender gender;

  private CowStatus cowStatus;

  private Barn barn;

  private Pen pen;

  private Cow mom;

  private Cow dad;

  private final List<Cow> children = new ArrayList<>();

  private List<FeedConsumption> feedConsumptions = new ArrayList<>();

  private List<CowRecord> records = new ArrayList<>();

  private Integer unitPrice;

  @Builder
  private CowResponse(Long id, String codeId, LocalDateTime birthDate, Gender gender,
      CowStatus cowStatus, Barn barn, Pen pen, Cow mom, Cow dad,
      List<FeedConsumption> feedConsumptions, List<CowRecord> records, Integer unitPrice) {
    this.id = id;
    this.codeId = codeId;
    this.birthDate = birthDate;
    this.gender = gender;
    this.cowStatus = cowStatus;
    this.barn = barn;
    this.pen = pen;
    this.mom = mom;
    this.dad = dad;
    this.feedConsumptions = feedConsumptions;
    this.records = records;
    this.unitPrice = unitPrice;
  }

  public static CowResponse of(Cow cow) {
    return CowResponse.builder()
        .id(cow.getId())
        .codeId(cow.getCodeId())
        .birthDate(cow.getBirthDate())
        .gender(cow.getGender())
        .cowStatus(cow.getCowStatus())
        .barn(cow.getBarn())
        .pen(cow.getPen())
        .mom(cow.getMom())
        .dad(cow.getDad())
        .feedConsumptions(cow.getFeedConsumptions())
        .records(cow.getRecords())
        .unitPrice(cow.getUnitPrice())
        .build();
  }
}
