package myproject.mockjang.domain.records.simple;

import static myproject.mockjang.domain.records.simple.QSimpleRecord.simpleRecord;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.stereotype.Repository;


@Repository
public class SimpleRecordQueryRepository {

  private final JPAQueryFactory queryFactory;

  public SimpleRecordQueryRepository(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public List<SimpleRecord> search(String codeId, RecordType recordType, LocalDateTime date) {
    BooleanBuilder predicate = new BooleanBuilder();
    if (codeId != null && !codeId.isEmpty()) {
      predicate.and(simpleRecord.codeId.like("%" + codeId + "%"));
    }
    if (recordType != null) {
      predicate.and(simpleRecord.recordType.eq(recordType));
    }
    if (date != null) {
      predicate.and(simpleRecord.date.eq(date));
    }
    return queryFactory.selectFrom(simpleRecord).where(predicate).fetch();
  }
}
