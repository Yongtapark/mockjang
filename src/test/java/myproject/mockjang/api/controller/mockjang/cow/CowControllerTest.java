package myproject.mockjang.api.controller.mockjang.cow;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.mockjang.cow.request.CowCreateRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRegisterParentsRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRegisterUnitPriceRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRemoveParentsRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowUpdateCowStatusRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowUpdatePenRequest;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
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
    CowCreateRequest request = createRequest(COW_CODE_ID_1);
    //given(cowService.createRaisingCow(any())).willReturn();



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

  @DisplayName("소의 부모를 등록한다")
  @Test
  void registerParents() throws Exception {
    //given
    CowRegisterParentsRequest request = CowRegisterParentsRequest.builder().cowId(1L).parents(List.of(2L,3L)).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/register/parents")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("204"))
            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
            .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("소의 부모의 부모는 최소 1마리, 최대 2마리 등록 가능하다.")
  @Test
  void registerParentsWithOverParents() throws Exception {
    //given
    CowRegisterParentsRequest request = CowRegisterParentsRequest.builder().cowId(1L).parents(List.of(2L,3L,4L)).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/register/parents")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("부모는 최소 1명, 최대 2명이어야 합니다."));
  }

  @DisplayName("소의 부모를 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void registerParentsWithNoParents() throws Exception {
    //given
    CowRegisterParentsRequest request = CowRegisterParentsRequest.builder().cowId(1L).parents(null).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/register/parents")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("고유번호는 반드시 입력하셔야 합니다."));
  }

  @DisplayName("도축 상태가 된 소의 금액을 지정한다.")
  @Test
  void registerUnitPrice() throws Exception {
    //given
    CowRegisterUnitPriceRequest request = CowRegisterUnitPriceRequest.builder().cowId(1L).unitPrice(UNIT_PRICE_100_000_000).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/register/price")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("204"))
            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
            .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("도축 상태가 된 소의 금액을 지정하지 않으면 예외를 발생키킨다.")
  @Test
  void registerUnitPriceWithNoPrice() throws Exception {
    //given
    CowRegisterUnitPriceRequest request = CowRegisterUnitPriceRequest.builder().cowId(1L).unitPrice(null).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/register/price")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("소 금액은 반드시 입력하셔야 합니다."));
  }

  @DisplayName("소의 축사칸을 변경한다.")
  @Test
  void updatePen() throws Exception {
    //given
    CowUpdatePenRequest request = CowUpdatePenRequest.builder().cowId(1L).penId(1L).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/update/pen")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("204"))
            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
            .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("소의 축사칸을 입력하지 않을 시 예외를 발생시킨다.")
  @Test
  void updatePenWithNoPenId() throws Exception {
    //given
    CowUpdatePenRequest request = CowUpdatePenRequest.builder().cowId(1L).penId(null).build();

    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/update/pen")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("고유번호는 반드시 입력하셔야 합니다."));
  }

  @DisplayName("소의 상태를 변경한다")
  @Test
  void updateCowStatus() throws Exception {
    //given
    CowUpdateCowStatusRequest request = CowUpdateCowStatusRequest.builder().cowId(1L).cowStatus(CowStatus.SLAUGHTERED).build();


    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/update/status")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("204"))
            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
            .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("소의 상태를 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void updateCowStatusWithNoStatus() throws Exception {
    //given
    CowUpdateCowStatusRequest request = CowUpdateCowStatusRequest.builder().cowId(1L).cowStatus(null).build();


    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/update/status")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("소 상태는 반드시 입력하셔야 합니다."));
  }

  @DisplayName("소의 부모 관계를 제거한다.")
  @Test
  void removeParents() throws Exception {
    //given
    CowRemoveParentsRequest request = CowRemoveParentsRequest.builder().cowId(1L).parents(List.of(2L,3L)).build();


    //when //then
    mockMvc.perform(
                    post("/api/v0/cows/remove/parents")
                            .content(objectMapper.writeValueAsString(request))
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("204"))
            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
            .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("소를 제거한다.")
  @Test
  void remove() throws Exception {
    //given//when //then
    mockMvc.perform(
                    delete("/api/v0/cows/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("204"))
            .andExpect(jsonPath("$.status").value("NO_CONTENT"))
            .andExpect(jsonPath("$.message").value("NO_CONTENT"));
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
    Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, date);

    CowResponse response = CowResponse.of(cow);
    //when //then
    when(cowService.findByCodeId(any())).thenReturn(response);
    mockMvc.perform(get("/api/v0/cows/" + COW_CODE_ID_1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"))
        .andExpect(jsonPath("$.data").exists());
  }


  private CowCreateRequest createRequest(String cowCode) {
    LocalDateTime date = LocalDateTime.of(2024, 5, 31, 00, 00);
    return CowCreateRequest.builder().cowCode(cowCode)
        .penCode(PEN_CODE_ID_1)
        .birthDate(date)
        .gender(Gender.FEMALE)
        .build();
  }
}