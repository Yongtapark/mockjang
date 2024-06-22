package myproject.mockjang.api.controller.note_parser.simple;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.note_parser.simple.request.SimpleNoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class SimpleNoteParserControllerTest extends ControllerTestSupport {

  @DisplayName("신규 일지를 저장한다.")
  @Test
  void createRecords() throws Exception {
    // given
    HashMap<String, Integer> names = new HashMap<>();
    names.put(PARSER_BARN_CODE_ID_1, 1);
    RecordParserResponse response = RecordParserResponse.builder().names(names).build();

    String context = "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1;
    SimpleNoteParserCreateRequest request = SimpleNoteParserCreateRequest.builder().context(context)
        .date(TEMP_DATE).recordType(RecordType.DAILY).build();

    // when // then
    when(simpleNoteParserService.parseNoteAndSaveRecord(any())).thenReturn(response);
    mockMvc.perform(
        post("/api/v0/simple-parser/new").content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
  }

  @DisplayName("신규 일지를 저장할 때 내용을 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createRecordsWithNoContext() throws Exception {
    // given
    HashMap<String, Integer> names = new HashMap<>();
    names.put(PARSER_BARN_CODE_ID_1, 1);
    RecordParserResponse response = RecordParserResponse.builder().names(names).build();

    String context = "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1;
    SimpleNoteParserCreateRequest request = SimpleNoteParserCreateRequest.builder().context(null)
        .date(TEMP_DATE).recordType(RecordType.DAILY).build();

    // when // then
    when(simpleNoteParserService.parseNoteAndSaveRecord(any())).thenReturn(response);
    mockMvc.perform(
            post("/api/v0/simple-parser/new").content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("내용은 공백일 수 없습니다."));
  }

  @DisplayName("신규 일지를 저장할 때 기록타입을 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createRecordsWithNoRecordType() throws Exception {
    // given
    HashMap<String, Integer> names = new HashMap<>();
    names.put(PARSER_BARN_CODE_ID_1, 1);
    RecordParserResponse response = RecordParserResponse.builder().names(names).build();

    String context = "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1;
    SimpleNoteParserCreateRequest request = SimpleNoteParserCreateRequest.builder().context(context)
        .date(TEMP_DATE).recordType(null).build();

    // when // then
    when(simpleNoteParserService.parseNoteAndSaveRecord(any())).thenReturn(response);
    mockMvc.perform(
            post("/api/v0/simple-parser/new").content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("기록 타입은 반드시 입력하셔야 합니다."));
  }

  @DisplayName("신규 일지를 저장할 때 기록타입을 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createRecordsWithNoDate() throws Exception {
    // given
    HashMap<String, Integer> names = new HashMap<>();
    names.put(PARSER_BARN_CODE_ID_1, 1);
    RecordParserResponse response = RecordParserResponse.builder().names(names).build();

    String context = "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1;
    SimpleNoteParserCreateRequest request = SimpleNoteParserCreateRequest.builder().context(context)
        .date(null).recordType(RecordType.DAILY).build();

    // when // then
    when(simpleNoteParserService.parseNoteAndSaveRecord(any())).thenReturn(response);
    mockMvc.perform(
            post("/api/v0/simple-parser/new").content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isBadRequest()).andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("날짜는 반드시 입력하셔야 합니다."));
  }
}