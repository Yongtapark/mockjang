package myproject.mockjang.api.service.schedule.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleCreateServiceRequest {

    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private String context;

    @Builder
    private ScheduleCreateServiceRequest(LocalDateTime startDate, LocalDateTime targetDate, String context) {
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.context = context;
    }
}
