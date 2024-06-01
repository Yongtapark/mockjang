package myproject.mockjang.api.service.records.simple_record;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.simple_record.response.SimpleRecordResponse;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.simple_record.SimpleRecord;
import myproject.mockjang.domain.records.simple_record.SimpleRecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SimpleRecordServiceTest extends IntegrationTestSupport {

    @Autowired
    private SimpleRecordService simpleRecordService;

    @Autowired
    private SimpleRecordRepository simpleRecordRepository;

    @DisplayName("문자열 codeId를 이용해 기록을 저장한다")
    @Test
    void create() {
        //given
        SimpleRecordCreateServiceRequest request = SimpleRecordCreateServiceRequest.builder()
                .codeId(PARSER_COW_CODE_ID_1)
                .recordType(RecordType.DAILY)
                .date(TEMP_DATE)
                .record(MEMO_1)
                .build();
        //when
        SimpleRecordResponse response = simpleRecordService.create(request);
        SimpleRecord simpleRecord = simpleRecordRepository.findById(response.getId()).orElseThrow();

        //then
        assertThat(simpleRecord.getId()).isEqualTo(response.getId());
        assertThat(simpleRecord.getCodeId()).isEqualTo(response.getCodeId());
        assertThat(simpleRecord.getRecordType()).isEqualTo(response.getRecordType());
        assertThat(simpleRecord.getDate()).isEqualTo(response.getDate());
        assertThat(simpleRecord.getRecord()).isEqualTo(response.getRecord());
    }

    @DisplayName("codeId,기록 타입, 기록날짜로 기록 전체 조회")
    @Test
    void searchWithPieceOfCodeId() {
        //given
        SimpleRecord simpleRecord1 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.DAILY, TEMP_DATE, MEMO_1);
        SimpleRecord simpleRecord2 = SimpleRecord.create(PARSER_COW_CODE_ID_1, RecordType.HEALTH, TEMP_DATE, MEMO_2);
        SimpleRecord simpleRecord3 = SimpleRecord.create(PARSER_COW_CODE_ID_2, RecordType.DAILY, TEMP_DATE, MEMO_1);

        simpleRecordRepository.save(simpleRecord1);
        simpleRecordRepository.save(simpleRecord2);
        simpleRecordRepository.save(simpleRecord3);

        SimpleRecordSearchServiceRequest request = SimpleRecordSearchServiceRequest.builder()
                .codeId("00")
                .recordType(RecordType.DAILY)
                .date(TEMP_DATE)
                .record(MEMO_1)
                .build();

        //when
        List<SimpleRecordResponse> searches = simpleRecordService.search(request);

        //then
        List<Long> simpleRecordsIds = searches.stream().map(SimpleRecordResponse::getId).toList();
        Assertions.assertThat(simpleRecordsIds).containsExactly(simpleRecord1.getId(),simpleRecord3.getId());
    }

}