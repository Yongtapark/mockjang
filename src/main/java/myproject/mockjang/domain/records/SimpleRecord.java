package myproject.mockjang.domain.records;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE simple_record SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class SimpleRecord extends Records{
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

    public static SimpleRecord create(String codeId, RecordType recordType, LocalDateTime date, String record) {
        SimpleRecord simpleRecord = SimpleRecord.builder()
                .codeId(codeId)
                .build();
        simpleRecord.registerDate(date);
        simpleRecord.registerRecordType(recordType);
        simpleRecord.registerRecord(record);
        return simpleRecord;
    }

    public void recordsNullCheck(SimpleRecord simpleRecord) {
        basicNullCheck(simpleRecord);
    }
}
