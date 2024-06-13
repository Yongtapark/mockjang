package myproject.mockjang.domain.schedule;

import static java.time.DayOfWeek.SUNDAY;
import static myproject.mockjang.domain.records.simple.QSimpleRecord.simpleRecord;
import static myproject.mockjang.domain.schedule.QSchedule.schedule;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Schedule> findAllScheduleThisDayToEndOfWeek(LocalDateTime date){
        LocalDateTime endOfWeek = date.with(SUNDAY);
        return queryFactory
                .selectFrom(schedule)
                .where(schedule.startDate.loe(endOfWeek).and(schedule.targetDate.goe(date)))
                .orderBy(schedule.targetDate.asc(),schedule.startDate.asc())
                .fetch();
    }

    public List<Schedule> search(String context, ScheduleStatus scheduleStatus,
        LocalDateTime targetDate,LocalDateTime startDate){
        BooleanBuilder predict = searching(context, scheduleStatus, targetDate, startDate);
        return queryFactory.selectFrom(schedule).where(predict).fetch();
    }

    private BooleanBuilder searching(String context, ScheduleStatus scheduleStatus,
        LocalDateTime targetDate,LocalDateTime startDate) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (context != null && !context.isEmpty()) {
            predicate.and(schedule.context.like("%" + context + "%"));
        }
        if (scheduleStatus != null) {
            predicate.and(schedule.scheduleStatus.eq(scheduleStatus));
        }
        if (startDate != null) {
            predicate.and(schedule.targetDate.eq(targetDate));
        }
        if (startDate != null) {
            predicate.and(schedule.startDate.eq(startDate));
        }
        return predicate;
    }
}
