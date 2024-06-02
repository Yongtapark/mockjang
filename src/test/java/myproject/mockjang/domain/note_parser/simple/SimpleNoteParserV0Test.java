package myproject.mockjang.domain.note_parser.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.NoteAndCodeId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class SimpleNoteParserV0Test extends IntegrationTestSupport {
  @Autowired
  private NoteParser<SimpleNoteContainer> noteParser;
  @DisplayName("문자열을 받아 codeAndId로 변환한다.")
  @Test
  void extractAndSaveNotes() {
    //given
    SimpleNoteContainer noteContainer = new SimpleNoteContainer();

    //when
    noteContainer = noteParser.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1);

    //then
    List<NoteAndCodeId> noteAndCodeIds = noteContainer.getImmutable();
    NoteAndCodeId noteAndCodeId = noteAndCodeIds.get(0);
    Assertions.assertThat(PARSER_BARN_CODE_ID_1).isEqualTo(noteAndCodeId.codeId());
    Assertions.assertThat(PARSER_BARN_NOTE_1).isEqualTo(noteAndCodeId.note());
  }

  @Test
  @DisplayName("엔터로 구분하여 동일한 그룹의 여러 codeId와 노트를 저장한다.")
  void extractAndSaveNoteWithEnterSameGroup() {
    //given
    SimpleNoteContainer noteContainer = new SimpleNoteContainer();

    //when
    noteContainer = noteParser.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_BARN_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_2);

    //then
    List<NoteAndCodeId> noteAndCodeIds = noteContainer.getImmutable();
    NoteAndCodeId noteAndCodeId1 = noteAndCodeIds.get(0);
    NoteAndCodeId noteAndCodeId2 = noteAndCodeIds.get(1);

    assertThat(noteAndCodeId1.codeId()).isEqualTo(PARSER_BARN_CODE_ID_1);
    assertThat(noteAndCodeId1.note()).isEqualTo(PARSER_BARN_NOTE_1);
    assertThat(noteAndCodeId2.codeId()).isEqualTo(PARSER_BARN_CODE_ID_2);
    assertThat(noteAndCodeId2.note()).isEqualTo(PARSER_BARN_NOTE_2);
  }

  @Test
  @DisplayName("엔터로 구분하여 다른 그룹의 여러 codeId와 노트를 저장한다.")
  void extractAndSaveNoteWithEnterDifferentGroup() {
    //given
    SimpleNoteContainer mockjangNoteContainer = new SimpleNoteContainer();

    //when
    mockjangNoteContainer = noteParser.extractAndSaveNotes(mockjangNoteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

    //then
    List<NoteAndCodeId> noteAndCodeIds = mockjangNoteContainer.getImmutable();
    NoteAndCodeId noteAndCodeId1 = noteAndCodeIds.get(0);
    NoteAndCodeId noteAndCodeId2 = noteAndCodeIds.get(1);

    assertThat(noteAndCodeIds).hasSize(2);
    assertThat(noteAndCodeId1.codeId()).isEqualTo(PARSER_BARN_CODE_ID_1);
    assertThat(noteAndCodeId1.note()).isEqualTo(PARSER_BARN_NOTE_1);
    assertThat(noteAndCodeId2.codeId()).isEqualTo(PARSER_COW_CODE_ID_1);
    assertThat(noteAndCodeId2.note()).isEqualTo(PARSER_COW_NOTE_1);
  }

  @Test
  @DisplayName("NoteContainer 리스트를 조작하면 예외를 발생시킨다.")
  void extractAndSaveNoteWithModificationNoteContainer() {
    //given
    SimpleNoteContainer noteContainer = new SimpleNoteContainer();

    //when
    noteContainer = noteParser.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

    //then
    List<NoteAndCodeId> noteAndCodeIds = noteContainer.getImmutable();

    assertThatThrownBy(() -> noteAndCodeIds.add(new NoteAndCodeId(null,null))).isInstanceOf(
        UnsupportedOperationException.class);
  }
}