package myproject.mockjang.docs.schedule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;
import myproject.mockjang.api.controller.schedule.ScheduleController;
import myproject.mockjang.api.controller.schedule.request.ScheduleCreateRequest;
import myproject.mockjang.api.controller.schedule.request.ScheduleSearchRequest;
import myproject.mockjang.api.controller.schedule.request.ScheduleUpdateRequest;
import myproject.mockjang.api.service.schedule.ScheduleService;
import myproject.mockjang.api.service.schedule.reponse.ScheduleResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.schedule.ScheduleStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class ScheduleControllerDocsTest extends RestDocsSupport {

    private final ScheduleService service =mock(ScheduleService.class);
    @Override
    protected Object initController() {
        return new ScheduleController(service);
    }

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
                .andDo(
                        document(
                                "schedule-new",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("startDate").type(JsonFieldType.ARRAY).description("일정 시작일"),
                                        fieldWithPath("targetDate").type(JsonFieldType.ARRAY).description("일정 목표일"),
                                        fieldWithPath("context").type(JsonFieldType.STRING).description("일정 내용")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 일정 id")
                                )
                        )
                );
    }

    @DisplayName("일정을 단일 조회한다.")
    @Test
    void findScheduleFindById() throws Exception {
        //given //when //then
        ScheduleResponse response = ScheduleResponse.builder()
                .id(1L)
                .scheduleStatus(ScheduleStatus.UPCOMING)
                .targetDate(READ_DATE.plusDays(1))
                .startDate(READ_DATE.minusDays(1))
                .context(MEMO_1)
                .build();

        given(service.findScheduleById(any())).willReturn(response);

        mockMvc.perform(get("/api/v0/schedule/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "schedule-findById",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("일정 시작일"),
                                        fieldWithPath("data.startDate").type(JsonFieldType.ARRAY).description("일정 시작일"),
                                        fieldWithPath("data.targetDate").type(JsonFieldType.ARRAY).description("일정 목표일"),
                                        fieldWithPath("data.scheduleStatus").type(JsonFieldType.STRING).description("일정 상태"),
                                        fieldWithPath("data.context").type(JsonFieldType.STRING).description("일정 내용")
                                )
                        )
                );
    }

    @DisplayName("조회일부터 주말까지의 일정을 조회한다")
    @Test
    void showThisWeekScheduleFromToday() throws Exception {
        ScheduleResponse response1 = ScheduleResponse.builder()
                .id(1L)
                .scheduleStatus(ScheduleStatus.UPCOMING)
                .targetDate(READ_DATE.plusDays(1))
                .startDate(READ_DATE.minusDays(1))
                .context(MEMO_1)
                .build();
        ScheduleResponse response2 = ScheduleResponse.builder()
                .id(2L)
                .scheduleStatus(ScheduleStatus.UPCOMING)
                .targetDate(READ_DATE.plusDays(2))
                .startDate(READ_DATE.minusDays(1))
                .context(MEMO_1)
                .build();

        given(service.showThisWeekScheduleFromToday(any())).willReturn(List.of(response1,response2));

        mockMvc.perform(get("/api/v0/schedule/weeks/{date}", READ_DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "schedule-showThisWeekScheduleFromToday",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("일정 시작일"),
                                        fieldWithPath("data[].startDate").type(JsonFieldType.ARRAY).description("일정 시작일"),
                                        fieldWithPath("data[].targetDate").type(JsonFieldType.ARRAY).description("일정 목표일"),
                                        fieldWithPath("data[].scheduleStatus").type(JsonFieldType.STRING).description("일정 상태"),
                                        fieldWithPath("data[].context").type(JsonFieldType.STRING).description("일정 내용")
                                )
                        )
                );
    }

    @DisplayName("일정을 제거한다")
    @Test
    void remove() throws Exception {
        //given //when //then
        mockMvc.perform(delete("/api/v0/schedule/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "schedule-remove",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                                )
                        )
                );
    }

    @DisplayName("일정을 수정한다.")
    @Test
    void update() throws Exception {
        //given
        ScheduleUpdateRequest request = ScheduleUpdateRequest.builder()
                .id(1L)
                .startDate(READ_DATE.minusDays(1))
                .targetDate(READ_DATE.plusDays(1))
                .readDate(READ_DATE)
                .context(SCHEDULE_CONTEXT_1)
                .build();

        //when //then
        mockMvc.perform(put("/api/v0/schedule/update")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "schedule-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("일정 id"),
                                        fieldWithPath("startDate").type(JsonFieldType.ARRAY).description("일정 시작일").optional(),
                                        fieldWithPath("targetDate").type(JsonFieldType.ARRAY).description("일정 목표일"),
                                        fieldWithPath("readDate").type(JsonFieldType.ARRAY).description("일정 조회일"),
                                        fieldWithPath("context").type(JsonFieldType.STRING).description("일정 내용").optional()
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                                )
                        )
                );
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

        ScheduleResponse response1 = ScheduleResponse.builder()
                .id(1L)
                .scheduleStatus(ScheduleStatus.UPCOMING)
                .targetDate(READ_DATE.plusDays(1))
                .startDate(READ_DATE.minusDays(1))
                .context(MEMO_1)
                .build();
        ScheduleResponse response2 = ScheduleResponse.builder()
                .id(2L)
                .scheduleStatus(ScheduleStatus.UPCOMING)
                .targetDate(READ_DATE.plusDays(2))
                .startDate(READ_DATE.minusDays(1))
                .context(MEMO_1)
                .build();

        given(service.search(any())).willReturn(List.of(response1,response2));
        //when //then
        mockMvc.perform(post("/api/v0/schedule")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "schedule-search",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("startDate").type(JsonFieldType.ARRAY).description("일정 시작일"),
                                        fieldWithPath("targetDate").type(JsonFieldType.ARRAY).description("일정 목표일"),
                                        fieldWithPath("context").type(JsonFieldType.STRING).description("일정 내용"),
                                        fieldWithPath("scheduleStatus").type(JsonFieldType.STRING).description("일정 상태")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("일정 시작일"),
                                        fieldWithPath("data[].startDate").type(JsonFieldType.ARRAY).description("일정 시작일"),
                                        fieldWithPath("data[].targetDate").type(JsonFieldType.ARRAY).description("일정 목표일"),
                                        fieldWithPath("data[].scheduleStatus").type(JsonFieldType.STRING).description("일정 상태"),
                                        fieldWithPath("data[].context").type(JsonFieldType.STRING).description("일정 내용")
                                )
                        )
                );
    }
}
