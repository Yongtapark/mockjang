package myproject.mockjang.domain.schedule;

import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.exception.schdules.ScheduleFormException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class ScheduleTest extends IntegrationTestSupport {

    @DisplayName("시작 날짜를 정하지 않았을 때, 읽은 날짜가 목표 날짜와 동일 하다면 스케줄 타입이 '진행중' 이다.")
    @Test
    void whenReadDateAndTargetDateSame() {
        //given
        Schedule schedule = Schedule.builder().targetDate(READ_DATE_SAME)
                .build();

        //when
        schedule.calculateScheduleStatus(READ_DATE);

        //then
        Assertions.assertThat(schedule.getScheduleStatus()).isEqualTo(ScheduleStatus.IN_PROGRESS);
    }

    @DisplayName("시작 날짜를 정하지 않았을 때, 읽은 날짜 기준 목표 날짜가 오지 않았다면  스케줄 타입이 '예정' 이다.")
    @Test
    void whenReadDateIsBeforeTargetDate() {
        //given
        Schedule schedule = Schedule.builder().targetDate(READ_DATE_AFTER)
                .build();

        //when
        schedule.calculateScheduleStatus(READ_DATE);

        //then
        Assertions.assertThat(schedule.getScheduleStatus()).isEqualTo(ScheduleStatus.UPCOMING);
    }

    @DisplayName("시작 날짜를 정하지 않았을 때, 읽은 날짜 기준 목표 날짜가 지났다면, 스케줄 타입이 '만료' 이다.")
    @Test
    void whenReadDateIsAfterTargetDate() {
        //given
        Schedule schedule = Schedule.builder().targetDate(READ_DATE_BEFORE)
                .build();

        //when
        schedule.calculateScheduleStatus(READ_DATE);

        //then
        Assertions.assertThat(schedule.getScheduleStatus()).isEqualTo(ScheduleStatus.EXPIRED);
    }

    @DisplayName("읽은 날짜 기준 시작 날짜가 지났고, 목표 날짜를 지나지 않았다면 스케줄 타입이 '진행중' 이다.")
    @Test
    void whenStartDateAfterTargetDateBefore() {
        //given
        Schedule schedule = Schedule.builder().targetDate(READ_DATE_AFTER)
                .startDate(READ_DATE_BEFORE).build();

        //when
        schedule.calculateScheduleStatus(READ_DATE);

        //then
        Assertions.assertThat(schedule.getScheduleStatus()).isEqualTo(ScheduleStatus.IN_PROGRESS);
    }

    @DisplayName("읽은 날짜 기준 시작 날짜가 지나지 않았꼬, 목표 날짜를 지나지 않았다면 스케줄 타입이 '예정' 이다.")
    @Test
    void whenStartDateBeforeTargetDateBefore() {
        //given
        Schedule schedule = Schedule.builder().targetDate(READ_DATE_AFTER)
                .startDate(READ_DATE_AFTER).build();

        //when
        schedule.calculateScheduleStatus(READ_DATE);

        //then
        Assertions.assertThat(schedule.getScheduleStatus()).isEqualTo(ScheduleStatus.UPCOMING);
    }

    @DisplayName("읽은 날짜 기준 시작 날짜가 지났고, 목표 날짜를 지났다면 스케줄 타입이 '만료' 이다.")
    @Test
    void whenStartDateAfterTargetDateAfter() {
        //given
        Schedule schedule = Schedule.builder().targetDate(TARGET_DATE_AFTER_READ)
                .startDate(START_DATE_BEFORE_READ).build();

        //when
        schedule.calculateScheduleStatus(READ_DATE);

        //then
        Assertions.assertThat(schedule.getScheduleStatus()).isEqualTo(ScheduleStatus.EXPIRED);
    }

    @DisplayName("일정 생성 시 목표 날짜보다 시작 날짜가 늦으면 예외를 발생시킨다.")
    @Test
    void createWhenTargetDateBeforeStartDate() {
        //given //when //then
        Assertions.assertThatThrownBy(() -> Schedule.create(READ_DATE.plusDays(1), READ_DATE, SCHEDULE_CONTEXT_1))
                .isInstanceOf(ScheduleFormException.class)
                .hasMessage("시작 날짜보다 목표 날짜가 시간상 뒤에 위치할 수 없습니다.");
    }
}