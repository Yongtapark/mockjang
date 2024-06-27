package myproject.mockjang.api.controller.mockjang.barn;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.mockjang.barn.request.BarnCreateRequest;
import myproject.mockjang.api.controller.mockjang.barn.request.BarnUpdateRequest;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.api.service.mockjang.barn.response.BarnResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/barns")
public class BarnController {

    private final BarnService barnService;

    @PostMapping("/new")
    public ApiResponse<Long> create(@Valid @RequestBody BarnCreateRequest request) {
        return ApiResponse.ok(barnService.create(request.toServiceRequest()));
    }

    @GetMapping
    public ApiResponse<List<BarnResponse>> findAll() {
        return ApiResponse.ok(barnService.findAll());
    }

    @GetMapping("/{codeId}")
    public ApiResponse<BarnResponse> findByCodeId(@PathVariable String codeId) {
        return ApiResponse.ok(barnService.findByCodeId(codeId));
    }

    @PutMapping("/update")
    public ApiResponse<BarnResponse> update(@Valid @RequestBody BarnUpdateRequest request) {
        barnService.update(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<BarnResponse> update(@PathVariable Long id) {
        barnService.remove(id);
        return ApiResponse.noContent();
    }
}
