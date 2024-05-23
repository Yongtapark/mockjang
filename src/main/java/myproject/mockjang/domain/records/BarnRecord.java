package myproject.mockjang.domain.records;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarnRecord extends Records{

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  @Builder
  public BarnRecord(Barn barn) {
    this.barn = barn;
  }

  public void writeRecord(String memo) {
    if(barn==null) {
      throw new RuntimeException("there is no barn or cow");
    }
    writeMemo(memo);
    barn.registerDailyRecord(this);
  }
}
