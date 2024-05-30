package myproject.mockjang.api.service.mockjang.pen.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PenCreateServiceRequest {
  private String penCodeId;
  private String barnCodeId;

  @Builder
  private PenCreateServiceRequest(String penCodeId, String barnCodeId) {
    this.penCodeId = penCodeId;
    this.barnCodeId = barnCodeId;
  }


}
