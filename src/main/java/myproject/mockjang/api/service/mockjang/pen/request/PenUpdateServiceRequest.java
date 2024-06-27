package myproject.mockjang.api.service.mockjang.pen.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PenUpdateServiceRequest {
    private Long penId;
    private Long barnId;
    private String codeId;

    @Builder
    private PenUpdateServiceRequest(Long barnId, String codeId, Long penId) {
        this.barnId = barnId;
        this.penId = penId;
        this.codeId = codeId;
    }
}
