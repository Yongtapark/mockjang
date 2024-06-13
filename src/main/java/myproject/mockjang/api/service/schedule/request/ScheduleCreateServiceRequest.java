package myproject.mockjang.api.service.schedule.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.schedule.ScheduleStatus;

@Getter
@NoArgsConstructor
public class ScheduleCreateServiceRequest {
  private LocalDateTime startDate;
  private LocalDateTime targetDate;
  private String context;
  private ScheduleStatus scheduleStatus;

  @Builder
  private ScheduleCreateServiceRequest(LocalDateTime startDate, LocalDateTime targetDate,
      String context, ScheduleStatus scheduleStatus) {
    this.startDate = startDate;
    this.targetDate = targetDate;
    this.context = context;
    this.scheduleStatus = scheduleStatus;
  }
}
