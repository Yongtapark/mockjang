package myproject.mockjang.domain.records.mockjang.barn;


import static myproject.mockjang.domain.records.mockjang.barn.QBarnRecord.barnRecord;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.stereotype.Repository;

@Repository
public class BarnRecordQueryRepository {

    private final JPAQueryFactory queryFactory;


    public BarnRecordQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<BarnRecord> search(String codeId, RecordType recordType, LocalDateTime date, String record) {
        BooleanBuilder predicate = searching(codeId, recordType, date, record);
        return queryFactory.selectFrom(barnRecord).where(predicate).fetch();
    }

    private BooleanBuilder searching(String codeId, RecordType recordType,
                                     LocalDateTime date, String record) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (codeId != null && !codeId.isEmpty()) {
            predicate.and(barnRecord.barn.codeId.like("%" + codeId + "%"));
        }
        if (recordType != null) {
            predicate.and(barnRecord.recordType.eq(recordType));
        }
        if (date != null) {
            LocalDateTime startDate = date.toLocalDate().atStartOfDay();
            LocalDateTime endDate = date.toLocalDate().atTime(LocalTime.MAX);
            predicate.and(barnRecord.date.between(startDate, endDate));
        }
        if (record != null) {
            predicate.and(barnRecord.record.like("%" + record + "%"));
        }
        return predicate;
    }
}
