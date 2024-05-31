package myproject.mockjang.api.controller.mockjang.cow;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.mockjang.cow.request.CowCreateRequest;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CowControllerTest extends ControllerTestSupport {

  @DisplayName("신규 소를 등록한다")
  @Test
  void create() throws Exception {
    //given
    CowCreateRequest request = createRequest(PARSER_COW_CODE_ID_1);

    //when //then
    mockMvc.perform(
            post("/api/v0/cows/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("null 값으로 소 생성을 시도하면 예외를 발생시킨다.")
  @Test
  void createWithCodeIdNull() throws Exception {
    //given
    CowCreateRequest request = createRequest(null);

    //when //then
    mockMvc.perform(
            post("/api/v0/cows/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("소 이름은 공백일 수 없습니다."))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @DisplayName("입력 없이 소 생성을 시도하면 예외를 발생시킨다.")
  @Test
  void createWithEmpty() throws Exception {
    //given
    CowCreateRequest request = createRequest(STRING_EMPTY);

    //when //then
    mockMvc.perform(
            post("/api/v0/cows/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("소 이름은 공백일 수 없습니다."))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @DisplayName("공백으로 소 생성을 시도하면 예외를 발생시킨다.")
  @Test
  void createWithBlank() throws Exception {
    //given
    CowCreateRequest request = createRequest(STRING_ONLY_SPACE);
    //when //then
    mockMvc.perform(
            post("/api/v0/cows/new")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("400"))
        .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
        .andExpect(jsonPath("$.message").value("소 이름은 공백일 수 없습니다."))
        .andExpect(jsonPath("$.data").isEmpty());
  }

  @DisplayName("소 목록을 조회한다.")
  @Test
  void findAll() throws Exception {
    //given
    List<CowResponse> response = List.of();
    //when //then
    when(cowService.findAll()).thenReturn(response);

    mockMvc.perform(get("/api/v0/cows"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.data").isArray());
  }

  @DisplayName("소 단건 조회")
  @Test
  void findByCodeId() throws Exception {
    //given
    LocalDateTime date = LocalDateTime.of(2024, 5, 31, 00, 00);
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, date);
    CowResponse response = CowResponse.of(cow);
    //when //then
    when(cowService.findByCodeId(any())).thenReturn(response);
    mockMvc.perform(get("/api/v0/cows/" + PARSER_COW_CODE_ID_1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.data").exists());
  }


  private  CowCreateRequest createRequest(String cowCode) {
    LocalDateTime date = LocalDateTime.of(2024, 5, 31, 00, 00);
    return CowCreateRequest.builder().cowCode(cowCode)
        .penCode(PARSER_PEN_CODE_ID_1)
        .birthDate(date)
        .gender(Gender.FEMALE)
        .build();
  }
}