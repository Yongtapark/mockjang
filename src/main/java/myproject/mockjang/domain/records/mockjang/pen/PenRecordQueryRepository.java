package myproject.mockjang.domain.records.mockjang.pen;

import static myproject.mockjang.domain.records.mockjang.pen.QPenRecord.penRecord;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.stereotype.Repository;

@Repository
public class PenRecordQueryRepository {

    private final JPAQueryFactory queryFactory;


    public PenRecordQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<PenRecord> search(String codeId, RecordType recordType, LocalDateTime date, String record) {
        BooleanBuilder predicate = searching(codeId, recordType, date, record);
        return queryFactory.selectFrom(penRecord).where(predicate).fetch();
    }

    private BooleanBuilder searching(String codeId, RecordType recordType,
                                     LocalDateTime date, String record) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (codeId != null && !codeId.isEmpty()) {
            predicate.and(penRecord.pen.codeId.like("%" + codeId + "%"));
        }
        if (recordType != null) {
            predicate.and(penRecord.recordType.eq(recordType));
        }
        if (date != null) {
            LocalDateTime startDate = date.toLocalDate().atStartOfDay();
            LocalDateTime endDate = date.toLocalDate().atTime(LocalTime.MAX);
            predicate.and(penRecord.date.between(startDate, endDate));
        }
        if (record != null) {
            predicate.and(penRecord.record.like("%" + record + "%"));
        }
        return predicate;
    }
}
