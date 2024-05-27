package myproject.mockjang.domain.note_parser;

import static myproject.mockjang.domain.note_parser.NoteRegex.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import myproject.mockjang.IntegrationTestSupport;
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
    NoteContainer noteContainer = new NoteContainer();

    //when
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1);

    //then
    Map<NoteRegex, List<NoteAndId>> immutableMap = noteContainer.getImmutableMap();
    List<NoteAndId> noteAndIdList = immutableMap.get(BARN);
    NoteAndId noteAndId = noteAndIdList.get(0);
    Assertions.assertThat(PARSER_BARN_CODE_ID_1).isEqualTo(noteAndId.codeId());
    Assertions.assertThat(PARSER_BARN_NOTE_1).isEqualTo(noteAndId.note());
  }

  @Test
  @DisplayName("축사칸에 대한 노트를 저장하면 codeId,note로 구별되어 저장된다.")
  void extractAndSaveNotesWithOnePenNote() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();

    //when
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1);

    //then
    Map<NoteRegex, List<NoteAndId>> immutableMap = noteContainer.getImmutableMap();
    List<NoteAndId> noteAndIdList = immutableMap.get(PEN);
    NoteAndId noteAndId = noteAndIdList.get(0);
    Assertions.assertThat(noteAndId.codeId()).isEqualTo(PARSER_PEN_CODE_ID_1);
    Assertions.assertThat(noteAndId.note()).isEqualTo(PARSER_PEN_NOTE_1);
  }

  @Test
  @DisplayName("소에 대한 노트를 저장하면 codeId,note로 구별되어 저장된다.")
  void extractAndSaveNotesWithOneCowNote() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();

    //when
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

    //then
    Map<NoteRegex, List<NoteAndId>> immutableMap = noteContainer.getImmutableMap();
    List<NoteAndId> noteAndIdList = immutableMap.get(COW);
    NoteAndId noteAndId = noteAndIdList.get(0);

    Assertions.assertThat(noteAndId.codeId()).isEqualTo(PARSER_COW_CODE_ID_1);
    Assertions.assertThat(noteAndId.note()).isEqualTo(PARSER_COW_NOTE_1);
  }

  @Test
  @DisplayName("노트를 단일 등록할 때, codeId 기입 후 ','를 입력하면 예외를 발생시킨다.")
  void extractAndSaveNotesWithOneNoteAddComma() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();
    String wrongFormatContent = "[[" + PARSER_BARN_CODE_ID_1 + ",]] " + PARSER_BARN_NOTE_1;

    //when //then
    Assertions.assertThatThrownBy(
            () -> noteParserV0.extractAndSaveNotes(noteContainer, wrongFormatContent))
        .isInstanceOf(NoteFormatException.class)
        .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
  }

  @Test
  @DisplayName("노트를 단일 등록할 때, 찾을 수 없는 형식의 codeId를 입력하면 예외를 발생시킨다.")
  void extractAndSaveNotesWithCantFindRegexForm() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();
    String wrongFormatContent = "[[" + PARSER_BARN_CODE_ID_1 + "@]] " + PARSER_BARN_NOTE_1;

    //when //then
    Assertions.assertThatThrownBy(
            () -> noteParserV0.extractAndSaveNotes(noteContainer, wrongFormatContent))
        .isInstanceOf(NoteFormatException.class)
        .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
  }

  @Test
  @DisplayName("노트를 단일 등록할 때, 찾을 수 없는 형식의 codeId를 입력하면 예외를 발생시킨다")
  void extractAndSaveNotesWithOnlySpaceCodeId() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();
    String wrongFormatContent = "[[" + STRING_ONLY_SPACE + "]] " + PARSER_BARN_NOTE_1;

    //when //then
    Assertions.assertThatThrownBy(
            () -> noteParserV0.extractAndSaveNotes(noteContainer, wrongFormatContent))
        .isInstanceOf(NoteFormatException.class)
        .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
  }

  @Test
  @DisplayName("노트를 단일 등록할 때, 찾을 수 없는 형식의 codeId를 입력하면 예외를 발생시킨다")
  void extractAndSaveNotesWithEmptyCodeId() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();
    String wrongFormatContent = "[[" + STRING_EMPTY + "]] " + PARSER_BARN_NOTE_1;

    //when //then
    Assertions.assertThatThrownBy(
            () -> noteParserV0.extractAndSaveNotes(noteContainer, wrongFormatContent))
        .isInstanceOf(NoteFormatException.class)
        .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContent));
  }

  @Test
  @DisplayName("[codeId1,codeId2] 포맷을 사용하면 하나의 노트를 동일한 여러 그룹에 저장할 수 있다.")
  void extractAndSaveNotesWithCommaSameRegex() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "," + PARSER_BARN_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_1);

    Map<NoteRegex, List<NoteAndId>> immutableMap = noteContainer.getImmutableMap();
    List<NoteAndId> noteAndIdList = immutableMap.get(BARN);
    NoteAndId noteAndId1 = noteAndIdList.get(0);
    NoteAndId noteAndId2 = noteAndIdList.get(1);

    //then
    Assertions.assertThat(noteAndId1.codeId()).isEqualTo(PARSER_BARN_CODE_ID_1);
    Assertions.assertThat(noteAndId2.codeId()).isEqualTo(PARSER_BARN_CODE_ID_2);
    Assertions.assertThat(noteAndId1.note()).isEqualTo(PARSER_BARN_NOTE_1);
    Assertions.assertThat(noteAndId2.note()).isEqualTo(PARSER_BARN_NOTE_1);
  }

  @Test
  @DisplayName("서로 다른 그룹으로 ','를 붙여 저장을 시도하면 예외를 발생시킨다.")
  void extractAndSaveNotesWithCommaDifferentRegex() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();
    String wrongFormatContext =
        "[[" + PARSER_BARN_CODE_ID_1 + "," + PARSER_COW_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_1;

    //then
    assertThatThrownBy(() -> noteParserV0.extractAndSaveNotes(noteContainer, wrongFormatContext))
        .isInstanceOf(NoteFormatException.class)
        .hasMessage(Exceptions.DOMAIN_NOTE_FORMAT.formatMessage(wrongFormatContext));
  }

  @Test
  @DisplayName("엔터로 구분하여 동일한 그룹의 여러 codeId와 노트를 저장한다.")
  void extractAndSaveNoteWithEnterSameGroup() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();

    //when
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_BARN_CODE_ID_2 + "]] " + PARSER_BARN_NOTE_2);

    //then
    Map<NoteRegex, List<NoteAndId>> immutableMap = noteContainer.getImmutableMap();
    List<NoteAndId> noteAndIdList = immutableMap.get(BARN);
    NoteAndId noteAndId1 = noteAndIdList.get(0);
    NoteAndId noteAndId2 = noteAndIdList.get(1);

    assertThat(noteAndId1.codeId()).isEqualTo(PARSER_BARN_CODE_ID_1);
    assertThat(noteAndId1.note()).isEqualTo(PARSER_BARN_NOTE_1);
    assertThat(noteAndId2.codeId()).isEqualTo(PARSER_BARN_CODE_ID_2);
    assertThat(noteAndId2.note()).isEqualTo(PARSER_BARN_NOTE_2);
  }

  @Test
  @DisplayName("엔터로 구분하여 다른 그룹의 여러 codeId와 노트를 저장한다.")
  void extractAndSaveNoteWithEnterDifferentGroup() {
    //given
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();

    //when
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer,
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1);

    //then
    Map<NoteRegex, List<NoteAndId>> immutableMap = noteContainer.getImmutableMap();
    List<NoteAndId> noteAndIdList1 = immutableMap.get(BARN);
    List<NoteAndId> noteAndIdList2 = immutableMap.get(COW);
    NoteAndId noteAndId1 = noteAndIdList1.get(0);
    NoteAndId noteAndId2 = noteAndIdList2.get(0);

    assertThat(noteAndIdList1).hasSize(1);
    assertThat(noteAndIdList2).hasSize(1);
    assertThat(noteAndId1.codeId()).isEqualTo(PARSER_BARN_CODE_ID_1);
    assertThat(noteAndId1.note()).isEqualTo(PARSER_BARN_NOTE_1);
    assertThat(noteAndId2.codeId()).isEqualTo(PARSER_COW_CODE_ID_1);
    assertThat(noteAndId2.note()).isEqualTo(PARSER_COW_NOTE_1);
  }

  @Test
  @DisplayName("같은 NoteRegex에 대해 서로 다른 두 줄이 독립적으로 처리되는지 검증한다.")
  void test5_separateProcessing_forSameNoteRegex() {
    final String ID1 = "1번축사";
    final String NOTE1 = "첫 번째 노트 내용";
    final String NOTE2 = "두 번째 노트 내용";
    NoteParserV0 noteParserV0 = new NoteParserV0();
    NoteContainer noteContainer = new NoteContainer();

    String combinedNotes = "[[" + ID1 + "]] " + NOTE1 + System.lineSeparator() +
        "[[" + ID1 + "]] " + NOTE2;
    noteContainer = noteParserV0.extractAndSaveNotes(noteContainer, combinedNotes);

    List<NoteAndId> notes = noteContainer.getNotes(BARN);
    Assertions.assertThat(notes).hasSize(2);
    Assertions.assertThat(notes.get(0).note()).isEqualTo(NOTE1);
    Assertions.assertThat(notes.get(1).note()).isEqualTo(NOTE2);
  }

}