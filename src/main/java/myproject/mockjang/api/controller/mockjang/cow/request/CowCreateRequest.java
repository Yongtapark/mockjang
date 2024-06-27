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

  @NotBlank(message = "{exception.cow.codId.blank}")
  private String cowCode;
  @NotNull(message = "{exception.cow.gender.null}")
  private Gender gender;
  @NotBlank(message = "{exception.pen.codId.blank}")
  private String penCode;
  @NotNull(message = "{exception.cow.date.null}")
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
