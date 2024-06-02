package myproject.mockjang.domain.mockjang.barn;

import static myproject.mockjang.exception.Exceptions.COMMON_NO_UNDER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UPPER_GROUP;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecord;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE barn SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@EntityListeners(AuditingEntityListener.class)
public class Barn implements Mockjang {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codeId;

  @OneToMany(mappedBy = "barn")
  private final List<Pen> pens = new ArrayList<>();

  @OneToMany(mappedBy = "barn")
  private final List<BarnRecord> records = new ArrayList<>();

  private boolean deleted = false;

  @Builder
  private Barn(String codeId) {
    this.codeId = codeId;
  }

  public static Barn createBarn(String codeId) {
    return Barn.builder().codeId(codeId).build();
  }

  public void addPen(Pen pen) {
    pens.add(pen);
  }

  public void addPen(List<Pen> pens) {
    for (Pen pen : pens) {
      addPen(pen);
    }
  }

  public void deletePen(Pen pen) {
    pens.remove(pen);
  }

  public void registerDailyRecord(BarnRecord record) {
    records.add(record);
  }

  @Override
  public Mockjang getUpperGroup() {
    throw new ThereIsNoGroupException(Exceptions.COMMON_NO_UPPER_GROUP, this);
  }

  @Override
  public void registerUpperGroup(Mockjang upperGroup) {
    throw new ThereIsNoGroupException(Exceptions.COMMON_NO_UPPER_GROUP, this);
  }

  @Override
  public void changeUpperGroup(Mockjang mockjang) {
    throw new ThereIsNoGroupException(COMMON_NO_UPPER_GROUP, this);
  }

  @Override
  public void removeOneOfUnderGroups(Mockjang mockjang) {
    if (pens.isEmpty()) {
      throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP, this);
    }
    if (!pens.remove(mockjang)) {
      throw new ThereIsNoGroupException(COMMON_NO_UNDER_GROUP, this);
    }
  }
}
