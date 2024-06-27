package myproject.mockjang.docs.mockjang.pen;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import myproject.mockjang.api.controller.mockjang.pen.PenController;
import myproject.mockjang.api.controller.mockjang.pen.request.PenCreateRequest;
import myproject.mockjang.api.controller.mockjang.pen.request.PenUpdateRequest;
import myproject.mockjang.api.service.mockjang.pen.PenService;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class PenControllerDocsTest extends RestDocsSupport {

    private final PenService service = mock(PenService.class);

    @Override
    protected Object initController() {
        return new PenController(service);
    }

    @DisplayName("신규 축사칸을 등록한다")
    @Test
    void create() throws Exception {
        PenCreateRequest request = PenCreateRequest.builder().barnCodeId(BARN_CODE_ID_1)
                .penCodeId(PEN_CODE_ID_1).build();

        given(service.createPen(any())).willReturn(1L);

        mockMvc.perform(
                        post("/api/v0/pens/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "pen-new",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("barnCodeId").type(JsonFieldType.STRING).description("축사 이름"),
                                        fieldWithPath("penCodeId").type(JsonFieldType.STRING).description("축사 칸 이름")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("생성된 축사 칸 id")
                                )
                        )
                );
    }

    @DisplayName("축사칸 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        PenResponse response1 = PenResponse.builder().id(1L).codeId(PEN_CODE_ID_1).barnId(1L).cows(List.of(44L, 55L))
                .records(List.of(2L, 52L)).build();
        PenResponse response2 = PenResponse.builder().id(2L).codeId(PEN_CODE_ID_2).barnId(1L).cows(List.of(45L, 57L))
                .records(List.of(32L, 51L)).build();
        List<PenResponse> response = List.of(response1,response2);
        given(service.findAll()).willReturn(response);

        mockMvc.perform(get("/api/v0/pens"))
                .andDo(
                        document(
                                "pen-findAll",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("축사 칸 id"),
                                        fieldWithPath("data[].codeId").type(JsonFieldType.STRING).description("축사 칸 이름"),
                                        fieldWithPath("data[].barnId").type(JsonFieldType.NUMBER).description("축사 id"),
                                        fieldWithPath("data[].cows").type(JsonFieldType.ARRAY).description("소 id"),
                                        fieldWithPath("data[].records").type(JsonFieldType.ARRAY).description("축사 칸 기록")
                                )
                        )
                );
    }

    @DisplayName("축사칸 단건 조회")
    @Test
    void findByCodeId() throws Exception {
        PenResponse response = PenResponse.builder().id(1L).codeId(PEN_CODE_ID_1).barnId(1L).cows(List.of(44L, 55L))
                .records(List.of(2L, 52L)).build();

        given(service.findByCodeId(any())).willReturn(response);
        mockMvc.perform(get("/api/v0/pens/{codeId}",PEN_CODE_ID_1))
                .andDo(
                        document(
                                "pen-findByCodeId",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("축사 칸 id"),
                                        fieldWithPath("data.codeId").type(JsonFieldType.STRING).description("축사 칸 이름"),
                                        fieldWithPath("data.barnId").type(JsonFieldType.NUMBER).description("축사 id"),
                                        fieldWithPath("data.cows").type(JsonFieldType.ARRAY).description("소 id"),
                                        fieldWithPath("data.records").type(JsonFieldType.ARRAY).description("축사 칸 기록")
                                )
                        )
                );
    }

    @DisplayName("축사칸 기본 정보를 수정한다.")
    @Test
    void update() throws Exception {
        PenUpdateRequest request = PenUpdateRequest.builder()
                .penId(1L)
                .barnId(2L)
                .codeId(PEN_CODE_ID_2)
                .build();

        mockMvc.perform(put("/api/v0/pens/update")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "pen-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("penId").type(JsonFieldType.NUMBER).description("축사칸 id"),
                                        fieldWithPath("barnId").type(JsonFieldType.NUMBER).description("축사 id").optional(),
                                        fieldWithPath("codeId").type(JsonFieldType.STRING).description("축사 칸 이름").optional()
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

    @DisplayName("축사칸 제거")
    @Test
    void remove() throws Exception {

        mockMvc.perform(delete("/api/v0/pens/{id}",1L))
                .andDo(
                        document(
                                "pen-remove",
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
