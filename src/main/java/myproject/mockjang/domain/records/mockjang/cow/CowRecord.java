package myproject.mockjang.domain.records.mockjang.cow;

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
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.Records;
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

    private final boolean deleted = false;

    @Builder
    private CowRecord(Cow cow) {
        this.cow = cow;
    }

    public static CowRecord createRecord(Cow cow, RecordType recordType, LocalDateTime date) {
        CowRecord cowRecord = CowRecord.builder()
                .cow(cow)
                .build();
        cowRecord.registerRecordType(recordType);
        cowRecord.registerDate(date);
        return cowRecord;
    }

    public void recordsNullCheck(CowRecord cowRecord) {
        basicNullCheck(cowRecord);
    }

    public void registerRecordType(RecordType recordType) {
        super.registerRecordType(recordType);
    }

    public void registerDate(LocalDateTime dateTime) {
        super.registerDate(dateTime);
    }

    public void recordMemo(String memo) {
        registerRecord(memo);
        cow.registerRecord(this);
    }

    public void removeMemo(){
        cow.removeRecord(this);
    }

    public Long getCowId() {
        return cow.getId();
    }
}
