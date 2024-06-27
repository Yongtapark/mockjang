package myproject.mockjang.api.controller.mockjang.pen;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.mockjang.pen.request.PenCreateRequest;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class PenControllerTest extends ControllerTestSupport {

    @DisplayName("신규 축사칸을 등록한다")
    @Test
    void create() throws Exception {
        //given
        PenCreateRequest request = PenCreateRequest.builder().barnCodeId(BARN_CODE_ID_1)
                .penCodeId(PEN_CODE_ID_1).build();

        //when //then
        mockMvc.perform(
                        post("/api/v0/pens/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @DisplayName("null 값으로 축사 생성을 시도하면 예외를 발생시킨다.")
    @Test
    void createWithCodeIdNull() throws Exception {
        //given
        PenCreateRequest request = PenCreateRequest.builder().barnCodeId(BARN_CODE_ID_1)
                .penCodeId(null).build();

        //when //then
        mockMvc.perform(
                        post("/api/v0/pens/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("축사칸명은 공백일 수 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("입력 없이 축사칸 생성을 시도하면 예외를 발생시킨다.")
    @Test
    void createWithEmpty() throws Exception {
        //given
        PenCreateRequest request = PenCreateRequest.builder().barnCodeId(BARN_CODE_ID_1)
                .penCodeId(STRING_EMPTY).build();

        //when //then
        mockMvc.perform(
                        post("/api/v0/pens/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("축사칸명은 공백일 수 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공백으로 축사칸 생성을 시도하면 예외를 발생시킨다.")
    @Test
    void createWithBlank() throws Exception {
        //given
        PenCreateRequest request = PenCreateRequest.builder().barnCodeId(BARN_CODE_ID_1)
                .penCodeId(STRING_ONLY_SPACE).build();
        //when //then
        mockMvc.perform(
                        post("/api/v0/pens/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("축사칸명은 공백일 수 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("축사칸 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        //given
        List<PenResponse> response = List.of();
        //when //then
        when(penService.findAll()).thenReturn(response);

        mockMvc.perform(get("/api/v0/pens"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("축사칸 단건 조회")
    @Test
    void findByCodeId() throws Exception {
        //given
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        PenResponse response = PenResponse.of(pen);

        //when //then
        when(penService.findByCodeId(any())).thenReturn(response);
        mockMvc.perform(get("/api/v0/pens/" + PEN_CODE_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists());
    }

}