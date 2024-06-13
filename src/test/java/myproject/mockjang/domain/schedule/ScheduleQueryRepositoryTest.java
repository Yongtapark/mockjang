package myproject.mockjang.domain.schedule;

import static org.assertj.core.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScheduleQueryRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ScheduleQueryRepository scheduleQueryRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("조회 날짜 부터, 해당 주 일요일 까지의 일정을 조회한다.")
    @Test
    void findAllScheduleThisDayToEndOfWeek() {
        //given
        LocalDateTime startDayOfWeek = READ_DATE.with(DayOfWeek.MONDAY);
        LocalDateTime endDayOfWeek = READ_DATE.with(DayOfWeek.SUNDAY);

        Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE).build();
        Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7)).build();
        Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE.plusDays(7)).build();
        Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE.minusDays(7)).build();
        Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7)).targetDate(READ_DATE.plusDays(7)).build();
        scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4,schedule5));


        //when
        List<Schedule> allScheduleThisWeek = scheduleQueryRepository.findAllScheduleThisDayToEndOfWeek(READ_DATE);

        //then
        assertThat(allScheduleThisWeek).containsExactly(schedule1,schedule3,schedule2);
        assertThat(allScheduleThisWeek).doesNotContain(schedule4,schedule5);
    }
}