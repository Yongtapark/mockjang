package myproject.mockjang.api.controller.records;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordFindAllByCodeIdAndRecordTypeRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordRemoveRequest;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CowRecordControllerTest extends ControllerTestSupport {

  @DisplayName("소 일일기록 생성 테스트")
  @Test
  void create() throws Exception {
    //given
    CowRecordCreateRequest request = CowRecordCreateRequest.builder().memo(MEMO_1)
        .cowCode(PARSER_COW_CODE_ID_1).recordType(
            RecordType.DAILY).date(TEMP_DATE).build();

    //when  //then
    mockMvc.perform(post("/api/v0/records/cow/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("소 기록 조회 테스트")
  @Test
  void findAllByCodeId() throws Exception {
    //given
    CowRecordCreateRequest request = CowRecordCreateRequest.builder().memo(MEMO_1)
        .cowCode(PARSER_COW_CODE_ID_1).recordType(
            RecordType.DAILY).date(TEMP_DATE).build();

    //when  //then
    mockMvc.perform(get("/api/v0/records/cow/" + PARSER_COW_CODE_ID_1)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("소 일일기록 제거 테스트")
  @Test
  void remove() throws Exception {
    //given
    CowRecordRemoveRequest request = CowRecordRemoveRequest.builder().id(1L).build();
    //when  //then
    mockMvc.perform(post("/api/v0/records/cow/remove")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("204"))
        .andExpect(jsonPath("$.status").value("NO_CONTENT"))
        .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("소 기록 제거를 할 떄 고유값을 받지 못하면 예외를 발생시킨다.")
  @Test
  void removeWithNoId() throws Exception {
    //given
    CowRecordRemoveRequest request = CowRecordRemoveRequest.builder().id(null).build();
    //when  //then
    mockMvc.perform(post("/api/v0/records/cow/remove")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("고유번호를 찾을 수 없습니다."));
  }

  @DisplayName("기록타입에 따라 기록 리스트를 조회한다.")
  @Test
  void findAllByCodeIdWhereRecordType() throws Exception {
    //given
    CowRecordFindAllByCodeIdAndRecordTypeRequest request = CowRecordFindAllByCodeIdAndRecordTypeRequest.builder()
        .cowCode(PARSER_COW_CODE_ID_1)
        .recordType(RecordType.DAILY)
        .build();

    //when  //then
    mockMvc.perform(get("/api/v0/records/cow/" + PARSER_COW_CODE_ID_1 + "/" + RecordType.DAILY)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }


}