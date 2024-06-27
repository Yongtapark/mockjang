package myproject.mockjang.domain.note_parser.mockjang;

import static myproject.mockjang.domain.note_parser.NoteRegex.BARN;
import static myproject.mockjang.domain.note_parser.NoteRegex.COW;
import static myproject.mockjang.domain.note_parser.NoteRegex.PEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.note_parser.NoteRegex;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.note_parser.NoteFormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NoteParserV0Test extends IntegrationTestSupport {

    @Test
    @DisplayName("축사에 대한 노트를 저장하면 codeId,note로 구별되어 저장된다.")
    void extractAndSaveNotesWithOneBarnNote() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList = immutableMap.get(BARN);
        RecordAndCodeId recordAndCodeId = recordAndCodeIdList.get(0);
        Assertions.assertThat(BARN_CODE_ID_1).isEqualTo(recordAndCodeId.codeId());
        Assertions.assertThat(PARSER_BARN_NOTE_1).isEqualTo(recordAndCodeId.record());
    }

    @Test
    @DisplayName("축사칸에 대한 노트를 저장하면 codeId,note로 구별되어 저장된다.")
    void extractAndSaveNotesWithOnePenNote() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList = immutableMap.get(PEN);
        RecordAndCodeId recordAndCodeId = recordAndCodeIdList.get(0);
        Assertions.assertThat(recordAndCodeId.codeId()).isEqualTo(PEN_CODE_ID_1);
        Assertions.assertThat(recordAndCodeId.record()).isEqualTo(PARSER_PEN_NOTE_1);
    }

    @Test
    @DisplayName("소에 대한 노트를 저장하면 codeId,note로 구별되어 저장된다.")
    void extractAndSaveNotesWithOneCowNote() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList = immutableMap.get(COW);
        RecordAndCodeId recordAndCodeId = recordAndCodeIdList.get(0);

        Assertions.assertThat(recordAndCodeId.codeId()).isEqualTo(COW_CODE_ID_1);
        Assertions.assertThat(recordAndCodeId.record()).isEqualTo(PARSER_COW_NOTE_1);
    }

    @Test
    @DisplayName("노트를 단일 등록할 때, codeId 기입 후 ','를 입력하면 예외를 발생시킨다.")
    void extractAndSaveNotesWithOneNoteAddComma() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
        String wrongFormatContent = "[[" + BARN_CODE_ID_1 + ",]] " + PARSER_BARN_NOTE_1;

        //when //then
        Assertions.assertThatThrownBy(
                        () -> noteParserV0.extractAndSaveNotes(mockjangNoteContainer, wrongFormatContent))
                .isInstanceOf(NoteFormatException.class)
                .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
    }

    @Test
    @DisplayName("노트를 단일 등록할 때, 찾을 수 없는 형식의 codeId를 입력하면 예외를 발생시킨다.")
    void extractAndSaveNotesWithCantFindRegexForm() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
        String wrongFormatContent = "[[" + BARN_CODE_ID_1 + "@]] " + PARSER_BARN_NOTE_1;

        //when //then
        Assertions.assertThatThrownBy(
                        () -> noteParserV0.extractAndSaveNotes(mockjangNoteContainer, wrongFormatContent))
                .isInstanceOf(NoteFormatException.class)
                .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
    }

    @Test
    @DisplayName("노트를 단일 등록할 때, 찾을 수 없는 형식의 codeId를 입력하면 예외를 발생시킨다")
    void extractAndSaveNotesWithOnlySpaceCodeId() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
        String wrongFormatContent = "[[" + STRING_ONLY_SPACE + "]] " + PARSER_BARN_NOTE_1;

        //when //then
        Assertions.assertThatThrownBy(
                        () -> noteParserV0.extractAndSaveNotes(mockjangNoteContainer, wrongFormatContent))
                .isInstanceOf(NoteFormatException.class)
                .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
    }

    @Test
    @DisplayName("노트를 단일 등록할 때, 찾을 수 없는 형식의 codeId를 입력하면 예외를 발생시킨다")
    void extractAndSaveNotesWithEmptyCodeId() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
        String wrongFormatContent = "[[" + STRING_EMPTY + "]] " + PARSER_BARN_NOTE_1;

        //when //then
        Assertions.assertThatThrownBy(
                        () -> noteParserV0.extractAndSaveNotes(mockjangNoteContainer, wrongFormatContent))
                .isInstanceOf(NoteFormatException.class)
                .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
    }

    @Test
    @DisplayName("[codeId1,codeId2] 포맷을 사용하면 하나의 노트를 동일한 여러 그룹에 저장할 수 있다.")
    void extractAndSaveNotesWithCommaSameRegex() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "," + PARSER_BARN_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_1);

        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList = immutableMap.get(BARN);
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIdList.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIdList.get(1);

        //then
        Assertions.assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        Assertions.assertThat(recordAndCodeId2.codeId()).isEqualTo(PARSER_BARN_CODE_ID_2);
        Assertions.assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        Assertions.assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_BARN_NOTE_1);
    }

    @Test
    @DisplayName("서로 다른 그룹으로 ','를 붙여 저장을 시도하면 예외를 발생시킨다.")
    void extractAndSaveNotesWithCommaDifferentRegex() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
        String wrongFormatContext =
                "[[" + BARN_CODE_ID_1 + "," + COW_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_1;

        //then
        assertThatThrownBy(
                () -> noteParserV0.extractAndSaveNotes(mockjangNoteContainer, wrongFormatContext))
                .isInstanceOf(NoteFormatException.class)
                .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContext));
    }

    @Test
    @DisplayName("엔터로 구분하여 동일한 그룹의 여러 codeId와 노트를 저장한다.")
    void extractAndSaveNoteWithEnterSameGroup() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + PARSER_BARN_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_2);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList = immutableMap.get(BARN);
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIdList.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIdList.get(1);

        assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(recordAndCodeId2.codeId()).isEqualTo(PARSER_BARN_CODE_ID_2);
        assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_BARN_NOTE_2);
    }

    @Test
    @DisplayName("엔터로 구분하여 다른 그룹의 여러 codeId와 노트를 저장한다.")
    void extractAndSaveNoteWithEnterDifferentGroup() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList1 = immutableMap.get(BARN);
        List<RecordAndCodeId> recordAndCodeIdList2 = immutableMap.get(COW);
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIdList1.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIdList2.get(0);

        assertThat(recordAndCodeIdList1).hasSize(1);
        assertThat(recordAndCodeIdList2).hasSize(1);
        assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(recordAndCodeId2.codeId()).isEqualTo(COW_CODE_ID_1);
        assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_COW_NOTE_1);
    }

    @Test
    @DisplayName("NoteContainer 해시맵 필드를 조작하면 예외를 발생시킨다.")
    void extractAndSaveNoteWithModificationNoteContainer() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();

        assertThatThrownBy(() -> immutableMap.put(COW, new ArrayList<RecordAndCodeId>())).isInstanceOf(
                UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("NoteContainer 내부 해시맵에 저장되어있는 리스트를 수정하면 예외를 발생시킨다.")
    void extractAndSaveNoteWithModificationNoteAndIds() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        Map<NoteRegex, List<RecordAndCodeId>> immutableMap = mockjangNoteContainer.getImmutableMap();
        List<RecordAndCodeId> recordAndCodeIdList1 = immutableMap.get(BARN);

        assertThatThrownBy(() -> recordAndCodeIdList1.add(new RecordAndCodeId("sd", "ds"))).isInstanceOf(
                UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("같은 NoteRegex에 대해 서로 다른 두 줄이 독립적으로 처리되는지 검증한다.")
    void extractAndSaveNotesWithOneCodeIdAndMultiNotes() {
        //given
        NoteParserV0 noteParserV0 = new NoteParserV0();
        MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();

        String combinedNotes =
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_2;

        //when
        mockjangNoteContainer = noteParserV0.extractAndSaveNotes(mockjangNoteContainer, combinedNotes);

        //then
        List<RecordAndCodeId> notes = mockjangNoteContainer.getNotes(BARN);
        Assertions.assertThat(notes).hasSize(2);
        Assertions.assertThat(notes.get(0).record()).isEqualTo(PARSER_BARN_NOTE_1);
        Assertions.assertThat(notes.get(1).record()).isEqualTo(PARSER_BARN_NOTE_2);
    }

}