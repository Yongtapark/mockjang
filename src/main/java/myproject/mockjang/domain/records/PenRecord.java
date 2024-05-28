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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE pen_record SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PenRecord extends Records{

  @ManyToOne(fetch = FetchType.LAZY)
  private Pen pen;

  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;

  private boolean deleted = false;

  @Builder
  private PenRecord(Barn barn,Pen pen) {
    this.pen = pen;
    this.barn =barn;
  }

  public static PenRecord createRecord(Pen pen) {
    return PenRecord.builder().pen(pen).barn(pen.getBarn()).build();
  }

  public void writeNote(String memo) {
    writeMemo(memo);
    pen.registerDailyRecord(this);
  }
}
