package myproject.mockjang.domain.records.simple;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class SimpleRecordQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private SimpleRecordQueryRepository simpleRecordQueryRepository;
    @Autowired
    private SimpleRecordRepository simpleRecordRepository;

    @DisplayName("코드id, 기록타입, 날짜를 통해 검색한다.")
    @Test
    void search() {
        //given
        SimpleRecord simpleRecord1 = SimpleRecord.create(COW_CODE_ID_1, RecordType.DAILY,
                TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord2 = SimpleRecord.create(COW_CODE_ID_1, RecordType.HEALTH,
                TEMP_DATE, MEMO_2);
        SimpleRecord simpleRecord3 = SimpleRecord.create(COW_CODE_ID_2, RecordType.DAILY,
                TEMP_DATE, MEMO_1);

        simpleRecordRepository.save(simpleRecord1);
        simpleRecordRepository.save(simpleRecord2);
        simpleRecordRepository.save(simpleRecord3);

        //when
        List<SimpleRecord> search = simpleRecordQueryRepository.search(COW_CODE_ID_1,
                RecordType.DAILY,
                TEMP_DATE);

        //then
        assertThat(search).containsOnly(simpleRecord1);
    }

    @DisplayName("기록타입, 날짜를 통해 검색한다.")
    @Test
    void searchWithNoCodeId() {
        //given
        SimpleRecord simpleRecord1 = SimpleRecord.create(COW_CODE_ID_1, RecordType.DAILY,
                TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord2 = SimpleRecord.create(COW_CODE_ID_1, RecordType.HEALTH,
                TEMP_DATE, MEMO_2);
        SimpleRecord simpleRecord3 = SimpleRecord.create(COW_CODE_ID_2, RecordType.DAILY,
                TEMP_DATE, MEMO_1);

        simpleRecordRepository.save(simpleRecord1);
        simpleRecordRepository.save(simpleRecord2);
        simpleRecordRepository.save(simpleRecord3);

        //when
        List<SimpleRecord> search = simpleRecordQueryRepository.search(null, RecordType.DAILY,
                TEMP_DATE);

        //then
        assertThat(search).containsExactly(simpleRecord1, simpleRecord3);
    }

    @DisplayName("날짜를 통해 검색한다.")
    @Test
    void searchWithNoCodeIdNoRecordType() {
        //given
        SimpleRecord simpleRecord1 = SimpleRecord.create(COW_CODE_ID_1, RecordType.DAILY,
                TEMP_DATE.plusHours(1), MEMO_1);
        SimpleRecord simpleRecord2 = SimpleRecord.create(COW_CODE_ID_1, RecordType.HEALTH,
                TEMP_DATE, MEMO_2);
        SimpleRecord simpleRecord3 = SimpleRecord.create(COW_CODE_ID_2, RecordType.DAILY,
                TEMP_DATE, MEMO_1);

        simpleRecordRepository.save(simpleRecord1);
        simpleRecordRepository.save(simpleRecord2);
        simpleRecordRepository.save(simpleRecord3);

        List<SimpleRecord> all = simpleRecordRepository.findAll();
        System.out.println("all = " + all);

        //when
        List<SimpleRecord> search = simpleRecordQueryRepository.search(null, null,
                TEMP_DATE);

        //then
        assertThat(search).containsExactly(simpleRecord1, simpleRecord2, simpleRecord3);
    }

    @DisplayName("조건없이 모두 검색한다.")
    @Test
    void searchWithNoCondition() {
        //given
        SimpleRecord simpleRecord1 = SimpleRecord.create(COW_CODE_ID_1, RecordType.DAILY,
                TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord2 = SimpleRecord.create(COW_CODE_ID_1, RecordType.HEALTH,
                TEMP_DATE, MEMO_2);
        SimpleRecord simpleRecord3 = SimpleRecord.create(COW_CODE_ID_2, RecordType.DAILY,
                TEMP_DATE, MEMO_1);

        simpleRecordRepository.save(simpleRecord1);
        simpleRecordRepository.save(simpleRecord2);
        simpleRecordRepository.save(simpleRecord3);

        //when
        List<SimpleRecord> search = simpleRecordQueryRepository.search(null, null,
                null);

        //then
        assertThat(search).containsExactly(simpleRecord1, simpleRecord2, simpleRecord3);
    }

    @DisplayName("자동완성")
    @Test
    void distinctCodeIds() {
        //given
        SimpleRecord simpleRecord1 = SimpleRecord.create(COW_CODE_ID_1, RecordType.DAILY,
                TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord2 = SimpleRecord.create(COW_CODE_ID_1, RecordType.HEALTH,
                TEMP_DATE, MEMO_2);
        SimpleRecord simpleRecord3 = SimpleRecord.create(COW_CODE_ID_2, RecordType.DAILY,
                TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord4 = SimpleRecord.create(BARN_CODE_ID_1, RecordType.DAILY,
                TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord5 = SimpleRecord.create(PARSER_BARN_CODE_ID_2, RecordType.DAILY,
                TEMP_DATE, MEMO_1);

        simpleRecordRepository.save(simpleRecord1);
        simpleRecordRepository.save(simpleRecord2);
        simpleRecordRepository.save(simpleRecord3);
        simpleRecordRepository.save(simpleRecord4);
        simpleRecordRepository.save(simpleRecord5);

        //when
        List<String> strings = simpleRecordQueryRepository.distinctCodeIds();

        //then
        assertThat(strings).containsExactly(COW_CODE_ID_1, COW_CODE_ID_2,
                BARN_CODE_ID_1, PARSER_BARN_CODE_ID_2);
    }
}