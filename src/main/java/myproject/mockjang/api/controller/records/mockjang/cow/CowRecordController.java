package myproject.mockjang.api.controller.records.mockjang.cow;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordFindAllByCodeIdAndRecordTypeRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordRemoveRequest;
import myproject.mockjang.api.service.records.mockjang.cow.CowRecordService;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CowRecordController {

    private final CowRecordService cowRecordService;

    @PostMapping("/api/v0/records/cow/new")
    public ApiResponse<CowRecordResponse> create(CowRecordCreateRequest request) {
        return ApiResponse.ok(cowRecordService.create(request.toServiceRequest()));
    }

    @GetMapping("/api/v0/records/cow/{codeId}")
    public ApiResponse<List<CowRecordResponse>> findAllByCodeId(@PathVariable String codeId) {
        return ApiResponse.ok(cowRecordService.findAllByCodeId(codeId));
    }

    @PostMapping("/api/v0/records/cow/remove")
    public ApiResponse<CowRecordResponse> remove(@Valid @RequestBody CowRecordRemoveRequest request) {
        cowRecordService.remove(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @GetMapping("/api/v0/records/cow/{codeId}/{recordType}")
    public ApiResponse<List<CowRecordResponse>> findAllByCodeIdWhereRecordType(
            @Valid @PathVariable String codeId, @Valid @PathVariable RecordType recordType) {
        return ApiResponse.ok(cowRecordService.findAllByCodeIdWhereRecordType(
                CowRecordFindAllByCodeIdAndRecordTypeRequest.builder().cowCode(codeId)
                        .recordType(recordType).build()
                        .toServiceRequest()));
    }

}
