package myproject.mockjang.domain.schedule;

import static java.time.DayOfWeek.SUNDAY;
import static myproject.mockjang.domain.schedule.QSchedule.schedule;
import static myproject.mockjang.domain.schedule.ScheduleStatus.EXPIRED;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<Long> findAllUpcomingSchedules(LocalDateTime date) {
        return queryFactory.select(schedule.id)
                .from(schedule)
                .where(schedule.startDate.eq(date.plusDays(1)))
                .fetch();
    }

    public List<Schedule> findAllScheduleExceptExpired() {
        return queryFactory.selectFrom(schedule)
                .where(schedule.scheduleStatus.ne(EXPIRED))
                .fetch();
    }

    public List<Schedule> findAllScheduleThisDayToEndOfWeek(LocalDateTime date) {
        LocalDateTime endOfWeek = date.with(SUNDAY);
        return queryFactory
                .selectFrom(schedule)
                .where(schedule.startDate.loe(endOfWeek).and(schedule.targetDate.goe(date)))
                .orderBy(schedule.targetDate.asc(), schedule.startDate.asc())
                .fetch();
    }

    public List<Schedule> search(LocalDateTime startDate, LocalDateTime targetDate,
                                 String context, ScheduleStatus scheduleStatus) {
        BooleanBuilder predict = searching(context, scheduleStatus, targetDate, startDate);
        return queryFactory.selectFrom(schedule).where(predict).fetch();
    }

    private BooleanBuilder searching(String context, ScheduleStatus scheduleStatus,
                                     LocalDateTime targetDate, LocalDateTime startDate) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (context != null && !context.isEmpty()) {
            predicate.and(schedule.context.like("%" + context + "%"));
        }
        if (scheduleStatus != null) {
            predicate.and(schedule.scheduleStatus.eq(scheduleStatus));
        }
        if (targetDate != null) {
            LocalDateTime startOfDay = targetDate.toLocalDate().atStartOfDay();
            LocalDateTime endOfDay = targetDate.toLocalDate().atTime(LocalTime.MAX);
            predicate.and(schedule.targetDate.between(startOfDay, endOfDay));
        }
        if (startDate != null) {
            LocalDateTime startOfDay = startDate.toLocalDate().atStartOfDay();
            LocalDateTime endOfDay = startDate.toLocalDate().atTime(LocalTime.MAX);
            predicate.and(schedule.startDate.between(startOfDay, endOfDay));
        }
        return predicate;
    }
}
