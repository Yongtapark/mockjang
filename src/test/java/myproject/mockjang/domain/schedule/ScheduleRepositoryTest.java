package myproject.mockjang.domain.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScheduleRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @DisplayName("시작일 기준으로 일정을 반환한다.")
    @Test
    void findAllByStartDateGreaterThanEqual() {
        //given
        Schedule schedule0 = Schedule.builder().startDate(READ_DATE.minusDays(1)).build();
        Schedule schedule1 = Schedule.builder().startDate(READ_DATE).build();
        Schedule schedule2 = Schedule.builder().startDate(READ_DATE.plusDays(1)).build();
        Schedule schedule3 = Schedule.builder().startDate(READ_DATE.plusDays(10)).build();
        scheduleRepository.saveAll(List.of(schedule0, schedule1, schedule2, schedule3));

        //when
        List<Schedule> schedules = scheduleRepository.findAllByStartDateGreaterThanEqual(READ_DATE);

        //then
        assertThat(schedules).containsExactly(schedule1, schedule2, schedule3);
        assertThat(schedules).doesNotContain(schedule0);
    }

}