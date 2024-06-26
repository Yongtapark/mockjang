package myproject.mockjang.api.service.mockjang.cow.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CowRemoveServiceRequest {
    private Long cowId;

    @Builder
    private CowRemoveServiceRequest(Long cowId) {
        this.cowId = cowId;
    }
}
