package myproject.mockjang.domain.note_parser.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.RecordAndCodeId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class SimpleNoteParserV0Test extends IntegrationTestSupport {

    @Autowired
    private NoteParser<SimpleRecordContainer> noteParser;

    @DisplayName("문자열을 받아 codeAndId로 변환한다.")
    @Test
    void extractAndSaveNotes() {
        //given
        SimpleRecordContainer noteContainer = new SimpleRecordContainer();

        //when
        noteContainer = noteParser.extractAndSaveNotes(noteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1);

        //then
        List<RecordAndCodeId> recordAndCodeIds = noteContainer.getNotes();
        RecordAndCodeId recordAndCodeId = recordAndCodeIds.get(0);
        Assertions.assertThat(BARN_CODE_ID_1).isEqualTo(recordAndCodeId.codeId());
        Assertions.assertThat(PARSER_BARN_NOTE_1).isEqualTo(recordAndCodeId.record());
    }

    @Test
    @DisplayName("엔터로 구분하여 동일한 그룹의 여러 codeId와 노트를 저장한다.")
    void extractAndSaveNoteWithEnterSameGroup() {
        //given
        SimpleRecordContainer noteContainer = new SimpleRecordContainer();

        //when
        noteContainer = noteParser.extractAndSaveNotes(noteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + PARSER_BARN_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_2);

        //then
        List<RecordAndCodeId> recordAndCodeIds = noteContainer.getNotes();
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIds.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIds.get(1);

        assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(recordAndCodeId2.codeId()).isEqualTo(PARSER_BARN_CODE_ID_2);
        assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_BARN_NOTE_2);
    }

    @Test
    @DisplayName("엔터로 구분하여 다른 그룹의 여러 codeId와 노트를 저장한다.")
    void extractAndSaveNoteWithEnterDifferentGroup() {
        //given
        SimpleRecordContainer mockjangNoteContainer = new SimpleRecordContainer();

        //when
        mockjangNoteContainer = noteParser.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        List<RecordAndCodeId> recordAndCodeIds = mockjangNoteContainer.getNotes();
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIds.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIds.get(1);

        assertThat(recordAndCodeIds).hasSize(2);
        assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(recordAndCodeId2.codeId()).isEqualTo(COW_CODE_ID_1);
        assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_COW_NOTE_1);
    }

    @Test
    @DisplayName("엔터를 한번 더 쳐서 입력해도 기록은 정상 입력된다.")
    void extractAndSaveNoteWithEmptyLine() {
        //given
        SimpleRecordContainer mockjangNoteContainer = new SimpleRecordContainer();

        //when
        mockjangNoteContainer = noteParser.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator()
                        + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        List<RecordAndCodeId> recordAndCodeIds = mockjangNoteContainer.getNotes();
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIds.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIds.get(1);

        assertThat(recordAndCodeIds).hasSize(2);
        assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(recordAndCodeId2.codeId()).isEqualTo(COW_CODE_ID_1);
        assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_COW_NOTE_1);
    }

    @Test
    @DisplayName("//를 입력 하면 주석 처리를 할 수 있다.")
    void extractAndSaveNoteWithAnnotation() {
        //given
        SimpleRecordContainer mockjangNoteContainer = new SimpleRecordContainer();

        //when
        mockjangNoteContainer = noteParser.extractAndSaveNotes(mockjangNoteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator()
                        + System.lineSeparator() + ANNOTATION_TEMP + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        List<RecordAndCodeId> recordAndCodeIds = mockjangNoteContainer.getNotes();
        RecordAndCodeId recordAndCodeId1 = recordAndCodeIds.get(0);
        RecordAndCodeId recordAndCodeId2 = recordAndCodeIds.get(1);

        assertThat(recordAndCodeIds).hasSize(2);
        assertThat(recordAndCodeId1.codeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(recordAndCodeId1.record()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(recordAndCodeId2.codeId()).isEqualTo(COW_CODE_ID_1);
        assertThat(recordAndCodeId2.record()).isEqualTo(PARSER_COW_NOTE_1);
    }


    @Test
    @DisplayName("NoteContainer 리스트를 조작하면 예외를 발생시킨다.")
    void extractAndSaveNoteWithModificationNoteContainer() {
        //given
        SimpleRecordContainer noteContainer = new SimpleRecordContainer();

        //when
        noteContainer = noteParser.extractAndSaveNotes(noteContainer,
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

        //then
        List<RecordAndCodeId> recordAndCodeIds = noteContainer.getNotes();

        assertThatThrownBy(() -> recordAndCodeIds.add(new RecordAndCodeId(null, null))).isInstanceOf(
                UnsupportedOperationException.class);
    }
}