package myproject.mockjang.api.service.schedule.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.schedule.ScheduleStatus;

@Getter
@NoArgsConstructor
public class ScheduleUpdateServiceRequest {

  private Long id;
  private LocalDateTime startDate;
  private LocalDateTime targetDate;
  private String context;
  private ScheduleStatus scheduleStatus;

  @Builder
  private ScheduleUpdateServiceRequest(Long id, LocalDateTime startDate, LocalDateTime targetDate,
      String context, ScheduleStatus scheduleStatus) {
    this.id = id;
    this.startDate = startDate;
    this.targetDate = targetDate;
    this.context = context;
    this.scheduleStatus = scheduleStatus;
  }
}
