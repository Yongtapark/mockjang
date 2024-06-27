package myproject.mockjang.api.service.note_parser.simple;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserUploadTempDataServiceRequest;
import myproject.mockjang.domain.note_parser.mockjang.RecordAndCodeId;
import myproject.mockjang.domain.note_parser.simple.SimpleRecordContainer;
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
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() + "[["
                        + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() + "[["
                        + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() + "[["
                        + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1 + System.lineSeparator() + "[["
                        + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_2 + System.lineSeparator() + "[["
                        + COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_2;

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
        assertThat(simpleRecord1.getCodeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(simpleRecord1.getRecord()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(simpleRecord2.getCodeId()).isEqualTo(PEN_CODE_ID_1);
        assertThat(simpleRecord2.getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
        assertThat(simpleRecord3.getCodeId()).isEqualTo(PEN_CODE_ID_1);
        assertThat(simpleRecord3.getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
        assertThat(simpleRecord4.getCodeId()).isEqualTo(COW_CODE_ID_1);
        assertThat(simpleRecord4.getRecord()).isEqualTo(PARSER_COW_NOTE_1);
        assertThat(simpleRecord5.getCodeId()).isEqualTo(COW_CODE_ID_1);
        assertThat(simpleRecord5.getRecord()).isEqualTo(PARSER_COW_NOTE_2);
        assertThat(simpleRecord6.getCodeId()).isEqualTo(COW_CODE_ID_2);
        assertThat(simpleRecord6.getRecord()).isEqualTo(PARSER_COW_NOTE_2);
    }

    @DisplayName("각 라인 사이에 빈 라인이 있어도 정상 동작한다.")
    @Test
    void parseNoteAndSaveRecordsBetweenSpaceLine() {
        //given
        String context =
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator()
                        + System.lineSeparator() + "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1;

        SimpleNoteParserCreateServiceRequest request = SimpleNoteParserCreateServiceRequest.builder()
                .date(TEMP_DATE).recordType(RecordType.DAILY).names(new HashMap<>()).context(context)
                .build();

        //when
        simpleNoteParserService.parseNoteAndSaveRecord(request);

        //then
        List<SimpleRecord> simpleRecords = simpleRecordRepository.findAll();

        assertThat(simpleRecords).hasSize(2);
        SimpleRecord simpleRecord1 = simpleRecords.get(0);
        SimpleRecord simpleRecord2 = simpleRecords.get(1);

        assertThat(simpleRecord1.getCodeId()).isEqualTo(BARN_CODE_ID_1);
        assertThat(simpleRecord1.getRecord()).isEqualTo(PARSER_BARN_NOTE_1);
        assertThat(simpleRecord2.getCodeId()).isEqualTo(PEN_CODE_ID_1);
        assertThat(simpleRecord2.getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
    }

    @DisplayName("엔터로 구분한 문자열을 입력받으면 작업 완료 후 이름들을 반환한다.")
    @Test
    void parseNoteAndSaveRecordWithEnterReturnNames() {
        //given
        String context =
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_2 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_2;

        SimpleNoteParserCreateServiceRequest request = SimpleNoteParserCreateServiceRequest.builder()
                .date(TEMP_DATE).recordType(RecordType.DAILY).names(new HashMap<>()).context(context)
                .build();
        //when
        RecordParserResponse response = simpleNoteParserService.parseNoteAndSaveRecord(request);

        //then
        assertThat(response.getNames()).containsEntry(COW_CODE_ID_1, 2);
        assertThat(response.getNames()).containsEntry(COW_CODE_ID_2, 1);
        assertThat(response.getNames()).containsEntry(PEN_CODE_ID_1, 2);
        assertThat(response.getNames()).containsEntry(BARN_CODE_ID_1, 1);
    }

    @DisplayName("임시로 저장된 기록 목록을 db에 저장한다.")
    @Test
    void uploadTempContents() {
        //given
        RecordAndCodeId recordAndCodeId1 = new RecordAndCodeId(COW_CODE_ID_1, PARSER_COW_NOTE_1);
        RecordAndCodeId recordAndCodeId2 = new RecordAndCodeId(COW_CODE_ID_2, PARSER_COW_NOTE_2);
        SimpleRecordContainer simpleRecordContainer = SimpleRecordContainer.builder()
                .notes(List.of(recordAndCodeId1,
                        recordAndCodeId2)).recordType(RecordType.DAILY).date(TEMP_DATE).build();

        SimpleNoteParserUploadTempDataServiceRequest request = SimpleNoteParserUploadTempDataServiceRequest.builder()
                .simpleRecordContainer(simpleRecordContainer).build();
        //when
        simpleNoteParserService.uploadTempRecords(request);

        //then
        List<SimpleRecord> simpleRecords = simpleRecordRepository.findAll();
        assertThat(simpleRecords.getFirst().getCodeId()).isEqualTo(recordAndCodeId1.codeId());
        assertThat(simpleRecords.getFirst().getRecord()).isEqualTo(recordAndCodeId1.record());
        assertThat(simpleRecords.getLast().getCodeId()).isEqualTo(recordAndCodeId2.codeId());
        assertThat(simpleRecords.getLast().getRecord()).isEqualTo(recordAndCodeId2.record());
    }
}