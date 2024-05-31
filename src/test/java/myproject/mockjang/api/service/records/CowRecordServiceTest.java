package myproject.mockjang.api.service.records;

import static org.assertj.core.api.Java6Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.controller.records.request.CowRecordCreateRequest;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.api.service.records.request.CowRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.response.CowRecordResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.records.CowRecordRepository;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.record.RecordException;
import org.assertj.core.api.Java6Assertions;
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
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(RecordException.class)
        .hasMessage(Exceptions.COMMON_NOT_EXIST.formatMessage(Barn.class));
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
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(RecordException.class)
        .hasMessage(Exceptions.COMMON_NOT_EXIST.formatMessage(Pen.class));
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
        .hasMessage(Exceptions.COMMON_NOT_EXIST.formatMessage(Cow.class));
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
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(RecordException.class)
        .hasMessage(Exceptions.COMMON_NOT_EXIST.formatMessage(RecordType.class));
  }

  @DisplayName("기록 날짜를 입력하지 않으면 예외를 발생시킨다.")
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
    assertThatThrownBy(() -> cowRecordService.create(request)).isInstanceOf(RecordException.class)
        .hasMessage(Exceptions.COMMON_NOT_EXIST.formatMessage(LocalDateTime.class));
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

}