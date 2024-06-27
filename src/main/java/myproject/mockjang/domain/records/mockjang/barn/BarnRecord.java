package myproject.mockjang.domain.records.mockjang.barn;

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
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.Records;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE barn_record SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class BarnRecord extends Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Barn barn;

    private final boolean deleted = false;

    @Builder
    private BarnRecord(Barn barn) {
        this.barn = barn;
    }

    public static BarnRecord creatRecord(Barn barn, RecordType recordType, LocalDateTime date) {
        BarnRecord barnRecord = BarnRecord.builder().barn(barn).build();
        barnRecord.registerRecordType(recordType);
        barnRecord.registerDate(date);
        return barnRecord;
    }

    public void writeNote(String memo) {
        if (barn == null) {
            throw new RuntimeException("there is no barn or cow");
        }
        registerRecord(memo);
        barn.registerDailyRecord(this);
    }
}
