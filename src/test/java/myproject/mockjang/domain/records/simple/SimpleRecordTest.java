package myproject.mockjang.domain.records.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimpleRecordTest extends IntegrationTestSupport {

  @DisplayName("문자열로 번호를 받는 단순 기록물을 생성한다.")
  @Test
  void create() {
    //given //when
    SimpleRecord simpleRecord = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY,
        TEMP_DATE, MEMO_1);
    simpleRecord.recordsNullCheck(simpleRecord);

    //then
    assertThat(simpleRecord.getRecord()).isEqualTo(MEMO_1);
  }

  @DisplayName("기록 타입을 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void recordsNullCheckWithNoRecordType() {
    //given
    SimpleRecord simpleRecord = SimpleRecord.create(PARSER_COW_CODE_ID_1, null, TEMP_DATE, MEMO_1);

    //when //then
    assertThatThrownBy(() -> simpleRecord.recordsNullCheck(simpleRecord)).isInstanceOf(
        NotExistException.class).hasMessage(
        Exceptions.COMMON_NOT_EXIST.formatMessage(RecordType.class));
  }

  @DisplayName("소 번호를 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void recordsNullCheckWithNoCodeId() {
    //given
    SimpleRecord simpleRecord = SimpleRecord.create(null, RecordType.DAILY, TEMP_DATE, MEMO_1);

    //when //then
    assertThatThrownBy(() -> simpleRecord.recordsNullCheck(simpleRecord)).isInstanceOf(
        NotExistException.class).hasMessage(
        Exceptions.COMMON_NOT_EXIST.formatMessage("codeId"));
  }

  @DisplayName("기록를 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void recordsNullCheckWithNoDate() {
    //given
    SimpleRecord simpleRecord = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY, null,
        MEMO_1);

    //when //then
    assertThatThrownBy(() -> simpleRecord.recordsNullCheck(simpleRecord)).isInstanceOf(
        NotExistException.class).hasMessage(
        Exceptions.COMMON_NOT_EXIST.formatMessage(LocalDateTime.class));
  }

  @DisplayName("기록을 등록하지 않으면 예외를 발생시킨다.")
  @Test
  void recordsNullCheckWithNoRecord() {
    //given
    SimpleRecord simpleRecord = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY,
        TEMP_DATE, null);

    //when //then
    assertThatThrownBy(() -> simpleRecord.recordsNullCheck(simpleRecord)).isInstanceOf(
        NotExistException.class).hasMessage(
        Exceptions.COMMON_NOT_EXIST.formatMessage("record"));
  }

}