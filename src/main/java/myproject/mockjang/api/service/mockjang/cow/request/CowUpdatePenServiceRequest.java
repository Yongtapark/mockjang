package myproject.mockjang.api.service.mockjang.cow.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CowUpdatePenServiceRequest {
    private Long cowId;
    private Long penId;

    @Builder
    private CowUpdatePenServiceRequest(Long cowId, Long penId) {
        this.cowId = cowId;
        this.penId = penId;
    }
}
