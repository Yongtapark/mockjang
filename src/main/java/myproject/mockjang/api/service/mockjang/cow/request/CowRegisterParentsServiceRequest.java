package myproject.mockjang.api.service.mockjang.cow.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Cow;

@Getter
@NoArgsConstructor
public class CowRegisterParentsServiceRequest {
    private Cow cow;
    private List<Cow> parents;

    @Builder
    private CowRegisterParentsServiceRequest(Cow cow, List<Cow> parents) {
        this.cow = cow;
        this.parents = parents;
    }
}
