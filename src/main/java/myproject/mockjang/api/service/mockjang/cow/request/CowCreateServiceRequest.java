package myproject.mockjang.api.service.mockjang.cow.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;

@Getter
@NoArgsConstructor
public class CowCreateServiceRequest {
  private String cowId;
  private Gender gender;
  private String penId;
  private LocalDateTime birthDate;

  @Builder
  public CowCreateServiceRequest(String cowId, Gender gender, String penId,
      LocalDateTime birthDate) {
    this.cowId = cowId;
    this.gender = gender;
    this.penId = penId;
    this.birthDate = birthDate;
  }
}
