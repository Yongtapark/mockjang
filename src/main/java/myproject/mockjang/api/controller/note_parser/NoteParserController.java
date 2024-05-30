package myproject.mockjang.api.controller.note_parser;

import jakarta.validation.Valid;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.ApiResponse;
import myproject.mockjang.api.controller.note_parser.request.NoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.NoteParserService;
import myproject.mockjang.api.service.note_parser.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.response.NoteParserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NoteParserController {

  private final NoteParserService noteParserService;

  @PostMapping("/api/v0/notes/daily/new")
  public ApiResponse<NoteParserResponse> createNote(@Valid @RequestBody NoteParserCreateRequest request) {
    return ApiResponse.ok(noteParserService.parseNoteAndSaveRecord(request.toServiceRequest(new HashMap<>())));
  }

}
