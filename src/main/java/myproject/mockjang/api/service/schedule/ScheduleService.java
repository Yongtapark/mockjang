package myproject.mockjang.api.service.schedule;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.schedule.reponse.ScheduleResponse;
import myproject.mockjang.api.service.schedule.request.ScheduleCreateServiceRequest;
import myproject.mockjang.api.service.schedule.request.ScheduleSearchServiceRequest;
import myproject.mockjang.api.service.schedule.request.ScheduleUpdateServiceRequest;
import myproject.mockjang.domain.schedule.Schedule;
import myproject.mockjang.domain.schedule.ScheduleQueryRepository;
import myproject.mockjang.domain.schedule.ScheduleRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.schdules.ScheduleFormException;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final ScheduleQueryRepository scheduleQueryRepository;

  public ScheduleResponse create(ScheduleCreateServiceRequest request) {
    validateSchedule(request.getStartDate(),request.getTargetDate());
    Schedule schedule = Schedule.create(request.getStartDate(), request.getTargetDate(),
        request.getContext());
    scheduleRepository.save(schedule);
    return ScheduleResponse.of(schedule);
  }

  private Schedule findById(Long id) {
    return scheduleRepository.findById(id).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Schedule.class)));
  }

  public ScheduleResponse findScheduleById(Long id) {
    return ScheduleResponse.of(findById(id));
  }

  public void update(ScheduleUpdateServiceRequest request) {
    validateSchedule(request.getStartDate(),request.getTargetDate());
    Schedule targetSchedule = findById(request.getId());
    targetSchedule.update(request.getContext(), request.getStartDate(), request.getTargetDate());
    if (request.getTargetDate() != null || request.getStartDate() != null) {
      calculateScheduleStatus(targetSchedule, request.getReadDate());
    }
    scheduleRepository.save(targetSchedule);
  }

  public void remove(Long id) {
    Schedule targetSchedule = findById(id);
    scheduleRepository.delete(targetSchedule);
  }

  public List<ScheduleResponse> showThisWeekScheduleFromToday(LocalDateTime date) {
    List<Schedule> scheduleThisWeek = scheduleQueryRepository.findAllScheduleThisDayToEndOfWeek(
        date);
    return scheduleThisWeek.stream().map(ScheduleResponse::of).toList();
  }

  public List<ScheduleResponse> search(ScheduleSearchServiceRequest request) {
    List<Schedule> search = scheduleQueryRepository.search(request.getStartDate(),
        request.getTargetDate(), request.getContext(), request.getScheduleStatus());
    return search.stream().map(ScheduleResponse::of).toList();
  }

  public void calculateScheduleStatus(List<Schedule> schedules, LocalDateTime readDate) {
    for (Schedule schedule : schedules) {
      calculateScheduleStatus(schedule, readDate);
    }
  }

  private void calculateScheduleStatus(Schedule schedule, LocalDateTime readDate) {
    schedule.calculateScheduleType(readDate);
  }

  private void validateSchedule(LocalDateTime startDate, LocalDateTime targetTime) {
    if(startDate.isAfter(targetTime)){
      throw new ScheduleFormException(Exceptions.DOMAIN_SCHEDULE_FORM);
    }
  }
}
