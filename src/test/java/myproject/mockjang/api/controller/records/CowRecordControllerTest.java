package myproject.mockjang.api.controller.records;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordSearchRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordUpdateRequest;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CowRecordControllerTest extends ControllerTestSupport {

    @DisplayName("소의 기록을 생성 한다.")
    @Test
    void create() throws Exception {
        //given
        CowRecordCreateRequest request = CowRecordCreateRequest.builder().record(MEMO_1)
                .codeId(COW_CODE_ID_1).recordType(
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

    @DisplayName("소의 기록을 검색한다.")
    @Test
    void search() throws Exception {
        //given
        CowRecordSearchRequest request = CowRecordSearchRequest.builder().record(MEMO_1)
                .codeId(COW_CODE_ID_1).recordType(
                        RecordType.DAILY).date(TEMP_DATE).build();

        //when  //then
        mockMvc.perform(post("/api/v0/records/cow" )
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("소의 기록을 수정한다.")
    @Test
    void update() throws Exception {
        //given
        CowRecordUpdateRequest request = CowRecordUpdateRequest.builder().id(1L).record(MEMO_1)
                .codeId(COW_CODE_ID_1).recordType(RecordType.DAILY).date(TEMP_DATE).build();

        //when  //then
        mockMvc.perform(put("/api/v0/records/cow/update" )
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"));
    }



    @DisplayName("소의 기록을 제거한다.")
    @Test
    void remove() throws Exception {
        //given
        //when  //then
        mockMvc.perform(delete("/api/v0/records/cow/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("204"))
                .andExpect(jsonPath("$.status").value("NO_CONTENT"))
                .andExpect(jsonPath("$.message").value("NO_CONTENT"));
    }


}