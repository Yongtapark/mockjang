package myproject.mockjang.api.service.mockjang.cow.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;

@NoArgsConstructor
@Getter
public class CowResponse {

    private Long id;

    private String codeId;

    private LocalDateTime birthDate;

    private Gender gender;

    private CowStatus cowStatus;

    private Long barnId;

    private Long penId;

    private Long momId;

    private Long dadId;

    private List<Long> children = new ArrayList<>();

    private List<Long> feedConsumptions = new ArrayList<>();

    private List<Long> records = new ArrayList<>();

    private Integer unitPrice;

    @Builder
    private CowResponse(Long id, String codeId, LocalDateTime birthDate, Gender gender,
                        CowStatus cowStatus, Long barnId, Long penId, Long momId, Long dadId,
                        List<Long> children, List<Long> feedConsumptions, List<Long> records, Integer unitPrice) {
        this.id = id;
        this.codeId = codeId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.cowStatus = cowStatus;
        this.barnId = barnId;
        this.penId = penId;
        this.momId = momId;
        this.dadId = dadId;
        this.children = children;
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
                .barnId(cow.getBarn() == null ? null : cow.getBarn().getId())
                .penId(cow.getPen() == null ? null : cow.getPen().getId())
                .momId(cow.getMom() == null ? null : cow.getMom().getId())
                .dadId(cow.getDad() == null ? null : cow.getDad().getId())
                .feedConsumptions(cow.getFeedConsumptions() == null ? List.of()
                        : cow.getFeedConsumptions().stream().mapToLong(FeedConsumption::getId).boxed().toList())
                .records(cow.getRecords() == null ? List.of()
                        : cow.getRecords().stream().mapToLong(CowRecord::getId).boxed().toList())
                .children(
                        cow.getChildren() == null ? List.of() : cow.getChildren().stream().mapToLong(Cow::getId).boxed()
                                .toList())
                .unitPrice(cow.getUnitPrice())
                .build();
    }
}
