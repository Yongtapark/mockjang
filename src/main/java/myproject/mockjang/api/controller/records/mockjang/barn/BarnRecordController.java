package myproject.mockjang.api.controller.records.mockjang.barn;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.records.mockjang.barn.request.BarnRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.barn.request.BarnRecordSearchRequest;
import myproject.mockjang.api.controller.records.mockjang.barn.request.BarnRecordUpdateRequest;
import myproject.mockjang.api.service.records.mockjang.barn.BarnRecordService;
import myproject.mockjang.api.service.records.mockjang.barn.response.BarnRecordResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/records/barn")
public class BarnRecordController {

    private final BarnRecordService barnRecordService;

    @PostMapping("/new")
    public ApiResponse<Long> create(BarnRecordCreateRequest request) {
        return ApiResponse.ok(barnRecordService.create(request.toServiceRequest()));
    }

    @PostMapping("/{codeId}")
    public ApiResponse<List<BarnRecordResponse>> search(@Valid @RequestBody BarnRecordSearchRequest request) {
        return ApiResponse.ok(barnRecordService.search(request.toServiceRequest()));
    }

    @PutMapping("/update")
    public ApiResponse<Void> update(@Valid @RequestBody BarnRecordUpdateRequest request) {
        barnRecordService.update(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id) {
        barnRecordService.remove(id);
        return ApiResponse.noContent();
    }

}
