package myproject.mockjang.docs.note_parser.mockjang;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import myproject.mockjang.api.controller.note_parser.mockjang.NoteParserController;
import myproject.mockjang.api.controller.note_parser.mockjang.request.NoteParserCreateRequest;
import myproject.mockjang.api.service.note_parser.mockjang.NoteParserService;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.docs.RestDocsSupport;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

public class NoteParserControllerDocsTest extends RestDocsSupport {

    private final NoteParserService service = mock(NoteParserService.class);

    @Override
    protected Object initController() {
        return new NoteParserController(service);
    }

    @DisplayName("신규 일지를 저장한다.")
    @Test
    void createRecords() throws Exception {
        // given
        LocalDateTime date = LocalDateTime.of(2024, 5, 31, 00, 00);
        HashMap<String, Integer> names = new HashMap<>();
        names.put(BARN_CODE_ID_1, 1);
        RecordParserResponse response = RecordParserResponse.builder().names(names).build();

        String context = "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1;
        NoteParserCreateRequest request = NoteParserCreateRequest.builder()
                .context(context)
                .date(date)
                .recordType(RecordType.DAILY)
                .build();

        // when // then
        when(service.parseNoteAndSaveRecord(any())).thenReturn(response);
        mockMvc.perform(
                        post("/api/v0/records/new")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(document("noteParser-new",
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
