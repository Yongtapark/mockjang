package myproject.mockjang.api.controller.mockjang.pen;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.mockjang.pen.request.PenCreateRequest;
import myproject.mockjang.api.service.mockjang.pen.PenService;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PenController {

  private final PenService penService;

  @PostMapping("/api/v0/pens/new")
  public ApiResponse<PenResponse> create(@Valid @RequestBody PenCreateRequest request){
    return ApiResponse.ok(penService.createPen(request.toServiceRequest()));
  }

  @GetMapping("/api/v0/pens")
  public ApiResponse<List<PenResponse>> findAll(){
    return ApiResponse.ok(penService.findAll());
  }

  @GetMapping("/api/v0/pens/{codeId}")
  public ApiResponse<PenResponse> findByCodeId(@PathVariable String codeId){
    return ApiResponse.ok(penService.findByCodeId(codeId));
  }

}
