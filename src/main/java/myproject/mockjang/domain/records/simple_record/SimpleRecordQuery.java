package myproject.mockjang.domain.records.simple_record;

import static myproject.mockjang.domain.records.simple_record.QSimpleRecord.simpleRecord;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.stereotype.Repository;


@Repository
public class SimpleRecordQuery {

  private final JPAQueryFactory queryFactory;

  public SimpleRecordQuery(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  BooleanExpression predicate = simpleRecord.isNotNull();


  public List<SimpleRecord> search(String codeId, RecordType recordType, LocalDateTime date) {
    if (codeId != null && !codeId.isEmpty()) {
      predicate = predicate.and(simpleRecord.codeId.like("%" + codeId + "%"));
    }
    if (recordType != null) {
      predicate = predicate.and(simpleRecord.recordType.eq(recordType));
    }
    if (date != null) {
      predicate = predicate.and(simpleRecord.date.eq(date));
    }
    return queryFactory.selectFrom(simpleRecord).where(predicate).fetch();
  }
}
