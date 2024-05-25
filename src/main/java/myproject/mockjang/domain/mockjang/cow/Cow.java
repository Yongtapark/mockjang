package myproject.mockjang.domain.mockjang.cow;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.CowRecord;
import myproject.mockjang.exception.cow.CowStatusException;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cow extends AbstractAuditable<Cow, Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String cowId;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private CowStatus cowStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  @ManyToOne(fetch = FetchType.LAZY)
  private Pen pen;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mom_id")
  private Cow mom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dad_id")
  private Cow dad;

  @OneToMany(mappedBy = "mom")
  private List<Cow> children = new ArrayList<>();

  @OneToMany(mappedBy = "cow")
  private List<FeedConsumption> feedConsumptions = new ArrayList<>();

  @OneToMany(mappedBy = "cow")
  private List<CowRecord> records = new ArrayList<>();

  private Integer unitPrice;

  @Builder
  public Cow(String cowId, Gender gender, Barn barn, Pen pen, CowStatus cowStatus,
      List<FeedConsumption> feedConsumptions, List<CowRecord> records, Integer unitPrice) {

    this.cowId = cowId;
    this.gender = gender;
    this.barn = barn;
    this.pen = pen;
    this.cowStatus = cowStatus;
    this.unitPrice = unitPrice;
    if (feedConsumptions != null) {
      this.feedConsumptions = feedConsumptions;
    }
    if (records != null) {
      this.records = records;
    }
  }

  public void registerPen(Pen pen) {
    this.pen = pen;
  }

  public void registerAllChildren(List<Cow> children) {
    for (Cow child : children) {
      child.registerParent(this);
    }
  }

  public void registerParent(Cow parent) {
    if (parent.gender.equals(Gender.FEMALE)) {
      this.mom = parent;
    } else {
      this.dad = parent;
    }
    parent.children.add(this);
  }

  public void registerDailyRecord(CowRecord record) {
    records.add(record);
  }

  public void registerFeedConsumptions(FeedConsumption feedConsumption) {
    feedConsumptions.add(feedConsumption);
  }

  public void changeSlaughterStatus(CowStatus cowStatus) {
    this.cowStatus = cowStatus;
  }

  public void changeCowStatus(CowStatus cowStatus) {
    this.cowStatus = cowStatus;
  }

  public void registerUnitPrice(Integer unitPrice) {
    if (getCowStatus() == null || !getCowStatus().equals(CowStatus.SLAUGHTERED)) {
      throw new CowStatusException(Exceptions.DOMAIN_ONLY_SLAUGHTERED_ERROR);
    }
    this.unitPrice = unitPrice;
  }
}
