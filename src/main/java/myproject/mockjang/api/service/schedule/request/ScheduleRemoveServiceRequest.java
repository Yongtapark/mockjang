package myproject.mockjang.api.service.schedule.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRemoveServiceRequest {

  private Long id;

  @Builder
  private ScheduleRemoveServiceRequest(Long id) {
    this.id = id;
  }
}
