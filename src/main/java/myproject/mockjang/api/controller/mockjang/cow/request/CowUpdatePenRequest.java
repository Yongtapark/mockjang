package myproject.mockjang.api.controller.mockjang.cow.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.cow.request.CowUpdatePenServiceRequest;

@NoArgsConstructor
@Getter
public class CowUpdatePenRequest {
    @NotNull(message = "{exception.id.null}")
    private Long cowId;
    @NotNull(message = "{exception.id.null}")
    private Long penId;

    @Builder
    private CowUpdatePenRequest(Long cowId, Long penId) {
        this.cowId = cowId;
        this.penId = penId;
    }

    public CowUpdatePenServiceRequest toServiceRequest(){
        return CowUpdatePenServiceRequest.builder()
                .cowId(cowId)
                .penId(penId)
                .build();
    }
}
