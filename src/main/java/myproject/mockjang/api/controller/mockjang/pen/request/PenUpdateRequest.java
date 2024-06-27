package myproject.mockjang.api.controller.mockjang.pen.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.pen.request.PenUpdateServiceRequest;

@Getter
@NoArgsConstructor
public class PenUpdateRequest {
    private Long penId;
    private Long barnId;
    private String codeId;


    @Builder
    public PenUpdateRequest(Long penId, Long barnId, String codeId) {
        this.penId = penId;
        this.barnId = barnId;
        this.codeId = codeId;

    }

    public PenUpdateServiceRequest toServiceRequest() {
        return PenUpdateServiceRequest.builder().penId(penId).barnId(barnId).codeId(codeId).build();
    }
}
