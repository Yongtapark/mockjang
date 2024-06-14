package myproject.mockjang.api.service.schedule;

import static myproject.mockjang.domain.schedule.ScheduleStatus.IN_PROGRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.schedule.reponse.ScheduleResponse;
import myproject.mockjang.api.service.schedule.request.ScheduleCreateServiceRequest;
import myproject.mockjang.api.service.schedule.request.ScheduleSearchServiceRequest;
import myproject.mockjang.api.service.schedule.request.ScheduleUpdateServiceRequest;
import myproject.mockjang.domain.schedule.Schedule;
import myproject.mockjang.domain.schedule.ScheduleRepository;
import myproject.mockjang.domain.schedule.ScheduleStatus;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.schdules.ScheduleFormException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ScheduleServiceTest extends IntegrationTestSupport {

  @Autowired
  private ScheduleService scheduleService;
  @Autowired
  private ScheduleRepository scheduleRepository;

  final LocalDateTime readDateWednesday = READ_DATE;
  final LocalDateTime monday = readDateWednesday.with(DayOfWeek.MONDAY);
  final LocalDateTime tuesday = readDateWednesday.minusDays(1);
  final LocalDateTime sunday = readDateWednesday.with(DayOfWeek.SUNDAY);
  final LocalDateTime readDayPlusNextWeek = readDateWednesday.plusDays(7);
  final LocalDateTime nextWeekMonday = sunday.plusDays(1);

  @DisplayName("일정을 생성한다.")
  @Test
  void create() {
    //given
    ScheduleCreateServiceRequest request = ScheduleCreateServiceRequest.builder()
        .targetDate(READ_DATE.plusDays(1))
        .startDate(READ_DATE.minusDays(1))
        .context(SCHEDULE_CONTEXT_1)
        .build();

    //when
    ScheduleResponse response = scheduleService.create(request);

    //then
    Schedule schedule = scheduleRepository.findById(response.getId()).orElseThrow();
    assertThat(schedule.getContext()).isEqualTo(SCHEDULE_CONTEXT_1);
    assertThat(schedule.getStartDate()).isEqualTo(request.getStartDate());
    assertThat(schedule.getTargetDate()).isEqualTo(request.getTargetDate());
  }

  @DisplayName("일정을 생성할 때, 목표날짜보다 시작날짜가 뒤에 있으면 예외를 발생시킨다.")
  @Test
  void createWhenStartDateIsAfterTargetDate() {
    //given
    ScheduleCreateServiceRequest request = ScheduleCreateServiceRequest.builder()
        .targetDate(READ_DATE.minusDays(1))
        .startDate(READ_DATE.plusDays(1))
        .context(SCHEDULE_CONTEXT_1)
        .build();

    //when //then
    assertThatThrownBy(() -> scheduleService.create(request)).isInstanceOf(
        ScheduleFormException.class).hasMessage(
        Exceptions.DOMAIN_SCHEDULE_FORM.getMessage());
  }

  @DisplayName("일정을 수정한다.")
  @Test
  void update() {
    //given
    Schedule savedSchedule = createAndSave(READ_DATE.minusDays(1), READ_DATE.plusDays(1),
        SCHEDULE_CONTEXT_1);

    ScheduleUpdateServiceRequest request = ScheduleUpdateServiceRequest.builder()
        .id(savedSchedule.getId())
        .targetDate(READ_DATE.plusDays(2))
        .startDate(READ_DATE.minusDays(2))
        .context(SCHEDULE_CONTEXT_2)
        .readDate(READ_DATE)
        .build();

    //when
    scheduleService.update(request);

    //then
    Schedule findSchedule = scheduleRepository.findById(request.getId()).orElseThrow();
    assertThat(findSchedule.getContext()).isEqualTo(SCHEDULE_CONTEXT_2);
    assertThat(findSchedule.getStartDate()).isEqualTo(request.getStartDate());
    assertThat(findSchedule.getTargetDate()).isEqualTo(request.getTargetDate());
  }

  @DisplayName("일정을 수정할 때, 목표날짜보다 시작날짜가 뒤에 있으면 예외를 발생시킨다.")
  @Test
  void updateWhenStartDateIsAfterTargetDate() {
    //given
    Schedule savedSchedule = createAndSave(READ_DATE.minusDays(1), READ_DATE.plusDays(1),
        SCHEDULE_CONTEXT_1);

    ScheduleUpdateServiceRequest request = ScheduleUpdateServiceRequest.builder()
        .id(savedSchedule.getId())
        .targetDate(READ_DATE.minusDays(1))
        .startDate(READ_DATE.plusDays(1))
        .context(SCHEDULE_CONTEXT_2)
        .build();

    //when //then
    assertThatThrownBy(() -> scheduleService.update(request)).isInstanceOf(
        ScheduleFormException.class).hasMessage(
        Exceptions.DOMAIN_SCHEDULE_FORM.getMessage());
  }

  @DisplayName("일정을 수정하면 상태도 수정된다.")
  @Test
  void updateWithStatusChange() {
    //given
    Schedule savedSchedule = createAndSave(READ_DATE.minusDays(1), READ_DATE.minusDays(1),
        SCHEDULE_CONTEXT_1);

    ScheduleUpdateServiceRequest request = ScheduleUpdateServiceRequest.builder()
        .id(savedSchedule.getId())
        .targetDate(READ_DATE.plusDays(2))
        .startDate(READ_DATE.minusDays(2))
        .context(SCHEDULE_CONTEXT_2)
        .readDate(READ_DATE)
        .build();

    //when
    scheduleService.update(request);

    //then
    Schedule findSchedule = scheduleRepository.findById(request.getId()).orElseThrow();
    assertThat(findSchedule.getContext()).isEqualTo(SCHEDULE_CONTEXT_2);
    assertThat(findSchedule.getStartDate()).isEqualTo(request.getStartDate());
    assertThat(findSchedule.getTargetDate()).isEqualTo(request.getTargetDate());
    assertThat(findSchedule.getScheduleStatus()).isEqualTo(IN_PROGRESS);
  }

  @DisplayName("일정을 제거한다.")
  @Test
  void remove() {
    //given
    Schedule savedSchedule = createAndSave(READ_DATE.minusDays(1), READ_DATE.plusDays(1),
        SCHEDULE_CONTEXT_1);

    //when
    scheduleService.remove(savedSchedule.getId());

    //then
    List<Schedule> schedules = scheduleRepository.findAll();
    assertThat(schedules).hasSize(0);
  }

  @DisplayName("조회일 부터 조회일 주말까지의 일정을 조회한다.")
  @Test
  void showThisWeekScheduleFromToday() {
    //given
    Schedule savedSchedule0 = createAndSave(monday, tuesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule1 = createAndSave(monday, readDateWednesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule2 = createAndSave(readDateWednesday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    Schedule savedSchedule3 = createAndSave(sunday, sunday, SCHEDULE_CONTEXT_2);
    Schedule savedSchedule4 = createAndSave(nextWeekMonday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);

    //when
    List<ScheduleResponse> scheduleResponses = scheduleService.showThisWeekScheduleFromToday(READ_DATE);

    //then
    List<Long> scheduleIds = scheduleResponses.stream().map(ScheduleResponse::getId).toList();
    assertThat(scheduleIds).containsExactly(savedSchedule1.getId(), savedSchedule3.getId(),
        savedSchedule2.getId());
    assertThat(scheduleIds).doesNotContain(savedSchedule0.getId(), savedSchedule4.getId());
  }

  @DisplayName("시작일 기준으로 일정을 검색한다.")
  @Test
  void searchWithStartDate() {
    //given
    Schedule savedSchedule0 = createAndSave(monday, tuesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule1 = createAndSave(monday, readDateWednesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule2 = createAndSave(readDateWednesday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    Schedule savedSchedule3 = createAndSave(sunday, sunday, SCHEDULE_CONTEXT_2);
    Schedule savedSchedule4 = createAndSave(nextWeekMonday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    ScheduleSearchServiceRequest request = ScheduleSearchServiceRequest.builder()
        .startDate(monday).build();

    //when
    List<ScheduleResponse> search = scheduleService.search(request);

    //then
    List<Long> ids = search.stream().map(ScheduleResponse::getId).toList();
    assertThat(ids).containsExactly(savedSchedule0.getId(), savedSchedule1.getId());
    assertThat(ids).doesNotContain(savedSchedule2.getId(), savedSchedule4.getId(),
        savedSchedule3.getId());
  }

  @DisplayName("목표일 기준으로 일정을 검색한다.")
  @Test
  void searchWithTargetDate() {
    //given
    Schedule savedSchedule0 = createAndSave(monday, tuesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule1 = createAndSave(monday, readDateWednesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule2 = createAndSave(readDateWednesday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    Schedule savedSchedule3 = createAndSave(sunday, sunday, SCHEDULE_CONTEXT_2);
    Schedule savedSchedule4 = createAndSave(nextWeekMonday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    ScheduleSearchServiceRequest request = ScheduleSearchServiceRequest.builder()
        .targetDate(readDayPlusNextWeek).build();

    //when
    List<ScheduleResponse> search = scheduleService.search(request);

    //then
    List<Long> ids = search.stream().map(ScheduleResponse::getId).toList();
    assertThat(ids).containsExactly(savedSchedule2.getId(), savedSchedule4.getId());
    assertThat(ids).doesNotContain(savedSchedule0.getId(), savedSchedule1.getId(),
        savedSchedule3.getId());
  }

  @DisplayName("내용 기준으로 일정을 검색한다.")
  @Test
  void searchWithContext() {
    //given
    Schedule savedSchedule0 = createAndSave(monday, tuesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule1 = createAndSave(monday, readDateWednesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule2 = createAndSave(readDateWednesday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    Schedule savedSchedule3 = createAndSave(sunday, sunday, SCHEDULE_CONTEXT_2);
    Schedule savedSchedule4 = createAndSave(nextWeekMonday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    ScheduleSearchServiceRequest request = ScheduleSearchServiceRequest.builder()
        .context(SCHEDULE_CONTEXT_1).build();

    //when
    List<ScheduleResponse> search = scheduleService.search(request);

    //then
    List<Long> ids = search.stream().map(ScheduleResponse::getId).toList();
    assertThat(ids).containsExactly(savedSchedule0.getId(), savedSchedule1.getId());
    assertThat(ids).doesNotContain(savedSchedule2.getId(), savedSchedule4.getId(),
        savedSchedule3.getId());
  }

  @DisplayName("조회일을 비교하며 상태를 추적한다.")
  @Test
  void calculateScheduleStatus() {
    //given
    Schedule savedSchedule0 = createAndSave(READ_DATE, monday, tuesday, SCHEDULE_CONTEXT_1);
    Schedule savedSchedule1 = createAndSave(READ_DATE, monday, readDateWednesday,
        SCHEDULE_CONTEXT_1);
    Schedule savedSchedule2 = createAndSave(READ_DATE, readDateWednesday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    Schedule savedSchedule3 = createAndSave(READ_DATE, sunday, sunday, SCHEDULE_CONTEXT_2);
    Schedule savedSchedule4 = createAndSave(READ_DATE, nextWeekMonday, readDayPlusNextWeek,
        SCHEDULE_CONTEXT_2);
    List<Schedule> savedSchedules = List.of(savedSchedule0, savedSchedule1, savedSchedule2,
        savedSchedule3, savedSchedule4);

    //when
    scheduleService.calculateScheduleStatus(savedSchedules, READ_DATE);

    //then
    assertThat(savedSchedule0.getScheduleStatus()).isEqualTo(ScheduleStatus.EXPIRED);
    assertThat(savedSchedule1.getScheduleStatus()).isEqualTo(IN_PROGRESS);
    assertThat(savedSchedule2.getScheduleStatus()).isEqualTo(IN_PROGRESS);
    assertThat(savedSchedule3.getScheduleStatus()).isEqualTo(ScheduleStatus.UPCOMING);
    assertThat(savedSchedule4.getScheduleStatus()).isEqualTo(ScheduleStatus.UPCOMING);
  }


  private Schedule createAndSave(LocalDateTime startDate, LocalDateTime targetDate,
      String context) {
    Schedule schedule = Schedule.create(startDate, targetDate,
        context);
    return scheduleRepository.save(schedule);
  }

  private Schedule createAndSave(LocalDateTime readDate, LocalDateTime startDate,
      LocalDateTime targetDate, String context) {
    Schedule schedule = Schedule.create(startDate, targetDate,
        context);
    schedule.calculateScheduleType(readDate);
    return scheduleRepository.save(schedule);
  }
}