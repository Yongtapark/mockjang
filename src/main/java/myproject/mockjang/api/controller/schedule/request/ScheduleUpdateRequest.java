package myproject.mockjang.api.controller.schedule.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.schedule.request.ScheduleUpdateServiceRequest;

@Getter
@NoArgsConstructor
public class ScheduleUpdateRequest {
    @NotNull(message = "{exception.id.null}")
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private LocalDateTime readDate;
    private String context;

    @Builder
    private ScheduleUpdateRequest(Long id, LocalDateTime startDate, LocalDateTime targetDate,
                                  LocalDateTime readDate, String context) {
        this.id = id;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.readDate = readDate;
        this.context = context;
    }

    public ScheduleUpdateServiceRequest toServiceRequest() {
        return ScheduleUpdateServiceRequest.builder()
                .id(id)
                .startDate(startDate)
                .targetDate(targetDate)
                .readDate(readDate)
                .context(context)
                .build();
    }
}
