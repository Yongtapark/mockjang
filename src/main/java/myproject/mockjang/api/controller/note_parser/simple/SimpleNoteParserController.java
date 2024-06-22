package myproject.mockjang.api.controller.note_parser.simple;

import jakarta.validation.Valid;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.note_parser.simple.request.SimpleNoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.api.service.note_parser.simple.SimpleNoteParserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SimpleNoteParserController {

  private final SimpleNoteParserService noteParserService;

  @PostMapping("/api/v0/simple-parser/new")
  public ApiResponse<RecordParserResponse> createRecords(
      @Valid @RequestBody SimpleNoteParserCreateRequest request) {
    return ApiResponse.ok(
        noteParserService.parseNoteAndSaveRecord(request.toServiceRequest(new HashMap<>())));
  }

}
