package myproject.mockjang.api.controller.mockjang.barn;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.mockjang.barn.request.BarnCreateRequest;
import myproject.mockjang.api.service.mockjang.barn.response.BarnResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


class BarnControllerTest extends ControllerTestSupport {

    @DisplayName("신규 축사를 등록한다")
    @Test
    void create() throws Exception {
        //given
        BarnCreateRequest request = BarnCreateRequest.builder().codeId(BARN_CODE_ID_1).build();

        //when //then

        mockMvc.perform(
                        post("/api/v0/barns/new")
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
        BarnCreateRequest request = BarnCreateRequest.builder().codeId(null).build();

        //when //then
        mockMvc.perform(
                        post("/api/v0/barns/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("축사명은 공백일 수 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("입력 없이 축사 생성을 시도하면 예외를 발생시킨다.")
    @Test
    void createWithEmpty() throws Exception {
        //given
        BarnCreateRequest request = BarnCreateRequest.builder().codeId(STRING_EMPTY).build();

        //when //then
        mockMvc.perform(
                        post("/api/v0/barns/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("축사명은 공백일 수 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("공백으로 축사 생성을 시도하면 예외를 발생시킨다.")
    @Test
    void createWithBlank() throws Exception {
        //given
        BarnCreateRequest request = BarnCreateRequest.builder().codeId(STRING_ONLY_SPACE).build();

        //when //then
        mockMvc.perform(
                        post("/api/v0/barns/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("축사명은 공백일 수 없습니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("축사 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        //given
        List<BarnResponse> response = List.of();
        //when //then
        when(barnService.findAll()).thenReturn(response);

        mockMvc.perform(get("/api/v0/barns"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("축사 단건 조회")
    @Test
    void findByCodeId() throws Exception {
        //given
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        BarnResponse response = BarnResponse.of(barn);

        //when //then
        when(barnService.findByCodeId(any())).thenReturn(response);
        mockMvc.perform(get("/api/v0/barns/" + BARN_CODE_ID_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").exists());
    }
}