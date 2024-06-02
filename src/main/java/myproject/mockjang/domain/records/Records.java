package myproject.mockjang.domain.records;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import myproject.mockjang.domain.AuditingEntity;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Records extends AuditingEntity {

  private String record;
  private LocalDateTime date;
  @Enumerated(EnumType.STRING)
  private RecordType recordType;

  protected void registerRecord(String memo) {
    this.record = memo;
  }

  protected void registerRecordType(RecordType recordType) {
    this.recordType = recordType;
  }

  protected void registerDate(LocalDateTime date) {
    this.date = date;
  }

  protected void basicNullCheck(Records record) {
    if (record.getRecordType() == null) {
      throw new NotExistException(COMMON_NOT_EXIST.formatMessage(RecordType.class));
    }
    if (record.getDate() == null) {
      throw new NotExistException(COMMON_NOT_EXIST.formatMessage(LocalDateTime.class));
    }
    if (record.getRecord() == null || record.getRecord().isBlank()) {
      throw new NotExistException(COMMON_NOT_EXIST.formatMessage("record"));
    }
  }
}
