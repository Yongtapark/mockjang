package myproject.mockjang.api.service.mockjang.cow.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CowRemoveParentsServiceRequest {
    private Long cowId;
    private List<Long> parentsIds;

    @Builder
    private CowRemoveParentsServiceRequest(Long cowId, List<Long> parentsIds) {
        this.cowId = cowId;
        this.parentsIds = parentsIds;
    }
}
