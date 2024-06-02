package myproject.mockjang.api.controller.mockjang.barn.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;

@Getter
@NoArgsConstructor
public class BarnCreateRequest {

  @NotBlank(message = "축사명은 공백일 수 없습니다.")
  private String codeId;

  @Builder
  public BarnCreateRequest(String codeId) {
    this.codeId = codeId;
  }

  public BarnCreateServiceRequest toServiceRequest() {
    return BarnCreateServiceRequest.builder().codeId(codeId).build();
  }
}
