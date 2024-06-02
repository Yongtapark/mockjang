package myproject.mockjang.domain.records.simple_record;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.records.RecordType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class SimpleRecordQueryTest extends IntegrationTestSupport {
  @Autowired
  private SimpleRecordQuery simpleRecordQuery;
  @Autowired
  private SimpleRecordRepository simpleRecordRepository;

  @DisplayName("코드id, 기록타입, 날짜를 통해 검색한다.")
  @Test
  void search() {
    //given
    SimpleRecord simpleRecord1 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY, TEMP_DATE, MEMO_1);
    SimpleRecord simpleRecord2 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.HEALTH, TEMP_DATE, MEMO_2);
    SimpleRecord simpleRecord3 = SimpleRecord.create(PARSER_COW_CODE_ID_2, RecordType.DAILY, TEMP_DATE, MEMO_1);

    simpleRecordRepository.save(simpleRecord1);
    simpleRecordRepository.save(simpleRecord2);
    simpleRecordRepository.save(simpleRecord3);

    //when
    List<SimpleRecord> search = simpleRecordQuery.search(PARSER_COW_CODE_ID_1, RecordType.DAILY,
        TEMP_DATE);

    //then
    Assertions.assertThat(search).containsOnly(simpleRecord1);
  }

  @DisplayName("기록타입, 날짜를 통해 검색한다.")
  @Test
  void searchWithNoCodeId() {
    //given
    SimpleRecord simpleRecord1 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY, TEMP_DATE, MEMO_1);
    SimpleRecord simpleRecord2 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.HEALTH, TEMP_DATE, MEMO_2);
    SimpleRecord simpleRecord3 = SimpleRecord.create(PARSER_COW_CODE_ID_2, RecordType.DAILY, TEMP_DATE, MEMO_1);

    simpleRecordRepository.save(simpleRecord1);
    simpleRecordRepository.save(simpleRecord2);
    simpleRecordRepository.save(simpleRecord3);

    //when
    List<SimpleRecord> search = simpleRecordQuery.search(null, RecordType.DAILY,
        TEMP_DATE);

    //then
    Assertions.assertThat(search).containsExactly(simpleRecord1,simpleRecord3);
  }

  @DisplayName("날짜를 통해 검색한다.")
  @Test
  void searchWithNoCodeIdNoRecordType() {
    //given
    SimpleRecord simpleRecord1 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY, TEMP_DATE, MEMO_1);
    SimpleRecord simpleRecord2 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.HEALTH, TEMP_DATE, MEMO_2);
    SimpleRecord simpleRecord3 = SimpleRecord.create(PARSER_COW_CODE_ID_2, RecordType.DAILY, TEMP_DATE, MEMO_1);

    simpleRecordRepository.save(simpleRecord1);
    simpleRecordRepository.save(simpleRecord2);
    simpleRecordRepository.save(simpleRecord3);

    List<SimpleRecord> all = simpleRecordRepository.findAll();
    System.out.println("all = " + all);

    //when
    List<SimpleRecord> search = simpleRecordQuery.search(null, null,
        TEMP_DATE);

    //then
    Assertions.assertThat(search).containsExactly(simpleRecord1,simpleRecord2,simpleRecord3);
  }

  @DisplayName("조건없이 모두 검색한다.")
  @Test
  void searchWithNoCondition() {
    //given
    SimpleRecord simpleRecord1 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY, TEMP_DATE, MEMO_1);
    SimpleRecord simpleRecord2 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.HEALTH, TEMP_DATE, MEMO_2);
    SimpleRecord simpleRecord3 = SimpleRecord.create(PARSER_COW_CODE_ID_2, RecordType.DAILY, TEMP_DATE, MEMO_1);

    simpleRecordRepository.save(simpleRecord1);
    simpleRecordRepository.save(simpleRecord2);
    simpleRecordRepository.save(simpleRecord3);

    //when
    List<SimpleRecord> search = simpleRecordQuery.search(null, null,
        null);

    //then
    Assertions.assertThat(search).containsExactly(simpleRecord1,simpleRecord2,simpleRecord3);
  }
}