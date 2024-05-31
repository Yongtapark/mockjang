package myproject.mockjang.domain.records;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

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

  public static CowRecord createRecord(Cow cow, RecordType recordType,LocalDateTime date) {
    CowRecord cowRecord = CowRecord.builder()
        .cow(cow)
        .pen(cow.getPen())
        .barn(cow.getBarn())
        .build();
    cowRecord.registerRecordType(recordType);
    cowRecord.registerDate(date);
    check(cowRecord);
    return cowRecord;
  }

  private static void check(CowRecord cowRecord) {
    if (cowRecord.getBarn() == null ) {
      throw new RecordException(COMMON_NOT_EXIST.formatMessage(Barn.class));
    }
    if (cowRecord.getPen() == null) {
      throw new RecordException(COMMON_NOT_EXIST.formatMessage(Pen.class));
    }
    if (cowRecord.getRecordType() == null) {
      throw new RecordException(COMMON_NOT_EXIST.formatMessage(RecordType.class));
    }
    if (cowRecord.getDate() == null) {
      throw new RecordException(COMMON_NOT_EXIST.formatMessage(LocalDateTime.class));
    }
  }

  public void registerRecordType(RecordType recordType) {
    super.registerRecordType(recordType);
  }

  public void registerDate(LocalDateTime dateTime) {
    super.registerDate(dateTime);
  }

  public void recordMemo(String memo) {
    writeMemo(memo);
    cow.registerRecord(this);
  }
}
