package myproject.mockjang.domain.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.*;
import static myproject.mockjang.exception.Exceptions.DOMAIN_BARN_ALREADY_EXIST;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.creater.YongTaPark;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.CowRecord;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import myproject.mockjang.exception.common.UpperGroupAlreadyExistException;
import myproject.mockjang.exception.cow.CowStatusException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE cow SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Cow extends AbstractAuditable<YongTaPark, Long> implements Mockjang {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codeId;

  private LocalDateTime birthDate;

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
  private final List<Cow> children = new ArrayList<>();

  @OneToMany(mappedBy = "cow")
  private List<FeedConsumption> feedConsumptions = new ArrayList<>();

  @OneToMany(mappedBy = "cow")
  private List<CowRecord> records = new ArrayList<>();

  private Integer unitPrice;

  private boolean deleted = false;

  @Builder
  private Cow(String codeId, LocalDateTime birthDate, Gender gender, Barn barn, Pen pen, CowStatus cowStatus,
              List<FeedConsumption> feedConsumptions, List<CowRecord> records, Integer unitPrice) {

    this.codeId = codeId;
    this.birthDate = birthDate;
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

  public static Cow createCow(String cowId,Gender gender,CowStatus cowStatus,
      LocalDateTime birthDate) {
    return Cow.builder()
        .codeId(cowId)
        .birthDate(birthDate)
        .gender(gender)
        .cowStatus(cowStatus)
        .build();
  }

  public void registerBarn(Barn barn) {
    if (this.barn != null) {
      throw new UpperGroupAlreadyExistException(barn,DOMAIN_BARN_ALREADY_EXIST);
    }
    this.barn=barn;
  }

  private void changeBarn(Barn barn) {
    this.barn=null;
    this.barn =barn;
  }

  @Override
  public void registerUpperGroup(Mockjang upperGroup) {
    if(upperGroup instanceof Pen pen){
      if (this.pen != null) {
        throw new UpperGroupAlreadyExistException(pen,DOMAIN_BARN_ALREADY_EXIST);
      }
      this.pen = pen;
      pen.addCow(this);
    }
  }

  public void changePen(Pen pen) {
    this.pen.deleteCow(this);
    this.pen=null;
    changeBarn(pen.getBarn());
    registerUpperGroup(pen);
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
      throw new CowStatusException(DOMAIN_ONLY_SLAUGHTERED_ERROR);
    }
    this.unitPrice = unitPrice;
  }

  @Override
  public Mockjang getUpperGroup() {
    if(pen==null){
      throw new ThereIsNoGroupException(COMMON_NO_UPPER_GROUP,this);
    }
    return pen;
  }

  @Override
  public void removeOneOfUnderGroups(Mockjang mockjang) {
    throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP,this);
  }

  public boolean getDeleted() {
    return deleted;
  }
}
