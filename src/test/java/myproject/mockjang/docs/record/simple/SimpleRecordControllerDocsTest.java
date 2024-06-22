package myproject.mockjang.docs.record.simple;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import myproject.mockjang.api.controller.records.simple.SimpleRecordController;
import myproject.mockjang.api.controller.records.simple.request.SimpleRecordCreateRequest;
import myproject.mockjang.api.controller.records.simple.request.SimpleRecordUpdateRequest;
import myproject.mockjang.api.service.records.simple.SimpleRecordService;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.simple.response.SimpleRecordResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.records.RecordType;
import org.apache.catalina.filters.ExpiresFilter.XHttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class SimpleRecordControllerDocsTest extends RestDocsSupport {

  private final SimpleRecordService service = mock(SimpleRecordService.class);

  @Override
  protected Object initController() {
    return new SimpleRecordController(service);
  }

  @DisplayName("codeId를 기반으로 한 기록을 생성한다.")
  @Test
  void create() throws Exception {
    SimpleRecordCreateRequest request = SimpleRecordCreateRequest.builder()
        .codeId(PARSER_COW_CODE_ID_1).recordType(RecordType.DAILY).date(TEMP_DATE).record(MEMO_1)
        .build();

    SimpleRecordResponse response = SimpleRecordResponse.builder().id(1L)
        .codeId(PARSER_COW_CODE_ID_1).recordType(RecordType.HEALTH).date(TEMP_DATE)
        .record(PARSER_COW_NOTE_1).build();

    given(service.create(any(SimpleRecordCreateServiceRequest.class))).willReturn(response);

    mockMvc.perform(
            post("/api/v0/records/simple/new"
            ).content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(
            document("SimpleRecord-new",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(fieldWithPath("codeId").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("recordType").type(JsonFieldType.STRING).description("기록 유형"),
                    fieldWithPath("date").type(JsonFieldType.ARRAY).description("기록 날짜"),
                    fieldWithPath("record").type(JsonFieldType.STRING).description("기록 내용")),
                responseFields(fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("data.codeId").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("data.recordType").type(JsonFieldType.STRING)
                        .description("기록 유형"),
                    fieldWithPath("data.date").type(JsonFieldType.ARRAY).description("기록 날짜"),
                    fieldWithPath("data.record").type(JsonFieldType.STRING).description("기록 내용")
                )
            )
        );
  }

  @DisplayName("기록을 단일 조회한다.")
  @Test
  void findSimpleRecordById() throws Exception {

    SimpleRecordResponse response = SimpleRecordResponse.builder().id(1L)
        .codeId(PARSER_COW_CODE_ID_1).recordType(RecordType.HEALTH).date(TEMP_DATE)
        .record(PARSER_COW_NOTE_1).build();

    given(service.findSimpleRecordById(1L)).willReturn(response);

    mockMvc.perform(
            get(
                "/api/v0/records/simple/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(
            document("SimpleRecord-findSimpleRecordById",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("data.codeId").type(JsonFieldType.STRING).description("이름"),
                    fieldWithPath("data.recordType").type(JsonFieldType.STRING)
                        .description("기록 유형"),
                    fieldWithPath("data.date").type(JsonFieldType.ARRAY).description("기록 날짜"),
                    fieldWithPath("data.record").type(JsonFieldType.STRING).description("기록 내용")
                )
            )
        );
  }

  @DisplayName("기록을 수정한다.")
  @Test
  void update() throws Exception {
    SimpleRecordUpdateRequest request = SimpleRecordUpdateRequest.builder()
        .id(1L)
        .codeId("수정된 이름")
        .date(TEMP_DATE)
        .recordType(RecordType.DAILY)
        .record("수정된 기록")
        .build();

    mockMvc.perform(put("/api/v0/records/simple/update")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(
            document("SimpleRecord-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("codeId").type(JsonFieldType.STRING).description("수정할 이름")
                        .optional(),
                    fieldWithPath("recordType").type(JsonFieldType.STRING).description("수정할 기록 유형")
                        .optional(),
                    fieldWithPath("date").type(JsonFieldType.ARRAY).description("수정할 기록 날짜")
                        .optional(),
                    fieldWithPath("record").type(JsonFieldType.STRING).description("수정할 기록 내용")
                        .optional()
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                )
            )
        );
  }
}
