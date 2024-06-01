package myproject.mockjang.api.controller.records.mockjang.cow.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordRemoveServiceRequest;

@Getter
@NoArgsConstructor
public class CowRecordRemoveRequest {
  @NotNull(message = "고유번호를 찾을 수 없습니다.")
  private Long id;

  @Builder
  private CowRecordRemoveRequest(Long id) {
    this.id = id;
  }

  public CowRecordRemoveServiceRequest toServiceRequest(){
    return CowRecordRemoveServiceRequest.builder().id(id).build();
  }
}
