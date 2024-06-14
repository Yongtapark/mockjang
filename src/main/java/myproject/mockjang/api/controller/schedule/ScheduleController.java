package myproject.mockjang.api.controller.schedule;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.schedule.request.ScheduleCreateRequest;
import myproject.mockjang.api.controller.schedule.request.ScheduleSearchRequest;
import myproject.mockjang.api.controller.schedule.request.ScheduleUpdateRequest;
import myproject.mockjang.api.service.schedule.ScheduleService;
import myproject.mockjang.api.service.schedule.reponse.ScheduleResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v0/schedule")
public class ScheduleController {

  private final ScheduleService scheduleService;

  @GetMapping("/{id}")
  public ApiResponse<ScheduleResponse> findScheduleById(@PathVariable Long id) {
    return ApiResponse.ok(scheduleService.findScheduleById(id));
  }

  @GetMapping("/weeks/{date}")
  public ApiResponse<List<ScheduleResponse>> showThisWeekScheduleFromToday(@PathVariable LocalDateTime date) {
    return ApiResponse.ok(scheduleService.showThisWeekScheduleFromToday(date));
  }

  @PostMapping("/new")
  public ApiResponse<ScheduleResponse> create(@Valid @RequestBody ScheduleCreateRequest request) {
    return ApiResponse.ok(scheduleService.create(request.toServiceRequest()));
  }

  @PostMapping
  public ApiResponse<List<ScheduleResponse>> search(
      @Valid @RequestBody ScheduleSearchRequest request) {
    return ApiResponse.ok(scheduleService.search(request.toServiceRequest()));
  }

  @PostMapping("/update")
  public ApiResponse<ScheduleResponse> update(@Valid @RequestBody ScheduleUpdateRequest request) {
    scheduleService.update(request.toServiceRequest());
    return ApiResponse.noContent();
  }

  @DeleteMapping("{id}")
  public ApiResponse<Void> remove(@PathVariable Long id) {
    scheduleService.remove(id);
    return ApiResponse.noContent();
  }
}
