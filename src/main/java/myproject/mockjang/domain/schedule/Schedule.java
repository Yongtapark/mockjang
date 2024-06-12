package myproject.mockjang.domain.schedule;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.AuditingEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE schedule SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Schedule extends AuditingEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime startDate;

  private LocalDateTime targetDate;

  private String context;

  private ScheduleStatus scheduleStatus;

  private boolean deleted = false;

  @Builder
  private Schedule(Long id, LocalDateTime startDate, LocalDateTime targetDate, String context,
      ScheduleStatus scheduleStatus) {
    this.id = id;
    this.startDate = startDate;
    this.targetDate = targetDate;
    this.context = context;
    this.scheduleStatus = scheduleStatus;
  }

  private void registerScheduleStatus(ScheduleStatus scheduleStatus) {
    this.scheduleStatus = scheduleStatus;
  }

  public void calculateScheduleType(LocalDateTime readDate) {
    if (startDate == null) {
      compareWithNoStart(targetDate, readDate);
      return;
    }
    compareWithPeriod(startDate,targetDate, readDate);
  }

  private void compareWithNoStart(LocalDateTime targetDate, LocalDateTime readDate) {
    if (targetDate.isAfter(readDate)) {
      registerScheduleStatus(ScheduleStatus.UPCOMING);
      return;
    }
    if (targetDate.equals(readDate)) {
      registerScheduleStatus(ScheduleStatus.IN_PROGRESS);
      return;
    }
    registerScheduleStatus(ScheduleStatus.EXPIRED);
  }

  private void compareWithPeriod(LocalDateTime startDate,LocalDateTime targetDate, LocalDateTime readDate) {
    if (startDate.isAfter(readDate)) {
      registerScheduleStatus(ScheduleStatus.UPCOMING);
      return;
    }
    if (startDate.isBefore(readDate) && (targetDate.isAfter(readDate) || targetDate.equals(readDate))) {
      registerScheduleStatus(ScheduleStatus.IN_PROGRESS);
      return;
    }
    registerScheduleStatus(ScheduleStatus.EXPIRED);
  }
}
