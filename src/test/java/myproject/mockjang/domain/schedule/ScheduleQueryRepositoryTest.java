package myproject.mockjang.domain.schedule;

import static myproject.mockjang.domain.schedule.ScheduleStatus.EXPIRED;
import static org.assertj.core.api.Assertions.*;

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
    Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE)
        .build();
    Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7))
        .build();
    Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .targetDate(READ_DATE.plusDays(7)).build();
    Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .targetDate(READ_DATE.minusDays(7)).build();
    Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7))
        .targetDate(READ_DATE.plusDays(7)).build();
    scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5));

    //when
    List<Schedule> allScheduleThisWeek = scheduleQueryRepository.findAllScheduleThisDayToEndOfWeek(
        READ_DATE);

    //then
    assertThat(allScheduleThisWeek).containsExactly(schedule1, schedule3, schedule2);
    assertThat(allScheduleThisWeek).doesNotContain(schedule4, schedule5);
  }

  @DisplayName("시작일을 기준으로 일정을 검색한다.")
  @Test
  void searchWithStartDate() {
    //given
    Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE)
        .build();
    Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7))
        .build();
    Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .targetDate(READ_DATE.plusDays(7)).build();
    Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .targetDate(READ_DATE.minusDays(7)).build();
    Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7))
        .targetDate(READ_DATE.plusDays(7)).build();
    scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5));

    //when
    List<Schedule> searches = scheduleQueryRepository.search(READ_DATE, null, null, null);

    //then
    assertThat(searches).containsOnly(schedule2);
  }

  @DisplayName("목표일을 기준으로 일정을 검색한다.")
  @Test
  void searchWithTargetDate() {
    //given
    Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE)
        .build();
    Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7))
        .build();
    Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .targetDate(READ_DATE.plusDays(7)).build();
    Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .targetDate(READ_DATE.minusDays(7)).build();
    Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7))
        .targetDate(READ_DATE.plusDays(7)).build();
    scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5));

    //when
    List<Schedule> searches = scheduleQueryRepository.search(null, READ_DATE, null, null);

    //then
    assertThat(searches).containsOnly(schedule1);
  }

  @DisplayName("내용을 기준으로 일정을 검색한다.")
  @Test
  void searchWithContext() {
    //given
    Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE)
        .context(SCHEDULE_CONTEXT_1)
        .build();
    Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .build();
    Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.plusDays(7)).build();
    Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.minusDays(7)).build();
    Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.plusDays(7)).build();
    scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5));

    //when
    List<Schedule> searches = scheduleQueryRepository.search(null, null, SCHEDULE_CONTEXT_1, null);

    //then
    assertThat(searches).containsOnly(schedule1);
  }

  @DisplayName("일정 상태를 기준으로 일정을 검색한다.")
  @Test
  void searchWithScheduleStatus() {
    //given
    Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE)
        .context(SCHEDULE_CONTEXT_1)
        .build();
    Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .build();
    Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.plusDays(7)).build();
    Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.minusDays(7)).build();
    Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.plusDays(7)).build();

    schedule1.calculateScheduleStatus(READ_DATE);
    schedule2.calculateScheduleStatus(READ_DATE);
    schedule3.calculateScheduleStatus(READ_DATE);
    schedule4.calculateScheduleStatus(READ_DATE);
    schedule5.calculateScheduleStatus(READ_DATE);

    scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5));

    //when
    List<Schedule> searches = scheduleQueryRepository.search(null, null, null, EXPIRED);

    //then
    assertThat(searches).containsOnly(schedule4);
  }


  @DisplayName("만료된 일정을 제외한 일정들을 조회한다.")
  @Test
  void findAllScheduleExceptExpired() {
    //given
    Schedule schedule1 = Schedule.builder().startDate(READ_DATE.minusDays(7)).targetDate(READ_DATE)
        .context(SCHEDULE_CONTEXT_1)
        .build();
    Schedule schedule2 = Schedule.builder().startDate(READ_DATE).targetDate(READ_DATE.plusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .build();
    Schedule schedule3 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.plusDays(7)).build();
    Schedule schedule4 = Schedule.builder().startDate(READ_DATE.minusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.minusDays(7)).build();
    Schedule schedule5 = Schedule.builder().startDate(READ_DATE.plusDays(7))
        .context(SCHEDULE_CONTEXT_2)
        .targetDate(READ_DATE.plusDays(7)).build();

    schedule1.calculateScheduleStatus(READ_DATE);
    schedule2.calculateScheduleStatus(READ_DATE);
    schedule3.calculateScheduleStatus(READ_DATE);
    schedule4.calculateScheduleStatus(READ_DATE);
    schedule5.calculateScheduleStatus(READ_DATE);

    scheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3, schedule4, schedule5));

    //when
    List<Schedule> schedules = scheduleQueryRepository.findAllScheduleExceptExpired();

    //then
    assertThat(schedules).containsOnly(schedule1, schedule2, schedule3, schedule5);
    assertThat(schedules).doesNotContain(schedule4);
  }
}