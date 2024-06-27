package myproject.mockjang.docs.mockjang.barn;

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
import myproject.mockjang.api.controller.mockjang.barn.BarnController;
import myproject.mockjang.api.controller.mockjang.barn.request.BarnUpdateRequest;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.barn.response.BarnResponse;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class BarnControllerDocsTest extends RestDocsSupport {

    private final BarnService service = mock(BarnService.class);

    @Override
    protected Object initController() {
        return new BarnController(service);
    }

    @DisplayName("신규 축사를 등록한다")
    @Test
    void create() throws Exception {

        BarnCreateServiceRequest request = BarnCreateServiceRequest.builder().codeId(BARN_CODE_ID_1).build();

        given(service.create(any())).willReturn(1L);

        mockMvc.perform(
                        post("/api/v0/barns/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "barn-new",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("codeId").type(JsonFieldType.STRING).description("축사 이름")
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

    @DisplayName("축사 기본 정보를 수정한다.")
    @Test
    void update() throws Exception {

        BarnUpdateRequest request = BarnUpdateRequest.builder().id(1L).codeId(BARN_CODE_ID_1).build();

        mockMvc.perform(
                        put("/api/v0/barns/update")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "barn-update",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("축사 id"),
                                        fieldWithPath("codeId").type(JsonFieldType.STRING).description("축사 이름")
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

    @DisplayName("축사 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        BarnResponse response1 = BarnResponse.builder()
                .id(1L)
                .codeId(BARN_CODE_ID_1)
                .pens(List.of())
                .records(List.of())
                .build();

        BarnResponse response2 = BarnResponse.builder()
                .id(1L)
                .codeId(BARN_CODE_ID_1)
                .pens(List.of())
                .records(List.of())
                .build();

        List<BarnResponse> response = List.of(response1, response2);

        when(service.findAll()).thenReturn(response);

        mockMvc.perform(get("/api/v0/barns"))
                .andDo(
                        document(
                                "barn-findAll",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("축사 id"),
                                        fieldWithPath("data[].codeId").type(JsonFieldType.STRING).description("축사 이름"),
                                        fieldWithPath("data[].pens").type(JsonFieldType.ARRAY).description("축사칸 id"),
                                        fieldWithPath("data[].records").type(JsonFieldType.ARRAY).description("기록 id")
                                )
                        )
                );
    }

    @DisplayName("축사 단건 조회")
    @Test
    void findByCodeId() throws Exception {

        BarnResponse response = BarnResponse.builder()
                .id(1L)
                .codeId(BARN_CODE_ID_1)
                .pens(List.of(1L,2L))
                .records(List.of(3L,4L,5L))
                .build();

        //when //then
        when(service.findByCodeId(any())).thenReturn(response);
        mockMvc.perform(get("/api/v0/barns/{codeId}",BARN_CODE_ID_1 ))
                .andDo(
                        document(
                                "barn-findByCodeId",
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("축사 id"),
                                        fieldWithPath("data.codeId").type(JsonFieldType.STRING).description("축사 이름"),
                                        fieldWithPath("data.pens").type(JsonFieldType.ARRAY).description("축사칸 id"),
                                        fieldWithPath("data.records").type(JsonFieldType.ARRAY).description("기록 id")
                                )
                        )
                );
    }

    @DisplayName("축사를 제거한다.")
    @Test
    void remove() throws Exception {

        //given //when //then
        mockMvc.perform(
                        delete("/api/v0/barns/{id}",1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(
                        document(
                                "barn-remove",
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
