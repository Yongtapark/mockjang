package myproject.mockjang.api.controller.mockjang.cow;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.mockjang.cow.request.CowCreateRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRegisterParentsRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRegisterUnitPriceRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRemoveParentsRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowUpdateCowStatusRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowUpdatePenRequest;
import myproject.mockjang.api.service.mockjang.cow.CowService;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/cows")
public class CowController {
    //컨트롤러 완성 ok
    // 테스트 코드 완성 ok
    // docs 완성

    private final CowService cowService;

    @PostMapping("/new")
    public ApiResponse<CowResponse> create(@Valid @RequestBody CowCreateRequest request) {
        return ApiResponse.ok(cowService.createRaisingCow(request.toServiceRequest()));
    }

    @PostMapping("/register/parents")
    public ApiResponse<Void> registerParents(@Valid @RequestBody CowRegisterParentsRequest request) {
        cowService.registerParents(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @PostMapping("/register/price")
    public ApiResponse<Void> registerUnitPrice(@Valid @RequestBody CowRegisterUnitPriceRequest request) {
        cowService.registerUnitPrice(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @PostMapping("/update/pen")
    public ApiResponse<Void> updatePen(@Valid @RequestBody CowUpdatePenRequest request) {
        cowService.updatePen(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @PostMapping("/update/status")
    public ApiResponse<Void> updateCowStatus(@Valid @RequestBody CowUpdateCowStatusRequest request) {
        cowService.updateCowStatus(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @PostMapping("/remove/parents")
    public ApiResponse<Void> removeParents(@Valid @RequestBody CowRemoveParentsRequest request) {
        cowService.removeParents(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id) {
        cowService.remove(id);
        return ApiResponse.noContent();
    }

    @GetMapping
    public ApiResponse<List<CowResponse>> findAll() {
        return ApiResponse.ok(cowService.findAll());
    }

    @GetMapping("/{codeId}")
    public ApiResponse<CowResponse> findByCodeId(@PathVariable String codeId) {
        return ApiResponse.ok(cowService.findByCodeId(codeId));
    }
}
