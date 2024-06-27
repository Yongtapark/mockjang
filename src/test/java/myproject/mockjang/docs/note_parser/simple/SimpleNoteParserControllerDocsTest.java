package myproject.mockjang.docs.note_parser.simple;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import myproject.mockjang.api.controller.note_parser.simple.SimpleNoteParserController;
import myproject.mockjang.api.controller.note_parser.simple.request.SimpleNoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.api.service.note_parser.simple.SimpleNoteParserService;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class SimpleNoteParserControllerDocsTest extends RestDocsSupport {

    private final SimpleNoteParserService service = mock(SimpleNoteParserService.class);

    @Override
    protected Object initController() {
        return new SimpleNoteParserController(service);
    }

    @DisplayName("문자열을 파싱하여 저장하는 api")
    @Test
    void createRecords() throws Exception {

        String context = "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1;
        SimpleNoteParserCreateRequest request = SimpleNoteParserCreateRequest.builder().context(context)
                .date(TEMP_DATE).recordType(RecordType.DAILY).build();

        HashMap<String, Integer> names = new HashMap<>();
        names.put(BARN_CODE_ID_1, 1);
        RecordParserResponse response = RecordParserResponse.builder().names(names).build();

        given(service.parseNoteAndSaveRecord(any(SimpleNoteParserCreateServiceRequest.class)))
                .willReturn(response);

        mockMvc.perform(
                        post("/api/v0/simple-parser/new").content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("simpleParser-new",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("context").type(JsonFieldType.STRING)
                                        .description("기록 내용"),
                                fieldWithPath("recordType").type(JsonFieldType.STRING)
                                        .description("기록 유형"),
                                fieldWithPath("date").type(JsonFieldType.ARRAY)
                                        .description("기록 날짜")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER)
                                        .description("코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING)
                                        .description("상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING)
                                        .description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT)
                                        .description("응답 데이터"),
                                fieldWithPath("data.names").type(JsonFieldType.OBJECT)
                                        .description("이름"),
                                fieldWithPath("data.names.*").type(JsonFieldType.NUMBER).description("저장 완료된 글의 수")

                        )
                ));
    }
}
