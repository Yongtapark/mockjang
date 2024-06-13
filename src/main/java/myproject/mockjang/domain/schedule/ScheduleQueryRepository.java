package myproject.mockjang.domain.schedule;

import static java.time.DayOfWeek.SUNDAY;
import static myproject.mockjang.domain.schedule.QSchedule.schedule;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
}
