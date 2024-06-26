package myproject.mockjang.api.service.mockjang.cow.request;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CowRegisterUnitPriceServiceRequest {
    private Long cowId;
    private Integer unitPrice;

    @Builder
    private CowRegisterUnitPriceServiceRequest(Long cowId, Integer unitPrice) {
        this.cowId = cowId;
        this.unitPrice = unitPrice;
    }
}
