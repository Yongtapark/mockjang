package myproject.mockjang.api.service.records;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.records.mockjang.cow.CowRecordService;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordFindAllByCodeIdAndRecordTypeServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordRemoveServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
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

class CowRecordServiceTest extends IntegrationTestSupport {

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
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    pen.registerUpperGroup(barn);
    penRepository.save(pen);
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cow.registerBarn(barn);
    cow.registerUpperGroup(pen);
    cowRepository.save(cow);

    CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
        .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();
    //when
    CowRecordResponse response = cowRecordService.create(request);

    //then
    cowRecordRepository.findById(response.getId());

  }

  @DisplayName("소의 축사이름을 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoBarnCodeId() {
    //given
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cowRepository.save(cow);

    CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
        .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();

    //when  //then
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
        .hasMessage(COMMON_NOT_EXIST.formatMessage(Barn.class));
  }

  @DisplayName("소의 축사칸 이름을 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoPenCodeId() {
    //given
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    pen.registerUpperGroup(barn);
    penRepository.save(pen);
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cow.registerBarn(barn);
    cowRepository.save(cow);

    CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
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
        .cowCode(null)
        .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();

    //when  //then
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
        .hasMessage(COMMON_NOT_EXIST.formatMessage(Cow.class));
  }

  @DisplayName("기록 타입을 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoRecordType() {
    //given
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    pen.registerUpperGroup(barn);
    penRepository.save(pen);
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cow.registerBarn(barn);
    cow.registerUpperGroup(pen);
    cowRepository.save(cow);

    CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
        .recordType(null).date(TEMP_DATE).memo(MEMO_1).build();

    //when  //then
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
        .hasMessage(COMMON_NOT_EXIST.formatMessage(RecordType.class));
  }

  @DisplayName("기록를 입력하지 않으면 예외를 발생시킨다.")
  @Test
  void createWithNoDate() {
    //given
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    pen.registerUpperGroup(barn);
    penRepository.save(pen);
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cow.registerBarn(barn);
    cow.registerUpperGroup(pen);
    cowRepository.save(cow);

    CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
        .recordType(RecordType.DAILY).date(null).memo(MEMO_1).build();

    //when  //then
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(NotExistException.class)
        .hasMessage(COMMON_NOT_EXIST.formatMessage(LocalDateTime.class));
  }

  @DisplayName("소 번호로 소 기록을 조회한다.")
  @Test
  void findAllByCodeId() {
    //given
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    pen.registerUpperGroup(barn);
    penRepository.save(pen);
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cow.registerUpperGroup(pen);
    cow.registerBarn(barn);
    cowRepository.save(cow);

    CowRecordCreateServiceRequest request1 = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
        .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_1).build();

    CowRecordCreateServiceRequest request2 = CowRecordCreateServiceRequest.builder()
        .cowCode(cow.getCodeId())
        .recordType(RecordType.DAILY).date(TEMP_DATE).memo(MEMO_2).build();

    cowRecordService.create(request1);
    cowRecordService.create(request2);
    //when
    List<CowRecordResponse> responses = cowRecordService.findAllByCodeId(cow.getCodeId());

    //then
    List<String> memos = responses.stream().map(CowRecordResponse::getMemo).toList();
    assertThat(memos).contains(request1.getMemo(), request2.getMemo());
  }

  @DisplayName("id 값으로 소 기록을 제거한다.")
  @Test
  void remove() {
    //given
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cowRepository.save(cow);
    CowRecord cowRecord1 = CowRecord.builder().cow(cow).build();
    CowRecord cowRecord2 = CowRecord.builder().cow(cow).build();
    cowRecord1.recordMemo(MEMO_1);
    cowRecord2.recordMemo(MEMO_2);
    CowRecord savedRecord1 = cowRecordRepository.save(cowRecord1);
    CowRecord savedRecord2 = cowRecordRepository.save(cowRecord2);

    CowRecordRemoveServiceRequest request = CowRecordRemoveServiceRequest.builder()
        .id(savedRecord1.getId()).build();

    //when
    cowRecordService.remove(request);

    //then
    List<CowRecord> cowRecords = cowRecordRepository.findAllByCow_CodeId(cow.getCodeId());
    assertThat(cowRecords).containsOnly(savedRecord2);
    assertThat(cowRecords).doesNotContain(savedRecord1);
  }

  @DisplayName("소 번호와 기록타입으로 소 기록 목록을 조회한다.")
  @Test
  void findAllByCodeIdWhereRecordType() {
    //given
    Cow cow = Cow.createCow(PARSER_COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
    cowRepository.save(cow);
    CowRecord cowRecord1 = CowRecord.builder().cow(cow).build();
    CowRecord cowRecord2 = CowRecord.builder().cow(cow).build();
    cowRecord1.registerRecordType(RecordType.DAILY);
    cowRecord2.registerRecordType(RecordType.HEALTH);
    cowRecord1.recordMemo(MEMO_1);
    cowRecord2.recordMemo(MEMO_2);
    CowRecord savedRecord1 = cowRecordRepository.save(cowRecord1);
    CowRecord savedRecord2 = cowRecordRepository.save(cowRecord2);

    CowRecordFindAllByCodeIdAndRecordTypeServiceRequest request1 = CowRecordFindAllByCodeIdAndRecordTypeServiceRequest.builder()
        .cowCode(savedRecord1.getCow().getCodeId()).recordType(RecordType.DAILY).build();

    CowRecordFindAllByCodeIdAndRecordTypeServiceRequest request2 = CowRecordFindAllByCodeIdAndRecordTypeServiceRequest.builder()
        .cowCode(savedRecord1.getCow().getCodeId()).recordType(RecordType.HEALTH).build();

    //when
    List<CowRecordResponse> allByCodeIdWhereRecordType1 = cowRecordService.findAllByCodeIdWhereRecordType(
        request1);
    List<CowRecordResponse> allByCodeIdWhereRecordType2 = cowRecordService.findAllByCodeIdWhereRecordType(
        request2);

    //then
    List<String> dailyRecords = allByCodeIdWhereRecordType1.stream().map(CowRecordResponse::getMemo)
        .toList();
    List<String> healthRecords = allByCodeIdWhereRecordType2.stream()
        .map(CowRecordResponse::getMemo)
        .toList();
    assertThat(dailyRecords).containsOnly(MEMO_1);
    assertThat(healthRecords).containsOnly(MEMO_2);
  }


}