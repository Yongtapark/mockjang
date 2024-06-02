package myproject.mockjang.api.service.note_parser.simple;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.note_parser.mockjang.response.NoteParserResponse;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.simple.SimpleRecord;
import myproject.mockjang.domain.records.simple.SimpleRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SimpleNoteParserServiceTest extends IntegrationTestSupport {

  @Autowired
  private SimpleNoteParserService simpleNoteParserService;

  @Autowired
  private SimpleRecordRepository simpleRecordRepository;

  @DisplayName("엔터로 구분한 문자열을 입력받으면 각 regex에 해당되는 저장소에 저장된다.")
  @Test
  void parseNoteAndSaveRecordWithEnter() {
    //given
    String context =
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() + "[["
            + PARSER_PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() + "[["
            + PARSER_PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() + "[["
            + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1 + System.lineSeparator() + "[["
            + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_2 + System.lineSeparator() + "[["
            + PARSER_COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_2;

    SimpleNoteParserCreateServiceRequest request = SimpleNoteParserCreateServiceRequest.builder()
        .date(TEMP_DATE).recordType(RecordType.DAILY).names(new HashMap<>()).context(context)
        .build();

    //when
    simpleNoteParserService.parseNoteAndSaveRecord(request);

    //then
    List<SimpleRecord> simpleRecords = simpleRecordRepository.findAll();

    assertThat(simpleRecords).hasSize(6);
    SimpleRecord simpleRecord1 = simpleRecords.get(0);
    SimpleRecord simpleRecord2 = simpleRecords.get(1);
    SimpleRecord simpleRecord3 = simpleRecords.get(2);
    SimpleRecord simpleRecord4 = simpleRecords.get(3);
    SimpleRecord simpleRecord5 = simpleRecords.get(4);
    SimpleRecord simpleRecord6 = simpleRecords.get(5);
    assertThat(simpleRecord1.getCodeId()).isEqualTo(PARSER_BARN_CODE_ID_1);
    assertThat(simpleRecord1.getRecord()).isEqualTo(PARSER_BARN_NOTE_1);
    assertThat(simpleRecord2.getCodeId()).isEqualTo(PARSER_PEN_CODE_ID_1);
    assertThat(simpleRecord2.getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
    assertThat(simpleRecord3.getCodeId()).isEqualTo(PARSER_PEN_CODE_ID_1);
    assertThat(simpleRecord3.getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
    assertThat(simpleRecord4.getCodeId()).isEqualTo(PARSER_COW_CODE_ID_1);
    assertThat(simpleRecord4.getRecord()).isEqualTo(PARSER_COW_NOTE_1);
    assertThat(simpleRecord5.getCodeId()).isEqualTo(PARSER_COW_CODE_ID_1);
    assertThat(simpleRecord5.getRecord()).isEqualTo(PARSER_COW_NOTE_2);
    assertThat(simpleRecord6.getCodeId()).isEqualTo(PARSER_COW_CODE_ID_2);
    assertThat(simpleRecord6.getRecord()).isEqualTo(PARSER_COW_NOTE_2);
  }

  @DisplayName("엔터로 구분한 문자열을 입력받으면 작업 완료 후 이름들을 반환한다.")
  @Test
  void parseNoteAndSaveRecordWithEnterReturnNames() {
    //given
    String context =
        "[[" + PARSER_BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1 + System.lineSeparator() +
            "[[" + PARSER_COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_2 + System.lineSeparator() +
            "[[" + PARSER_COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_2;

    SimpleNoteParserCreateServiceRequest request = SimpleNoteParserCreateServiceRequest.builder()
        .date(TEMP_DATE).recordType(RecordType.DAILY).names(new HashMap<>()).context(context)
        .build();
    //when
    NoteParserResponse response = simpleNoteParserService.parseNoteAndSaveRecord(request);

    //then
    assertThat(response.getNames()).containsEntry(PARSER_COW_CODE_ID_1,2);
    assertThat(response.getNames()).containsEntry(PARSER_COW_CODE_ID_2,1);
    assertThat(response.getNames()).containsEntry(PARSER_PEN_CODE_ID_1,2);
    assertThat(response.getNames()).containsEntry(PARSER_BARN_CODE_ID_1,1);
  }
}