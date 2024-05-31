package myproject.mockjang.domain.records;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.Getter;
import myproject.mockjang.domain.creater.YongTaPark;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Getter
public abstract class Records extends AbstractAuditable<YongTaPark, Long> {

  private String memo;
  private LocalDateTime date;
  @Enumerated(EnumType.STRING)
  private RecordType recordType;

  protected void writeMemo(String memo) {
    this.memo = memo;
  }

  protected void registerRecordType(RecordType recordType) {
    this.recordType =recordType;
  }
  protected void registerDate(LocalDateTime date) {
    this.date = date;
  }
}
