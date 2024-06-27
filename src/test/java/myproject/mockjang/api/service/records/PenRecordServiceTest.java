package myproject.mockjang.api.service.records;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.records.mockjang.cow.CowRecordService;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PenRecordServiceTest extends IntegrationTestSupport {

    @Autowired
    private BarnRepository barnRepository;
    @Autowired
    private PenRepository penRepository;
    @Autowired
    private CowRepository cowRepository;
    @Autowired
    private CowRecordRepository cowRecordRepository;
    @Autowired
    private CowRecordService cowRecordService;

    @DisplayName("소번호, 날짜, 기록타입, 메모를 입력하면 소 기록을 생성한다.")
    @Test
    void create() {
        //given
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow.registerBarn(barn);
        cow.registerUpperGroup(pen);
        cowRepository.save(cow);

        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .cowCodeId(cow.getCodeId())
                .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();
        //when
        Long savedId = cowRecordService.create(request);

        //then
        cowRecordRepository.findById(savedId);

    }

    @DisplayName("소의 축사이름을 입력하지 않으면 예외를 발생시킨다.")
    @Test
    void createWithNoBarnCodeId() {
        //given
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cowRepository.save(cow);

        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .cowCodeId(cow.getCodeId())
                .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();

        //when  //then
        assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
                .hasMessage(COMMON_NOT_EXIST.formatMessage(Barn.class));
    }

    @DisplayName("소의 축사칸 이름을 입력하지 않으면 예외를 발생시킨다.")
    @Test
    void createWithNoPenCodeId() {
        //given
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow.registerBarn(barn);
        cowRepository.save(cow);

        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .cowCodeId(cow.getCodeId())
                .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();

        //when  //then
        assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
                .hasMessage(COMMON_NOT_EXIST.formatMessage(Pen.class));
    }

    @DisplayName("소의 이름을 입력하지 않으면 예외를 발생시킨다.")
    @Test
    void createWithNoCowId() {
        //given
        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .cowCodeId(null)
                .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();

        //when  //then
        assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
                .hasMessage(COMMON_NOT_EXIST.formatMessage(Cow.class));
    }

    @DisplayName("기록 타입을 입력하지 않으면 예외를 발생시킨다.")
    @Test
    void createWithNoRecordType() {
        //given
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow.registerBarn(barn);
        cow.registerUpperGroup(pen);
        cowRepository.save(cow);

        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .cowCodeId(cow.getCodeId())
                .recordType(null).date(TEMP_DATE).memo(MEMO_1).build();

        //when  //then
        assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
                .hasMessage(COMMON_NOT_EXIST.formatMessage(RecordType.class));
    }

    @DisplayName("기록를 입력하지 않으면 예외를 발생시킨다.")
    @Test
    void createWithNoDate() {
        //given
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cow.registerBarn(barn);
        cow.registerUpperGroup(pen);
        cowRepository.save(cow);

        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .cowCodeId(cow.getCodeId())
                .recordType(RecordType.DAILY).date(null).memo(MEMO_1).build();

        //when  //then
        assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
                .hasMessage(COMMON_NOT_EXIST.formatMessage(LocalDateTime.class));
    }

    @DisplayName("id 값으로 소 기록을 제거한다.")
    @Test
    void remove() {
        //given
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cowRepository.save(cow);
        CowRecord cowRecord1 = CowRecord.builder().cow(cow).build();
        CowRecord cowRecord2 = CowRecord.builder().cow(cow).build();
        cowRecord1.recordMemo(MEMO_1);
        cowRecord2.recordMemo(MEMO_2);
        CowRecord savedRecord1 = cowRecordRepository.save(cowRecord1);
        CowRecord savedRecord2 = cowRecordRepository.save(cowRecord2);

        //when
        cowRecordService.remove(savedRecord1.getId());

        //then
        List<CowRecord> cowRecords = cowRecordRepository.findAllByCow_CodeId(cow.getCodeId());
        assertThat(cowRecords).containsOnly(savedRecord2);
        assertThat(cowRecords).doesNotContain(savedRecord1);
        assertThat(cow.getRecords()).doesNotContain(savedRecord1);
    }
}