package myproject.mockjang.domain.records;

import static myproject.mockjang.exception.Exceptions.DOMAIN_NO_COW_OR_PEN_OR_BARN;

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
import myproject.mockjang.exception.record.RecordException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE cow_record SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class CowRecord extends Records {

  @ManyToOne(fetch = FetchType.LAZY)
  private Cow cow;

  @ManyToOne(fetch = FetchType.LAZY)
  private Pen pen;

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  private boolean deleted = false;

  @Builder
  private CowRecord(Cow cow, Pen pen, Barn barn) {
    this.cow = cow;
    this.pen = pen;
    this.barn = barn;
  }

  public static CowRecord createRecord(Cow cow) {
    return CowRecord.builder()
        .cow(cow)
        .pen(cow.getPen())
        .barn(cow.getBarn())
        .build();
  }

  public void writeNote(String memo) {
    if (barn == null || cow == null || pen == null) {
      throw new RecordException(DOMAIN_NO_COW_OR_PEN_OR_BARN);
    }
    writeMemo(memo);
    cow.registerDailyRecord(this);
  }
}
