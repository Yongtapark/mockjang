package myproject.mockjang.api.controller.mockjang.barn;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.mockjang.barn.request.BarnCreateRequest;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.api.service.mockjang.barn.response.BarnResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BarnController {

  private final BarnService barnService;

  @PostMapping("/api/v0/barns/new")
  public ApiResponse<BarnResponse> create(@Valid @RequestBody BarnCreateRequest request) {
    return ApiResponse.ok(barnService.createBarn(request.toServiceRequest()));
  }

  @GetMapping("/api/v0/barns")
  public ApiResponse<List<BarnResponse>> findAll() {
    return ApiResponse.ok(barnService.findAll());
  }

  @GetMapping("/api/v0/barns/{codeId}")
  public ApiResponse<BarnResponse> findByCodeId(@PathVariable String codeId) {
    return ApiResponse.ok(barnService.findByCodeId(codeId));
  }
}
