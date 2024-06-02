package myproject.mockjang.domain.records.simple;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.records.RecordType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class SimpleRecordRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private SimpleRecordRepository simpleRecordRepository;

  @DisplayName("codeId로 기록 전체 조회")
  @Test
  void findAllByCodeId() {
    //given
    SimpleRecord simpleRecord1 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY,
        TEMP_DATE, MEMO_1);
    SimpleRecord simpleRecord2 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY,
        TEMP_DATE, MEMO_2);
    SimpleRecord simpleRecord3 = SimpleRecord.create(PARSER_COW_CODE_ID_2, RecordType.DAILY,
        TEMP_DATE, MEMO_1);

    simpleRecordRepository.save(simpleRecord1);
    simpleRecordRepository.save(simpleRecord2);
    simpleRecordRepository.save(simpleRecord3);

    //when
    List<SimpleRecord> simpleRecords = simpleRecordRepository.findAllByCodeId(PARSER_COW_CODE_ID_1);

    //then
    Assertions.assertThat(simpleRecords).containsExactly(simpleRecord1, simpleRecord2);
    Assertions.assertThat(simpleRecords).hasSize(2);
  }

}