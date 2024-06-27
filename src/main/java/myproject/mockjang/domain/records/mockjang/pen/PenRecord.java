package myproject.mockjang.domain.records.mockjang.pen;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.Records;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE pen_record SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class PenRecord extends Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pen pen;

    private final boolean deleted = false;

    @Builder
    private PenRecord(Pen pen) {
        this.pen = pen;
    }

    public Long getPenId() {
        return pen.getId();
    }

    public static PenRecord createRecord(Pen pen, RecordType recordType, LocalDateTime date) {
        PenRecord penRecord = PenRecord.builder().pen(pen).build();
        penRecord.registerDate(date);
        penRecord.registerRecordType(recordType);
        return penRecord;
    }

    public void writeNote(String memo) {
        registerRecord(memo);
        pen.registerDailyRecord(this);
    }

    public void recordsNullCheck(PenRecord penRecord) {
        basicNullCheck(penRecord);
    }

    public void registerRecordType(RecordType recordType) {
        super.registerRecordType(recordType);
    }

    public void registerDate(LocalDateTime dateTime) {
        super.registerDate(dateTime);
    }

    public void recordMemo(String memo) {
        registerRecord(memo);
        pen.registerRecord(this);
    }

    public void removeMemo() {
        pen.removeRecord(this);
    }
}
