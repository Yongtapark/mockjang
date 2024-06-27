package myproject.mockjang.docs.record.mockjang.cow;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import myproject.mockjang.api.controller.records.mockjang.cow.CowRecordController;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordCreateRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordSearchRequest;
import myproject.mockjang.api.controller.records.mockjang.cow.request.CowRecordUpdateRequest;
import myproject.mockjang.api.service.records.mockjang.cow.CowRecordService;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class CowRecordControllerDocsTest extends RestDocsSupport {
    private final CowRecordService service = mock(CowRecordService.class);

    @Override
    protected Object initController() {
        return new CowRecordController(service);
    }

    @DisplayName("소의 기록을 생성한다.")
    @Test
    void create() throws Exception {
        CowRecordCreateRequest request = CowRecordCreateRequest.builder().record(MEMO_1)
                .codeId(COW_CODE_ID_1).recordType(RecordType.DAILY).date(TEMP_DATE).build();

        mockMvc.perform(post("/api/v0/records/cow/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "cowRecord-new",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("codeId").type(JsonFieldType.STRING).description("소 이름"),
                                        fieldWithPath("record").type(JsonFieldType.STRING).description("기록"),
                                        fieldWithPath("recordType").type(JsonFieldType.STRING).description("기록 유형"),
                                        fieldWithPath("date").type(JsonFieldType.ARRAY).description("기록 날짜")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 축사 id")
                                )
                        )
                );
    }

    @DisplayName("소의 기록 목록을 검색한다")
    @Test
    void search() throws Exception {
        //given
        CowRecordSearchRequest request = CowRecordSearchRequest.builder().record(null)
                .codeId(COW_CODE_ID_1).recordType(
                        null).date(null).build();

        CowRecordResponse response1 = CowRecordResponse.builder()
                .id(1L)
                .cowId(1L)
                .memo(MEMO_1)
                .recordType(RecordType.DAILY)
                .date(TEMP_DATE)
                .build();
        CowRecordResponse response2 = CowRecordResponse.builder()
                .id(2L)
                .cowId(1L)
                .memo(MEMO_2)
                .recordType(RecordType.HEALTH)
                .date(TEMP_DATE)
                .build();

        given(service.search(any())).willReturn(List.of(response1,response2));

        //when  //then
        mockMvc.perform(post("/api/v0/records/cow" )
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "cowRecord-search",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("codeId").type(JsonFieldType.STRING).description("소 이름").optional(),
                                        fieldWithPath("record").type(JsonFieldType.STRING).description("기록").optional(),
                                        fieldWithPath("recordType").type(JsonFieldType.STRING).description("기록 유형").optional(),
                                        fieldWithPath("date").type(JsonFieldType.ARRAY).description("기록 날짜").optional()
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("id"),
                                        fieldWithPath("data[].cowId").type(JsonFieldType.NUMBER).description("소 id"),
                                        fieldWithPath("data[].memo").type(JsonFieldType.STRING).description("기록"),
                                        fieldWithPath("data[].recordType").type(JsonFieldType.STRING).description("기록 유형"),
                                        fieldWithPath("data[].date").type(JsonFieldType.ARRAY).description("기록 날짜")
                                )
                        )
                );
    }

    @DisplayName("소의 기록을 수정한다.")
    @Test
    void update() throws Exception {
        CowRecordUpdateRequest request = CowRecordUpdateRequest.builder().id(1L).record(MEMO_1)
                .codeId(COW_CODE_ID_1).recordType(RecordType.DAILY).date(TEMP_DATE).build();

        mockMvc.perform(put("/api/v0/records/cow/update" )
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "cowRecord-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("소 id"),
                                        fieldWithPath("codeId").type(JsonFieldType.STRING).description("소 이름"),
                                        fieldWithPath("record").type(JsonFieldType.STRING).description("기록"),
                                        fieldWithPath("recordType").type(JsonFieldType.STRING).description("기록 유형"),
                                        fieldWithPath("date").type(JsonFieldType.ARRAY).description("기록 날짜")
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

    @DisplayName("소의 기록을 제거한다.")
    @Test
    void remove() throws Exception {
        //given
        //when  //then
        mockMvc.perform(delete("/api/v0/records/cow/{id}",1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "cowRecord-remove",
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
}
