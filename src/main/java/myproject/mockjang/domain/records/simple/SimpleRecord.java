package myproject.mockjang.domain.records.simple;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.Records;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE simple_record SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SimpleRecord extends Records {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codeId;

  private boolean deleted = false;

  @Builder
  private SimpleRecord(String codeId, boolean deleted, Long id) {
    this.codeId = codeId;
    this.deleted = deleted;
    this.id = id;
  }

  public static SimpleRecord create(String codeId, RecordType recordType, LocalDateTime date,
      String record) {
    SimpleRecord simpleRecord = SimpleRecord.builder()
        .codeId(codeId)
        .build();
    simpleRecord.registerDate(date);
    simpleRecord.registerRecordType(recordType);
    simpleRecord.registerRecord(record);
    return simpleRecord;
  }

  public void recordsNullCheck(SimpleRecord simpleRecord) {
    if (codeId == null || codeId.isBlank()) {
      throw new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage("codeId"));
    }
    basicNullCheck(simpleRecord);
  }

  public void update(String codeId, RecordType recordType, LocalDateTime date, String record) {
    this.codeId=codeId;
    registerRecordType(recordType);
    registerDate(date);
    registerRecord(record);
  }
}
