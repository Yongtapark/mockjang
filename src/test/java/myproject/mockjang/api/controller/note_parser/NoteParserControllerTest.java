package myproject.mockjang.api.controller.note_parser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.HashMap;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.note_parser.mockjang.request.NoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.NoteParserResponse;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class NoteParserControllerTest extends ControllerTestSupport {

  @DisplayName("신규 일지를 저장한다.")
  @Test
  void createRecords() throws Exception {
    // given
    LocalDateTime date = LocalDateTime.of(2024, 5, 31, 00, 00);
    HashMap<String, Integer> names = new HashMap<>();
    names.put(PARSER_BARN_CODE_ID_1, 1);
    NoteParserResponse response = NoteParserResponse.builder().names(names).build();


    String context = "[["+PARSER_BARN_CODE_ID_1+"]] "+PARSER_BARN_NOTE_1;
    NoteParserCreateRequest request = NoteParserCreateRequest.builder()
        .context(context)
        .date(date)
        .recordType(RecordType.DAILY)
        .build();

    // when // then
    when(noteParserService.parseNoteAndSaveRecord(any())).thenReturn(response);
    mockMvc.perform(
            post("/api/v0/records/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
