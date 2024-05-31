package myproject.mockjang.domain.mockjang.pen;

import static myproject.mockjang.exception.Exceptions.COMMON_NO_UNDER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UPPER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_ALREADY_EXIST;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.creater.YongTaPark;
import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.records.PenRecord;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import myproject.mockjang.exception.common.UpperGroupAlreadyExistException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE pen SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Pen extends AbstractAuditable<YongTaPark, Long> implements Mockjang {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codeId;

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  @OneToMany(mappedBy = "pen")
  private List<Cow> cows = new ArrayList<>();

  @OneToMany(mappedBy = "pen")
  private List<PenRecord> records = new ArrayList<>();

  private boolean deleted = false;

  @Builder
  private Pen(String codeId, Barn barn, List<Cow> cows, List<PenRecord> records) {
    this.codeId = codeId;
    this.barn = barn;
    if (cows != null) {
      this.cows = cows;
    }
    if (records != null) {
      this.records = records;
    }
  }

  public static Pen createPen(String penCode) {
    return Pen.builder()
        .codeId(penCode)
        .build();
  }

  @Override
  public void registerUpperGroup(Mockjang mockjang) {
    if (mockjang instanceof Barn barn) {
      if (this.barn != null) {
        throw new UpperGroupAlreadyExistException(barn, COMMON_ALREADY_EXIST);
      }
      barn.addPen(this);
      this.barn = barn;
    }
  }

  @Override
  public void changeUpperGroup(Mockjang mockjang) {
    if (mockjang instanceof Barn barn) {
      this.barn.deletePen(this);
      this.barn = null;
      registerUpperGroup(barn);
    }
  }

  public void addCow(Cow cow) {
    cows.add(cow);
  }

  public void addCow(List<Cow> cows) {
    for (Cow cow : cows) {
      addCow(cow);
    }
  }

  public void registerDailyRecord(PenRecord record) {
    records.add(record);
  }

  public void deleteCow(Cow cow) {
    cows.remove(cow);
  }

  @Override
  public Mockjang getUpperGroup() {
    if (barn == null) {
      throw new ThereIsNoGroupException(COMMON_NO_UPPER_GROUP, this);
    }
    return barn;
  }

  @Override
  public void removeOneOfUnderGroups(Mockjang mockjang) {
    if (cows.isEmpty()) {
      throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP, this);
    }
    if (!cows.remove(mockjang)) {
      throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP, this);
    }
  }
}
