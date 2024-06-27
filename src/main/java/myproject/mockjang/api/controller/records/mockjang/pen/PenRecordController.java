package myproject.mockjang.api.controller.records.mockjang.pen;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.records.mockjang.pen.request.PenRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.pen.request.PenRecordSearchRequest;
import myproject.mockjang.api.controller.records.mockjang.pen.request.PenRecordUpdateRequest;
import myproject.mockjang.api.service.records.mockjang.pen.PenRecordService;
import myproject.mockjang.api.service.records.mockjang.pen.response.PenRecordResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/records/pen")
public class PenRecordController {

    private final PenRecordService penRecordService;

    @PostMapping("/new")
    public ApiResponse<Long> create(PenRecordCreateRequest request) {
        return ApiResponse.ok(penRecordService.create(request.toServiceRequest()));
    }

    @PostMapping
    public ApiResponse<List<PenRecordResponse>> search(@Valid @RequestBody PenRecordSearchRequest request) {
        return ApiResponse.ok(penRecordService.search(request.toServiceRequest()));
    }

    @PutMapping("/update")
    public ApiResponse<Void> update(@Valid @RequestBody PenRecordUpdateRequest request) {
        penRecordService.update(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id) {
        penRecordService.remove(id);
        return ApiResponse.noContent();
    }

}
