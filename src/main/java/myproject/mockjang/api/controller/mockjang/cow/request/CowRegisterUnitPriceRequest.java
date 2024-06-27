package myproject.mockjang.api.controller.mockjang.cow.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterUnitPriceServiceRequest;

@NoArgsConstructor
@Getter
public class CowRegisterUnitPriceRequest {
    @NotNull(message = "{exception.id.null}")
    private Long cowId;
    @NotNull(message = "{exception.cow.price.null}")
    private Integer unitPrice;

    @Builder
    private CowRegisterUnitPriceRequest(Long cowId, Integer unitPrice) {
        this.cowId = cowId;
        this.unitPrice = unitPrice;
    }

    public CowRegisterUnitPriceServiceRequest toServiceRequest() {
        return CowRegisterUnitPriceServiceRequest.builder()
                .cowId(cowId)
                .unitPrice(unitPrice)
                .build();
    }
}
