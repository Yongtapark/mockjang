package myproject.mockjang.api.controller.mockjang.cow.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterParentsServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowUpdateCowStatusServiceRequest;
import myproject.mockjang.domain.mockjang.cow.CowStatus;

@NoArgsConstructor
@Getter
public class CowUpdateCowStatusRequest {
    @NotNull(message = "{exception.id.null}")
    private Long cowId;
    @NotNull(message = "{exception.cow.cowStatus.null}")
    private CowStatus cowStatus;

    @Builder
    private CowUpdateCowStatusRequest(Long cowId, CowStatus cowStatus) {
        this.cowId = cowId;
        this.cowStatus = cowStatus;
    }

    public CowUpdateCowStatusServiceRequest toServiceRequest(){
        return CowUpdateCowStatusServiceRequest.builder()
                .cowId(cowId)
                .cowStatus(cowStatus)
                .build();
    }
}
