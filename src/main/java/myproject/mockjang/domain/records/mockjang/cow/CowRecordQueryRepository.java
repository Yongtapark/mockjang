package myproject.mockjang.domain.records.mockjang.cow;

import static myproject.mockjang.domain.records.mockjang.cow.QCowRecord.cowRecord;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.stereotype.Repository;

@Repository
public class CowRecordQueryRepository {

    private final JPAQueryFactory queryFactory;


    public CowRecordQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<CowRecord> search(String codeId, RecordType recordType, LocalDateTime date, String record) {
        BooleanBuilder predicate = searching(codeId, recordType, date, record);
        return queryFactory.selectFrom(cowRecord).where(predicate).fetch();
    }

    private BooleanBuilder searching(String codeId, RecordType recordType,
                                     LocalDateTime date, String record) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (codeId != null && !codeId.isEmpty()) {
            predicate.and(cowRecord.cow.codeId.like("%" + codeId + "%"));
        }
        if (recordType != null) {
            predicate.and(cowRecord.recordType.eq(recordType));
        }
        if (date != null) {
            LocalDateTime startDate = date.toLocalDate().atStartOfDay();
            LocalDateTime endDate = date.toLocalDate().atTime(LocalTime.MAX);
            predicate.and(cowRecord.date.between(startDate, endDate));
        }
        if (record != null) {
            predicate.and(cowRecord.record.like("%" + record + "%"));
        }
        return predicate;
    }
}
