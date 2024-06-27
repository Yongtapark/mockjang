package myproject.mockjang.api.controller.mockjang.pen.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.pen.request.PenCreateServiceRequest;

@Getter
@NoArgsConstructor
public class PenCreateRequest {

    @NotBlank(message = "{exception.pen.codeId.blank}")
    private String penCodeId;

    @NotBlank(message = "{exception.barn.codeId.blank}")
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
