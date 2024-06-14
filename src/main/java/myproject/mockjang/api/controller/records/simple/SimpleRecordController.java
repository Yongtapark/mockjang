package myproject.mockjang.api.controller.records.simple;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.records.simple.request.SimpleRecordCreateRequest;
import myproject.mockjang.api.controller.records.simple.request.SimpleRecordSearchRequest;
import myproject.mockjang.api.controller.records.simple.request.SimpleRecordUpdateRequest;
import myproject.mockjang.api.service.records.simple.SimpleRecordService;
import myproject.mockjang.api.service.records.simple.response.SimpleRecordResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SimpleRecordController {

    private final SimpleRecordService simpleRecordService;

    @PostMapping("/api/v0/records/simple/new")
    public ApiResponse<SimpleRecordResponse> create(
            @Valid @RequestBody SimpleRecordCreateRequest request) {
        return ApiResponse.ok(simpleRecordService.create(request.toServiceRequest()));
    }

    @GetMapping("/api/v0/records/simple/{id}")
    public ApiResponse<SimpleRecordResponse> findSimpleRecordById(@PathVariable Long id) {
        return ApiResponse.ok(simpleRecordService.findSimpleRecordById(id));
    }

    @PostMapping("/api/v0/records/simple/update")
    public ApiResponse<Void> update(@Valid @RequestBody SimpleRecordUpdateRequest request) {
        simpleRecordService.update(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @PostMapping("/api/v0/records/simple")
    public ApiResponse<List<SimpleRecordResponse>> search(@Valid @RequestBody SimpleRecordSearchRequest request) {
        return ApiResponse.ok(simpleRecordService.search(request.toServiceRequest()));
    }

    @GetMapping("/api/v0/records/simple/codeids")
    public ApiResponse<List<String>> getAutoCompleteList() {
        return ApiResponse.ok(simpleRecordService.findAllCodeIdWithDistinct());
    }

    @DeleteMapping("/api/v0/records/simple/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id) {
        simpleRecordService.remove(id);
        return ApiResponse.noContent();
    }
}
