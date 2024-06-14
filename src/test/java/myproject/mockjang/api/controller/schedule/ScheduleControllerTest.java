package myproject.mockjang.api.controller.schedule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import myproject.mockjang.ControllerTestSupport;
import myproject.mockjang.api.controller.schedule.request.ScheduleCreateRequest;
import myproject.mockjang.api.controller.schedule.request.ScheduleSearchRequest;
import myproject.mockjang.api.controller.schedule.request.ScheduleUpdateRequest;
import myproject.mockjang.domain.schedule.ScheduleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class ScheduleControllerTest extends ControllerTestSupport {

  @DisplayName("일정을 생성한다.")
  @Test
  void create() throws Exception {
    //given
    ScheduleCreateRequest request = ScheduleCreateRequest.builder()
        .startDate(READ_DATE.minusDays(1))
        .targetDate(READ_DATE.plusDays(1))
        .context(SCHEDULE_CONTEXT_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/schedule/new")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("일정을 단일 조회한다.")
  @Test
  void findScheduleFindById() throws Exception {
    //given //when //then
    mockMvc.perform(get("/api/v0/schedule/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("조회일부터 주말까지의 일정을 조회한다")
  @Test
  void showThisWeekScheduleFromToday() throws Exception {
    //given //when //then
    mockMvc.perform(get("/api/v0/schedule/weeks/{date}", READ_DATE)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }

  @DisplayName("일정을 제거한다")
  @Test
  void remove() throws Exception {
    //given //when //then
    mockMvc.perform(delete("/api/v0/schedule/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("204"))
        .andExpect(jsonPath("$.status").value("NO_CONTENT"))
        .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("일정을 수정한다.")
  @Test
  void update() throws Exception {
    //given
    ScheduleUpdateRequest request = ScheduleUpdateRequest.builder()
        .startDate(READ_DATE.minusDays(1))
        .targetDate(READ_DATE.plusDays(1))
        .context(SCHEDULE_CONTEXT_1)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/schedule/update")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("204"))
        .andExpect(jsonPath("$.status").value("NO_CONTENT"))
        .andExpect(jsonPath("$.message").value("NO_CONTENT"));
  }

  @DisplayName("일정을 검색한다.")
  @Test
  void search() throws Exception {
    //given
    ScheduleSearchRequest request = ScheduleSearchRequest.builder()
        .startDate(READ_DATE.minusDays(1))
        .targetDate(READ_DATE.plusDays(1))
        .context(SCHEDULE_CONTEXT_1)
        .scheduleStatus(ScheduleStatus.UPCOMING)
        .build();

    //when //then
    mockMvc.perform(post("/api/v0/schedule")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.code").value("200"))
        .andExpect(jsonPath("$.status").value("OK"))
        .andExpect(jsonPath("$.message").value("OK"));
  }




}