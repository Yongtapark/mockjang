package myproject.mockjang.api.service.mockjang.cow.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.CowStatus;

@Getter
@NoArgsConstructor
public class CowChangeCowStatusServiceRequest {
    private Long cowId;
    private CowStatus cowStatus;

    @Builder
    private CowChangeCowStatusServiceRequest(Long cowId, CowStatus cowStatus) {
        this.cowId = cowId;
        this.cowStatus = cowStatus;
    }
}
