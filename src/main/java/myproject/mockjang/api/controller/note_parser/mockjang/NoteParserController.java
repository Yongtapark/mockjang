package myproject.mockjang.api.controller.note_parser.mockjang;

import jakarta.validation.Valid;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.note_parser.mockjang.request.NoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.mockjang.NoteParserService;
import myproject.mockjang.api.service.note_parser.mockjang.response.NoteParserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoteParserController {

  private final NoteParserService noteParserService;

  @PostMapping("/api/v0/records/new")
  public ApiResponse<NoteParserResponse> createRecords(@Valid @RequestBody NoteParserCreateRequest request) {
    return ApiResponse.ok(noteParserService.parseNoteAndSaveRecord(request.toServiceRequest(new HashMap<>())));
  }

}
