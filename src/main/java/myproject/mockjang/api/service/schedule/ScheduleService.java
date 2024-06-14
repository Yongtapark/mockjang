package myproject.mockjang.api.service.schedule;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.schedule.request.ScheduleCreateServiceRequest;
import myproject.mockjang.api.service.schedule.reponse.ScheduleResponse;
import myproject.mockjang.api.service.schedule.request.ScheduleRemoveServiceRequest;
import myproject.mockjang.api.service.schedule.request.ScheduleSearchServiceRequest;
import myproject.mockjang.api.service.schedule.request.ScheduleUpdateServiceRequest;
import myproject.mockjang.domain.schedule.Schedule;
import myproject.mockjang.domain.schedule.ScheduleQueryRepository;
import myproject.mockjang.domain.schedule.ScheduleRepository;
import myproject.mockjang.domain.schedule.ScheduleStatus;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final ScheduleQueryRepository scheduleQueryRepository;

  public ScheduleResponse create(ScheduleCreateServiceRequest request) {
    Schedule schedule = Schedule.create(request.getStartDate(), request.getTargetDate(),
        request.getContext());
    scheduleRepository.save(schedule);
    return ScheduleResponse.of(schedule);
  }

  public void update(ScheduleUpdateServiceRequest request) {
    Schedule targetSchedule = scheduleRepository.findById(request.getId()).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Schedule.class)));
    targetSchedule.update(request.getContext(), request.getStartDate(), request.getTargetDate());
    scheduleRepository.save(targetSchedule);
  }

  public void remove(ScheduleRemoveServiceRequest request) {
    Schedule targetSchedule = scheduleRepository.findById(request.getId()).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Schedule.class)));
    scheduleRepository.delete(targetSchedule);
  }

  public List<ScheduleResponse> showThisWeekSchedule(LocalDateTime date) {
    List<Schedule> scheduleThisWeek = scheduleQueryRepository.findAllScheduleThisDayToEndOfWeek(
        date);
    return scheduleThisWeek.stream().map(ScheduleResponse::of).toList();
  }

  public List<ScheduleResponse> search(ScheduleSearchServiceRequest request) {
    List<Schedule> search = scheduleQueryRepository.search(request.getContext(),
        request.getScheduleStatus(), request.getTargetDate(), request.getStartDate());
    return search.stream().map(ScheduleResponse::of).toList();
  }

  public void calculateScheduleStatus(List<Schedule> schedules,LocalDateTime readDate) {
    for (Schedule schedule : schedules) {
      schedule.calculateScheduleType(readDate);
    }
  }
}
