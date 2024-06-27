package myproject.mockjang.api.controller.records.mockjang.cow;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordSearchRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordUpdateRequest;
import myproject.mockjang.api.service.records.mockjang.cow.CowRecordService;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/records/cow")
public class CowRecordController {

    private final CowRecordService cowRecordService;

    @PostMapping("/new")
    public ApiResponse<Long> create(CowRecordCreateRequest request) {
        return ApiResponse.ok(cowRecordService.create(request.toServiceRequest()));
    }

    @PostMapping
    public ApiResponse<List<CowRecordResponse>> search(@Valid @RequestBody CowRecordSearchRequest request) {
        return ApiResponse.ok(cowRecordService.search(request.toServiceRequest()));
    }

    @PutMapping("/update")
    public ApiResponse<Void> update(@Valid @RequestBody CowRecordUpdateRequest request) {
        cowRecordService.update(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id) {
        cowRecordService.remove(id);
        return ApiResponse.noContent();
    }

}
