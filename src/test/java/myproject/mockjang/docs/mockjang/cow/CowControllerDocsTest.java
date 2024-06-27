package myproject.mockjang.docs.mockjang.cow;

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

import java.util.List;
import myproject.mockjang.api.controller.mockjang.cow.CowController;
import myproject.mockjang.api.controller.mockjang.cow.request.CowCreateRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRegisterParentsRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRegisterUnitPriceRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowRemoveParentsRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowUpdateCowStatusRequest;
import myproject.mockjang.api.controller.mockjang.cow.request.CowUpdatePenRequest;
import myproject.mockjang.api.service.mockjang.cow.CowService;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class CowControllerDocsTest extends RestDocsSupport {

    private final CowService service = mock(CowService.class);

    @Override
    protected Object initController() {
        return new CowController(service);
    }

    @DisplayName("신규 소를 등록한다")
    @Test
    void create() throws Exception {
        //given

        CowCreateRequest request = CowCreateRequest.builder().cowCode(COW_CODE_ID_1)
                .penCode(PEN_CODE_ID_1)
                .birthDate(TEMP_DATE)
                .gender(Gender.FEMALE)
                .build();

        CowResponse response = CowResponse.builder()
                .id(3L)
                .codeId(COW_CODE_ID_1)
                .birthDate(TEMP_DATE)
                .gender(Gender.FEMALE)
                .cowStatus(CowStatus.RAISING)
                .barnId(1L)
                .penId(2L)
                .momId(null)
                .dadId(null)
                .children(List.of())
                .feedConsumptions(List.of())
                .records(List.of())
                .unitPrice(null)
                .build();

        given(service.createRaisingCow(any())).willReturn(response);

        //when //then
        mockMvc.perform(
                        post("/api/v0/cows/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document(
                        "Cow-new",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cowCode").type(JsonFieldType.STRING).description("소 이름"),
                                fieldWithPath("penCode").type(JsonFieldType.STRING).description("축사칸 이름"),
                                fieldWithPath("birthDate").type(JsonFieldType.ARRAY).description("생일"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.codeId").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.birthDate").type(JsonFieldType.ARRAY).description("생일"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("data.cowStatus").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("data.barnId").type(JsonFieldType.NUMBER).description("축사 id"),
                                fieldWithPath("data.penId").type(JsonFieldType.NUMBER).description("축사칸 id"),
                                fieldWithPath("data.momId").type(JsonFieldType.NULL).description("엄마 소 id"),
                                fieldWithPath("data.dadId").type(JsonFieldType.NULL).description("아빠 소 id"),
                                fieldWithPath("data.children").type(JsonFieldType.ARRAY).description("자식 id"),
                                fieldWithPath("data.records").type(JsonFieldType.ARRAY).description("기록 id"),
                                fieldWithPath("data.feedConsumptions").type(JsonFieldType.ARRAY).description("먹이 소비 기록 id"),
                                fieldWithPath("data.unitPrice").type(JsonFieldType.NULL).description("판매가")
                        )
                ));

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
                .andDo(document(
                        "Cow-registerParents",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cowId").type(JsonFieldType.NUMBER).description("소 id"),
                                fieldWithPath("parents").type(JsonFieldType.ARRAY).description("부모 id")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                                )
                ));
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
                .andDo(document(
                        "Cow-registerUnitPrice",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cowId").type(JsonFieldType.NUMBER).description("소 id"),
                                fieldWithPath("unitPrice").type(JsonFieldType.NUMBER).description("판매가")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                        )
                ));
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
                .andDo(document(
                        "Cow-updatePen",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cowId").type(JsonFieldType.NUMBER).description("소 id"),
                                fieldWithPath("penId").type(JsonFieldType.NUMBER).description("축사칸 id")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                        )
                ));
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
                .andDo(document(
                        "Cow-updateCowStatus",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cowId").type(JsonFieldType.NUMBER).description("소 id"),
                                fieldWithPath("cowStatus").type(JsonFieldType.STRING).description("소 상태")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                        )
                ));
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
                .andDo(document(
                        "Cow-removeParents",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("cowId").type(JsonFieldType.NUMBER).description("소 id"),
                                fieldWithPath("parents").type(JsonFieldType.ARRAY).description("부모 id")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                        )
                ));
    }

    @DisplayName("소를 제거한다.")
    @Test
    void remove() throws Exception {
        //given//when //then
        mockMvc.perform(
                        delete("/api/v0/cows/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document(
                        "Cow-remove",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                        )
                ));
    }

    @DisplayName("소 목록을 조회한다.")
    @Test
    void findAll() throws Exception {
        //given

        CowResponse cowResponse1 = CowResponse.builder()
                .id(3L)
                .codeId(COW_CODE_ID_1)
                .birthDate(TEMP_DATE)
                .gender(Gender.FEMALE)
                .cowStatus(CowStatus.RAISING)
                .barnId(1L)
                .penId(2L)
                .momId(null)
                .dadId(null)
                .children(List.of())
                .feedConsumptions(List.of())
                .records(List.of())
                .unitPrice(null)
                .build();

        CowResponse cowResponse2 = CowResponse.builder()
                .id(4L)
                .codeId(COW_CODE_ID_1)
                .birthDate(TEMP_DATE)
                .gender(Gender.FEMALE)
                .cowStatus(CowStatus.RAISING)
                .barnId(1L)
                .penId(2L)
                .momId(null)
                .dadId(null)
                .children(List.of())
                .feedConsumptions(List.of())
                .records(List.of())
                .unitPrice(null)
                .build();

        List<CowResponse> response = List.of(cowResponse1,cowResponse2);
        //when //then
        when(service.findAll()).thenReturn(response);

        mockMvc.perform(get("/api/v0/cows"))
                .andDo(document(
                        "Cow-findAll",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("응답 데이터"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data[].codeId").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data[].birthDate").type(JsonFieldType.ARRAY).description("생일"),
                                fieldWithPath("data[].gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("data[].cowStatus").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("data[].barnId").type(JsonFieldType.NUMBER).description("축사 id"),
                                fieldWithPath("data[].penId").type(JsonFieldType.NUMBER).description("축사칸 id"),
                                fieldWithPath("data[].momId").type(JsonFieldType.NULL).description("엄마 소 id"),
                                fieldWithPath("data[].dadId").type(JsonFieldType.NULL).description("아빠 소 id"),
                                fieldWithPath("data[].children").type(JsonFieldType.ARRAY).description("자식 id"),
                                fieldWithPath("data[].records").type(JsonFieldType.ARRAY).description("기록 id"),
                                fieldWithPath("data[].feedConsumptions").type(JsonFieldType.ARRAY).description("먹이 소비 기록 id"),
                                fieldWithPath("data[].unitPrice").type(JsonFieldType.NULL).description("판매가")
                        )
                ));
    }

    @DisplayName("소 단건 조회")
    @Test
    void findByCodeId() throws Exception {
        CowResponse response = CowResponse.builder()
                .id(3L)
                .codeId(COW_CODE_ID_1)
                .birthDate(TEMP_DATE)
                .gender(Gender.FEMALE)
                .cowStatus(CowStatus.RAISING)
                .barnId(1L)
                .penId(2L)
                .momId(null)
                .dadId(null)
                .children(List.of())
                .feedConsumptions(List.of())
                .records(List.of())
                .unitPrice(null)
                .build();

        when(service.findByCodeId(any())).thenReturn(response);

        mockMvc.perform(get("/api/v0/cows/{codeId}",COW_CODE_ID_1))
                .andDo(document(
                        "Cow-findByCodeId",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.codeId").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.birthDate").type(JsonFieldType.ARRAY).description("생일"),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("data.cowStatus").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("data.barnId").type(JsonFieldType.NUMBER).description("축사 id"),
                                fieldWithPath("data.penId").type(JsonFieldType.NUMBER).description("축사칸 id"),
                                fieldWithPath("data.momId").type(JsonFieldType.NULL).description("엄마 소 id"),
                                fieldWithPath("data.dadId").type(JsonFieldType.NULL).description("아빠 소 id"),
                                fieldWithPath("data.children").type(JsonFieldType.ARRAY).description("자식 id"),
                                fieldWithPath("data.records").type(JsonFieldType.ARRAY).description("기록 id"),
                                fieldWithPath("data.feedConsumptions").type(JsonFieldType.ARRAY).description("먹이 소비 기록 id"),
                                fieldWithPath("data.unitPrice").type(JsonFieldType.NULL).description("판매가")
                        )
                ));
    }

}
