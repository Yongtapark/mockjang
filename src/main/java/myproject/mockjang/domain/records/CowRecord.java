package myproject.mockjang.domain.records;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.pen.Pen;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CowRecord extends Records{

  @ManyToOne(fetch = FetchType.LAZY)
  private Cow cow;

  @ManyToOne(fetch = FetchType.LAZY)
  private Pen pen;

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  @Builder
  public CowRecord(Cow cow, Pen pen, Barn barn) {
    this.cow = cow;
    this.pen = pen;
    this.barn = barn;
  }

  public void writeRecord(String memo) {
    if(barn==null || cow==null || pen==null) {
      throw new RuntimeException("there is no barn or cow");
    }
    writeMemo(memo);
    cow.registerDailyRecord(this);
  }
}
