package myproject.mockjang.api.controller.mockjang.cow;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.mockjang.cow.request.CowCreateRequest;
import myproject.mockjang.api.controller.mockjang.pen.request.PenCreateRequest;
import myproject.mockjang.api.service.mockjang.cow.CowService;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CowController {

  private final CowService cowService;

  @PostMapping("/api/v0/cows/new")
  public ApiResponse<CowResponse> createPen(@Valid @RequestBody CowCreateRequest request){
    return ApiResponse.ok(cowService.createRaisingCow(request.toServiceRequest()));
  }

  @GetMapping("/api/v0/cows")
  public ApiResponse<List<CowResponse>> createBarn(){
    return ApiResponse.ok(cowService.findAll());
  }

  @GetMapping("/api/v0/cows/{codeId}")
  public ApiResponse<CowResponse> findByCodeId(@PathVariable String codeId){
    return ApiResponse.ok(cowService.findByCodeId(codeId));
  }
}
