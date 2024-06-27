package myproject.mockjang.domain.records.mockjang.cow;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CowRecordRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private PenRepository penRepository;
    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private CowRecordRepository cowRecordRepository;

    @DisplayName("단일 메모를 입력하면 소의 메모 리스트에 저장된다.")
    @Test
    void writeOneRecord() {
        //given
        Barn barn = Barn.builder().codeId(BARN_CODE_ID_1).build();
        barnRepository.save(barn);
        Pen pen = Pen.builder().codeId(PEN_CODE_ID_1).barn(barn).build();
        penRepository.save(pen);
        Cow cow = Cow.builder().codeId(COW_CODE_ID_1).build();
        cow.registerUpperGroup(pen);
        cow.registerBarn(barn);
        cowRepository.save(cow);
        CowRecord cowRecord1 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
        CowRecord cowRecord2 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);

        //when
        cowRecord1.recordMemo("test1");
        cowRecord2.recordMemo("test2");
        cowRecordRepository.save(cowRecord1);
        cowRecordRepository.save(cowRecord2);
        Cow findCow = cowRepository.findById(cow.getId()).orElseThrow();
        List<CowRecord> records = findCow.getRecords();

        //then
        assertThat(records).hasSize(2);
        assertThat(records).contains(cowRecord1, cowRecord2);
    }

    @DisplayName("해당 이름을 가진 축사의 기록리스트를 반환한다.")
    @Test
    void findAllByBarn_CodeId() {
        Barn barn = Barn.builder().codeId(BARN_CODE_ID_1).build();
        barnRepository.save(barn);
        Pen pen = Pen.builder().codeId(PEN_CODE_ID_1).barn(barn).build();
        penRepository.save(pen);
        Cow cow = Cow.builder().codeId(COW_CODE_ID_1).build();
        cow.registerUpperGroup(pen);
        cow.registerBarn(barn);
        cowRepository.save(cow);
        CowRecord cowRecord1 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
        CowRecord cowRecord2 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
        cowRecord1.recordMemo(MEMO_1);
        cowRecord2.recordMemo(MEMO_2);
        cowRecordRepository.save(cowRecord1);
        cowRecordRepository.save(cowRecord2);

        //when
        List<CowRecord> allByCowCodeId = cowRecordRepository.findAllByCow_CodeId(COW_CODE_ID_1);

        //then
        assertThat(allByCowCodeId).hasSize(2);
        assertThat(allByCowCodeId).contains(cowRecord1, cowRecord2);
    }

    @DisplayName("소 번호와 기록 타입에 따라 조회한다.")
    @Test
    void findAllByCow_CodeIdAndRecordType() {
        //given
        Barn barn = Barn.builder().codeId(BARN_CODE_ID_1).build();
        barnRepository.save(barn);
        Pen pen = Pen.builder().codeId(PEN_CODE_ID_1).barn(barn).build();
        penRepository.save(pen);
        Cow cow = Cow.builder().codeId(COW_CODE_ID_1).build();
        cow.registerUpperGroup(pen);
        cow.registerBarn(barn);
        cowRepository.save(cow);
        CowRecord cowRecord1 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
        CowRecord cowRecord2 = CowRecord.createRecord(cow, RecordType.HEALTH, TEMP_DATE);
        cowRecord1.recordMemo(MEMO_1);
        cowRecord2.recordMemo(MEMO_2);
        cowRecordRepository.save(cowRecord1);
        cowRecordRepository.save(cowRecord2);
        //when
        List<CowRecord> dailyRecords = cowRecordRepository.findAllByCow_CodeIdAndRecordType(
                cow.getCodeId(), RecordType.DAILY);
        List<CowRecord> healthRecords = cowRecordRepository.findAllByCow_CodeIdAndRecordType(
                cow.getCodeId(), RecordType.HEALTH);

        //then
        assertThat(dailyRecords).containsOnly(cowRecord1);
        assertThat(healthRecords).containsOnly(cowRecord2);
    }

}