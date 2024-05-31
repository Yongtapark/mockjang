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
  private String cowId;
  @NotNull(message = "소 성별 공백일 수 없습니다.")
  private Gender gender;
  @NotBlank(message = "축사칸 이름은 공백일 수 없습니다.")
  private String penId;
  @NotNull(message = "소 생일은 공백일 수 없습니다.")
  private LocalDateTime birthDate;

  @Builder
  private CowCreateRequest(String cowId, Gender gender, String penId, LocalDateTime birthDate) {
    this.cowId = cowId;
    this.gender = gender;
    this.penId = penId;
    this.birthDate = birthDate;
  }

  public CowCreateServiceRequest toServiceRequest() {
    return CowCreateServiceRequest.builder()
        .cowId(cowId)
        .penId(penId)
        .birthDate(birthDate)
        .gender(gender)
        .build();
  }
}
