package myproject.mockjang.api.service.mockjang.cow.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CowChangePenServiceRequest {
    private Long cowId;
    private Long penId;

    @Builder
    private CowChangePenServiceRequest(Long cowId, Long penId) {
        this.cowId = cowId;
        this.penId = penId;
    }
}
