package myproject.mockjang.api.service.mockjang.barn.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BarnUpdateServiceRequest {
    private Long id;
    private String codeId;

    @Builder
    private BarnUpdateServiceRequest(String codeId, Long id) {
        this.codeId = codeId;
        this.id = id;
    }
}
