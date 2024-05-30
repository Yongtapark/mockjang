package myproject.mockjang.api.controller.note_parser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Optional;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.note_parser.request.NoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.NoteParserService;
import myproject.mockjang.api.service.note_parser.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.response.NoteParserResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

class NoteParserControllerTest extends ControllerTestSupport {

  @DisplayName("신규 일지를 저장한다.")
  @Test
  void createNote() throws Exception {
    // given
    HashMap<String, Integer> names = new HashMap<>();
    names.put(PARSER_BARN_CODE_ID_1, 1);
    NoteParserResponse response = NoteParserResponse.builder().names(names).build();


    String context = "[["+PARSER_BARN_CODE_ID_1+"]] "+PARSER_BARN_NOTE_1;
    NoteParserCreateRequest request = NoteParserCreateRequest.builder()
        .context(context)
        .build();

    // when // then
    when(noteParserService.parseNoteAndSaveRecord(any())).thenReturn(response);
    mockMvc.perform(
            post("/api/v0/notes/daily/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
