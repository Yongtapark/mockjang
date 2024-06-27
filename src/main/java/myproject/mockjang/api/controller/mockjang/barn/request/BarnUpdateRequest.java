package myproject.mockjang.api.controller.mockjang.barn.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.barn.request.BarnUpdateServiceRequest;

@Getter
@NoArgsConstructor
public class BarnUpdateRequest {

    @NotNull(message = "{exception.id.null}")
    private Long id;
    @NotBlank(message = "{exception.codeId.blank}")
    private String codeId;

    @Builder
    public BarnUpdateRequest(Long id, String codeId) {
        this.id = id;
        this.codeId = codeId;
    }

    public BarnUpdateServiceRequest toServiceRequest() {
        return BarnUpdateServiceRequest.builder().id(id).codeId(codeId).build();
    }
}
