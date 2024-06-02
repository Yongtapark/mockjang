package myproject.mockjang.api.controller.records.simple_record;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.records.simple_record.request.SimpleRecordCreateRequest;
import myproject.mockjang.api.controller.records.simple_record.request.SimpleRecordRemoveRequest;
import myproject.mockjang.api.controller.records.simple_record.request.SimpleRecordSearchRequest;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class SimpleRecordControllerTest extends ControllerTestSupport {

  @DisplayName("codeId를 기반으로 한 기록을 생성한다.")
  @Test
  void create() throws Exception {
    //given
    SimpleRecordCreateRequest request = SimpleRecordCreateRequest.builder()
        .codeId(PARSER_COW_CODE_ID_1)
        .recordType(RecordType.DAILY)
        .date(TEMP_DATE)
        .record(MEMO_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple/new")
            .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("기록을 생성 할 때, codeId를 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoCodeId() throws Exception {
    //given
    SimpleRecordCreateRequest request = SimpleRecordCreateRequest.builder()
        .codeId(null)
        .recordType(RecordType.DAILY)
        .date(TEMP_DATE)
        .record(MEMO_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("코드명은 공백일 수 없습니다."));
  }

  @DisplayName("기록을 생성 할 때, 기록 타입을 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoRecordType() throws Exception {
    //given
    SimpleRecordCreateRequest request = SimpleRecordCreateRequest.builder()
        .codeId(PARSER_COW_CODE_ID_1)
        .recordType(null)
        .date(TEMP_DATE)
        .record(MEMO_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("기록 타입은 반드시 입력하셔야 합니다."));
  }

  @DisplayName("기록을 생성 할 때, 날짜를 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoDate() throws Exception {
    //given
    SimpleRecordCreateRequest request = SimpleRecordCreateRequest.builder()
        .codeId(PARSER_COW_CODE_ID_1)
        .recordType(RecordType.DAILY)
        .date(null)
        .record(MEMO_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("기록 날짜는 반드시 입력하셔야 합니다."));
  }

  @DisplayName("기록을 생성 할 때, 기록을 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoRecord() throws Exception {
    //given
    SimpleRecordCreateRequest request = SimpleRecordCreateRequest.builder()
        .codeId(PARSER_COW_CODE_ID_1)
        .recordType(RecordType.DAILY)
        .date(TEMP_DATE)
        .record(null)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("기록 메모는 공백일 수 없습니다."));
  }

  @DisplayName("고유번호를 기반으로 기록을 제거한다")
  @Test
  void remove() throws Exception {
    //given
    SimpleRecordRemoveRequest request = SimpleRecordRemoveRequest.builder()
        .id(1L)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple/remove")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("204"))
        .andExpect(jsonPath("$.status").value("NO_CONTENT"))
        .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("코드id, 기록타입, 기록날짜를 통해 기록을 조회한다.")
  @Test
  void search() throws Exception {
    //given
    SimpleRecordSearchRequest request = SimpleRecordSearchRequest.builder()
        .codeId(PARSER_COW_CODE_ID_1)
        .recordType(RecordType.DAILY)
        .date(TEMP_DATE)
        .record(MEMO_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/records/simple")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }
}