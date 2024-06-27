package myproject.mockjang.api.controller.mockjang.pen;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.mockjang.pen.request.PenCreateRequest;
import myproject.mockjang.api.controller.mockjang.pen.request.PenUpdateRequest;
import myproject.mockjang.api.service.mockjang.pen.PenService;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
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
@RequestMapping("/api/v0/pens")
public class PenController {

    private final PenService penService;

    @PostMapping("/new")
    public ApiResponse<Long> create(@Valid @RequestBody PenCreateRequest request) {
        return ApiResponse.ok(penService.createPen(request.toServiceRequest()));
    }

    @GetMapping
    public ApiResponse<List<PenResponse>> findAll() {
        return ApiResponse.ok(penService.findAll());
    }

    @GetMapping("/{codeId}")
    public ApiResponse<PenResponse> findByCodeId(@PathVariable String codeId) {
        return ApiResponse.ok(penService.findByCodeId(codeId));
    }

    @PutMapping("/update")
    public ApiResponse<Void> update(@Valid @RequestBody PenUpdateRequest request){
        penService.update(request.toServiceRequest());
        return ApiResponse.noContent();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> remove(@PathVariable Long id){
        penService.delete(id);
        return ApiResponse.noContent();
    }

}
