package myproject.mockjang.api.controller.mockjang.cow.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterParentsServiceRequest;

@NoArgsConstructor
@Getter
public class CowRegisterParentsRequest {
    @NotNull(message = "{exception.id.null}")
    private Long cowId;
    @NotNull(message = "{exception.id.null}")
    @Size(min = 1, max = 2, message = "{exception.cow.parents.size}")
    private List<Long> parents;

    @Builder
    private CowRegisterParentsRequest(Long cowId, List<Long> parents) {
        this.cowId = cowId;
        this.parents = parents;
    }

    public CowRegisterParentsServiceRequest toServiceRequest() {
        return CowRegisterParentsServiceRequest.builder()
                .cowId(cowId)
                .parentsIds(parents)
                .build();
    }
}
