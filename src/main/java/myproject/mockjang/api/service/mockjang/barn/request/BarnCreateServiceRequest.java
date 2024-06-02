package myproject.mockjang.api.service.mockjang.barn.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BarnCreateServiceRequest {

  private String codeId;

  @Builder
  private BarnCreateServiceRequest(String codeId) {
    this.codeId = codeId;
  }


}
