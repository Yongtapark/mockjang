package myproject.mockjang.api.service.mockjang.cow.request;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Gender;

@Getter
@NoArgsConstructor
public class CowCreateServiceRequest {

  private String cowCode;
  private Gender gender;
  private String penCode;
  private LocalDateTime birthDate;

  @Builder
  public CowCreateServiceRequest(String cowCode, Gender gender, String penCode,
      LocalDateTime birthDate) {
    this.cowCode = cowCode;
    this.gender = gender;
    this.penCode = penCode;
    this.birthDate = birthDate;
  }
}
