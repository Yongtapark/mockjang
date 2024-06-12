package myproject.mockjang.domain.schedule;

import lombok.Getter;

@Getter
public enum ScheduleStatus {
  UPCOMING("예정"),
  IN_PROGRESS("진행중"),
  EXPIRED("만료");

  private final String description;

  ScheduleStatus(String description) {
    this.description = description;
  }
}
