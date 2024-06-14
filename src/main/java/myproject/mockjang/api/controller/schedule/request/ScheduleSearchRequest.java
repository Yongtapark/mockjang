package myproject.mockjang.api.controller.schedule.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.schedule.request.ScheduleSearchServiceRequest;
import myproject.mockjang.domain.schedule.ScheduleStatus;

@Getter
@NoArgsConstructor
public class ScheduleSearchRequest {
  private LocalDateTime startDate;
  private LocalDateTime targetDate;
  private String context;
  private ScheduleStatus scheduleStatus;

  @Builder
  private ScheduleSearchRequest(LocalDateTime startDate, LocalDateTime targetDate, String context,
      ScheduleStatus scheduleStatus) {
    this.startDate = startDate;
    this.targetDate = targetDate;
    this.context = context;
    this.scheduleStatus = scheduleStatus;
  }

  public ScheduleSearchServiceRequest toServiceRequest() {
    return ScheduleSearchServiceRequest.builder()
        .startDate(startDate)
        .targetDate(targetDate)
        .context(context)
        .scheduleStatus(scheduleStatus)
        .build();
  }
}
