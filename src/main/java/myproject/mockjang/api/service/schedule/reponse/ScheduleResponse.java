package myproject.mockjang.api.service.schedule.reponse;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.schedule.Schedule;
import myproject.mockjang.domain.schedule.ScheduleStatus;

@Getter
@NoArgsConstructor
public class ScheduleResponse {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime targetDate;
    private String context;
    private ScheduleStatus scheduleStatus;

    @Builder
    private ScheduleResponse(Long id, LocalDateTime startDate, LocalDateTime targetDate,
                             String context,
                             ScheduleStatus scheduleStatus) {
        this.id = id;
        this.startDate = startDate;
        this.targetDate = targetDate;
        this.context = context;
        this.scheduleStatus = scheduleStatus;
    }

    public static ScheduleResponse of(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .startDate(schedule.getStartDate())
                .targetDate(schedule.getTargetDate())
                .context(schedule.getContext())
                .scheduleStatus(schedule.getScheduleStatus())
                .build();
    }
}
