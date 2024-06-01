package myproject.mockjang.api.controller.records.simple_record.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordRemoveServiceRequest;

@Getter
@NoArgsConstructor
public class SimpleRecordRemoveRequest {
  @NotNull(message = "{exception.id.null}")
  private Long id;

  @Builder
  private SimpleRecordRemoveRequest(Long id) {
    this.id = id;
  }

  public SimpleRecordRemoveServiceRequest toServiceRequest() {
    return SimpleRecordRemoveServiceRequest.builder()
        .id(id)
        .build();
  }
}
