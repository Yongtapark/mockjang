package myproject.mockjang.domain.records;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BarnRecord extends Records{

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  @Builder
  private BarnRecord(Barn barn) {
    this.barn = barn;
  }

  public static BarnRecord creatRecord(Barn barn) {
    return BarnRecord.builder().barn(barn).build();
  }

  public void writeNote(String memo) {
    if(barn==null) {
      throw new RuntimeException("there is no barn or cow");
    }
    writeMemo(memo);
    barn.registerDailyRecord(this);
  }
}
