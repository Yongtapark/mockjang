package myproject.mockjang.api.controller.schedule.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.schedule.request.ScheduleCreateServiceRequest;
import myproject.mockjang.domain.schedule.ScheduleStatus;

@Getter
@NoArgsConstructor
public class ScheduleCreateRequest {
  private LocalDateTime startDate;
  private LocalDateTime targetDate;
  private String context;

  @Builder
  private ScheduleCreateRequest(LocalDateTime startDate, LocalDateTime targetDate, String context) {
    this.startDate = startDate;
    this.targetDate = targetDate;
    this.context = context;
  }

  public ScheduleCreateServiceRequest toServiceRequest() {
    return ScheduleCreateServiceRequest.builder()
        .startDate(startDate)
        .targetDate(targetDate)
        .context(context)
        .build();
  }
}
