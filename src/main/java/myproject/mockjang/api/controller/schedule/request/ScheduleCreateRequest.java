package myproject.mockjang.api.controller.schedule.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotNull(message = "{exception.date.null}")
  private LocalDateTime targetDate;

  @NotBlank(message = "{exception.context.blank}")
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
