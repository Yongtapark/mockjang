package myproject.mockjang.api.service.note_parser.mockjang;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.note_parser.mockjang.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecord;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecordRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordRepository;
import myproject.mockjang.domain.records.mockjang.pen.PenRecord;
import myproject.mockjang.domain.records.mockjang.pen.PenRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class NoteParserServiceTest extends IntegrationTestSupport {

    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private PenRepository penRepository;
    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private BarnRecordRepository barnRecordRepository;
    @Autowired
    private PenRecordRepository penRecordRepository;
    @Autowired
    private CowRecordRepository cowRecordRepository;

    @Autowired
    private NoteParserService noteParserService;


    @DisplayName("엔터로 구분한 문자열을 입력받으면 각 regex에 해당되는 저장소에 저장된다.")
    @Test
    void parseNoteAndSaveRecordWithEnter() {
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);

        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);

        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow1.registerUpperGroup(pen);
        cow1.registerBarn(barn);
        cowRepository.save(cow1);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow2.registerUpperGroup(pen);
        cow2.registerBarn(barn);
        cowRepository.save(cow2);
        //given
        String context =
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_2 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_2;

        NoteParserCreateServiceRequest request = NoteParserCreateServiceRequest.builder()
                .date(TEMP_DATE).recordType(RecordType.DAILY).names(new HashMap<>()).context(context)
                .build();

        //when
        noteParserService.parseNoteAndSaveRecord(request);

        //then
        List<BarnRecord> allByBarnCodeId = barnRecordRepository.findAllByBarn_CodeId(
                BARN_CODE_ID_1);
        List<PenRecord> allByPenCodeId = penRecordRepository.findAllByPen_CodeId(PEN_CODE_ID_1);
        List<CowRecord> allByCowCodeId1 = cowRecordRepository.findAllByCow_CodeId(COW_CODE_ID_1);
        List<CowRecord> allByCowCodeId2 = cowRecordRepository.findAllByCow_CodeId(COW_CODE_ID_2);

        assertThat(allByBarnCodeId).hasSize(1);
        assertThat(allByBarnCodeId.getFirst().getBarn()).isEqualTo(barn);
        assertThat(allByBarnCodeId.getFirst().getRecord()).isEqualTo(PARSER_BARN_NOTE_1);

        assertThat(allByPenCodeId).hasSize(2);
        assertThat(allByPenCodeId.getFirst().getPen()).isEqualTo(pen);
        assertThat(allByPenCodeId.getLast().getPen()).isEqualTo(pen);
        assertThat(allByPenCodeId.getFirst().getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
        assertThat(allByPenCodeId.getLast().getRecord()).isEqualTo(PARSER_PEN_NOTE_1);

        assertThat(allByCowCodeId1).hasSize(2);
        assertThat(allByCowCodeId1.getFirst().getCow()).isEqualTo(cow1);
        assertThat(allByCowCodeId1.getFirst().getRecord()).isEqualTo(PARSER_COW_NOTE_1);
        assertThat(allByCowCodeId1.getLast().getRecord()).isEqualTo(PARSER_COW_NOTE_2);
        assertThat(allByCowCodeId2).hasSize(1);
        assertThat(allByCowCodeId2.getFirst().getCow()).isEqualTo(cow2);
        assertThat(allByCowCodeId2.getFirst().getRecord()).isEqualTo(PARSER_COW_NOTE_2);
    }

    @DisplayName("엔터로 구분한 문자열을 입력받으면 작업 완료 후 이름들을 반환한다.")
    @Test
    void parseNoteAndSaveRecordWithEnterReturnNames() {
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);

        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);

        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow1.registerUpperGroup(pen);
        cow1.registerBarn(barn);
        cowRepository.save(cow1);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow2.registerUpperGroup(pen);
        cow2.registerBarn(barn);
        cowRepository.save(cow2);
        //given
        String context =
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "]] " + PARSER_PEN_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_1 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "]] " + PARSER_COW_NOTE_2 + System.lineSeparator() +
                        "[[" + COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_2;

        //when
        NoteParserCreateServiceRequest request = NoteParserCreateServiceRequest.builder()
                .names(new HashMap<>()).context(context).recordType(RecordType.DAILY).date(TEMP_DATE)
                .build();
        RecordParserResponse response = noteParserService.parseNoteAndSaveRecord(request);

        //then
        assertThat(response.getNames()).containsEntry(COW_CODE_ID_1, 2);
        assertThat(response.getNames()).containsEntry(COW_CODE_ID_2, 1);
        assertThat(response.getNames()).containsEntry(PEN_CODE_ID_1, 2);
        assertThat(response.getNames()).containsEntry(BARN_CODE_ID_1, 1);
    }

    @DisplayName("',' 구분한 문자열을 입력받으면 각 regex에 해당되는 저장소에 저장된다.")
    @Test
    void parseNoteAndSaveRecordWithComma() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 28, 12, 43);
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);

        Pen pen1 = Pen.createPen(PEN_CODE_ID_1);
        pen1.registerUpperGroup(barn);
        penRepository.save(pen1);
        Pen pen2 = Pen.createPen(PEN_CODE_ID_2);
        pen2.registerUpperGroup(barn);
        penRepository.save(pen2);

        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, dateTime);
        cow1.registerUpperGroup(pen1);
        cow1.registerBarn(barn);
        cowRepository.save(cow1);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, dateTime);
        cow2.registerUpperGroup(pen1);
        cow2.registerBarn(barn);
        cowRepository.save(cow2);
        //given
        String context =
                "[[" + BARN_CODE_ID_1 + "]] " + PARSER_BARN_NOTE_1 + System.lineSeparator() +
                        "[[" + PEN_CODE_ID_1 + "," + PEN_CODE_ID_2 + "]] " + PARSER_PEN_NOTE_1
                        + System.lineSeparator() +
                        "[[" + COW_CODE_ID_1 + "," + COW_CODE_ID_2 + "]] " + PARSER_COW_NOTE_1;

        //when
        NoteParserCreateServiceRequest request = NoteParserCreateServiceRequest.builder()
                .names(new HashMap<>()).context(context).date(TEMP_DATE).recordType(RecordType.DAILY)
                .build();
        noteParserService.parseNoteAndSaveRecord(request);

        //then
        List<BarnRecord> allByBarnCodeId = barnRecordRepository.findAllByBarn_CodeId(
                BARN_CODE_ID_1);
        List<PenRecord> allByPenCodeId1 = penRecordRepository.findAllByPen_CodeId(PEN_CODE_ID_1);
        List<PenRecord> allByPenCodeId2 = penRecordRepository.findAllByPen_CodeId(PEN_CODE_ID_2);
        List<CowRecord> allByCowCodeId1 = cowRecordRepository.findAllByCow_CodeId(COW_CODE_ID_1);
        List<CowRecord> allByCowCodeId2 = cowRecordRepository.findAllByCow_CodeId(COW_CODE_ID_2);

        assertThat(allByBarnCodeId).hasSize(1);
        assertThat(allByBarnCodeId.getFirst().getBarn()).isEqualTo(barn);
        assertThat(allByBarnCodeId.getFirst().getRecord()).isEqualTo(PARSER_BARN_NOTE_1);

        assertThat(allByPenCodeId1).hasSize(1);
        assertThat(allByPenCodeId1.getFirst().getPen()).isEqualTo(pen1);
        assertThat(allByPenCodeId1.getFirst().getRecord()).isEqualTo(PARSER_PEN_NOTE_1);
        assertThat(allByPenCodeId1).hasSize(1);
        assertThat(allByPenCodeId2.getFirst().getPen()).isEqualTo(pen2);
        assertThat(allByPenCodeId2.getFirst().getRecord()).isEqualTo(PARSER_PEN_NOTE_1);

        assertThat(allByCowCodeId1).hasSize(1);
        assertThat(allByCowCodeId1.getFirst().getCow()).isEqualTo(cow1);
        assertThat(allByCowCodeId1.getFirst().getRecord()).isEqualTo(PARSER_COW_NOTE_1);
        assertThat(allByCowCodeId2).hasSize(1);
        assertThat(allByCowCodeId2.getFirst().getCow()).isEqualTo(cow2);
        assertThat(allByCowCodeId2.getFirst().getRecord()).isEqualTo(PARSER_COW_NOTE_1);

    }

}