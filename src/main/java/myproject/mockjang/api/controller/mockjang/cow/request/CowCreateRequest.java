package myproject.mockjang.api.controller.mockjang.cow.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import myproject.mockjang.api.service.mockjang.cow.request.CowCreateServiceRequest;
import myproject.mockjang.domain.mockjang.cow.Gender;

@Getter
public class CowCreateRequest {

  @NotBlank(message = "소 이름은 공백일 수 없습니다.")
  private String cowCode;
  @NotNull(message = "소 성별 공백일 수 없습니다.")
  private Gender gender;
  @NotBlank(message = "축사칸 이름은 공백일 수 없습니다.")
  private String penCode;
  @NotNull(message = "소 생일은 공백일 수 없습니다.")
  private LocalDateTime birthDate;

  @Builder
  private CowCreateRequest(String cowCode, Gender gender, String penCode, LocalDateTime birthDate) {
    this.cowCode = cowCode;
    this.gender = gender;
    this.penCode = penCode;
    this.birthDate = birthDate;
  }

  public CowCreateServiceRequest toServiceRequest() {
    return CowCreateServiceRequest.builder()
        .cowCode(cowCode)
        .penCode(penCode)
        .birthDate(birthDate)
        .gender(gender)
        .build();
  }
}
