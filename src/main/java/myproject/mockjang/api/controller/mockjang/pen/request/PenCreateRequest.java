package myproject.mockjang.api.controller.mockjang.pen.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.pen.request.PenCreateServiceRequest;

@Getter
@NoArgsConstructor
public class PenCreateRequest {

  @NotBlank(message = "축사칸명은 공백일 수 없습니다.")
  private String penCodeId;

  @NotBlank(message = "축사명은 공백일 수 없습니다.")
  private String barnCodeId;

  @Builder
  public PenCreateRequest(String penCodeId, String barnCodeId) {
    this.penCodeId = penCodeId;
    this.barnCodeId = barnCodeId;
  }

  public PenCreateServiceRequest toServiceRequest() {
    return PenCreateServiceRequest.builder().penCodeId(penCodeId).barnCodeId(barnCodeId).build();
  }
}
