package myproject.mockjang.api.service.records.mockjang.cow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordUpdateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordQueryRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class CowRecordServiceTest extends IntegrationTestSupport {
    @Autowired
    private CowRecordService cowRecordService;
    @Autowired
    private CowRecordQueryRepository cowRecordQueryRepository;
    @Autowired
    private  CowRecordRepository cowRecordRepository;
    @Autowired
    private  CowRepository cowRepository;

    @DisplayName("소 기록을 생성한다.")
    @Test
    void create() {
        //given
        CowRecordCreateServiceRequest request = CowRecordCreateServiceRequest.builder()
                .memo(MEMO_1)
                .cowCodeId(COW_CODE_ID_1)
                .date(TEMP_DATE)
                .recordType(RecordType.DAILY)
                .build();

        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        Cow savedCow = cowRepository.save(cow);

        //when
        Long savedRecordId = cowRecordService.create(request);

        //then
        assertThat(cowRecordRepository.findById(savedRecordId).orElseThrow().getCow()).isEqualTo(savedCow);
    }

    @DisplayName("소의 이름을 통해 소 기록을 검색한다.")
    @Test
    void searchWithCodeId() {
        //given
        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cowRepository.save(cow1);
        cowRepository.save(cow2);

        CowRecord record1 = CowRecord.createRecord(cow1, RecordType.DAILY, TEMP_DATE);
        record1.recordMemo(MEMO_1);
        CowRecord record2 = CowRecord.createRecord(cow2, RecordType.HEALTH, TEMP_DATE);
        record2.recordMemo(MEMO_2);

        cowRecordRepository.saveAll(List.of(record1,record2));

        CowRecordSearchServiceRequest request = CowRecordSearchServiceRequest.builder()
                .codeId(COW_CODE_ID_1)
                .build();

        //when
        List<Long> recordIds = cowRecordService.search(request).stream().map(CowRecordResponse::getId).toList();

        //then
        assertThat(recordIds).containsExactly(record1.getId());
    }

    @DisplayName("기록 날짜를 통해 소 기록을 검색한다.")
    @Test
    void searchWithDate() {
        //given
        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cowRepository.save(cow1);
        cowRepository.save(cow2);

        CowRecord record1 = CowRecord.createRecord(cow1, RecordType.DAILY, TEMP_DATE);
        record1.recordMemo(MEMO_1);
        CowRecord record2 = CowRecord.createRecord(cow2, RecordType.HEALTH, TEMP_DATE);
        record2.recordMemo(MEMO_2);

        cowRecordRepository.saveAll(List.of(record1,record2));

        CowRecordSearchServiceRequest request = CowRecordSearchServiceRequest.builder()
                .date(TEMP_DATE)
                .build();

        //when
        List<Long> recordIds = cowRecordService.search(request).stream().map(CowRecordResponse::getId).toList();

        //then
        assertThat(recordIds).containsExactly(record1.getId(),record2.getId());
    }

    @DisplayName("기록 내용을 통해 소 기록을 검색한다.")
    @Test
    void searchWithRecord() {
        //given
        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cowRepository.save(cow1);
        cowRepository.save(cow2);

        CowRecord record1 = CowRecord.createRecord(cow1, RecordType.DAILY, TEMP_DATE);
        record1.recordMemo(MEMO_1);
        CowRecord record2 = CowRecord.createRecord(cow2, RecordType.HEALTH, TEMP_DATE);
        record2.recordMemo(MEMO_2);

        cowRecordRepository.saveAll(List.of(record1,record2));

        CowRecordSearchServiceRequest request = CowRecordSearchServiceRequest.builder()
                .record("메모")
                .build();

        //when
        List<Long> recordIds = cowRecordService.search(request).stream().map(CowRecordResponse::getId).toList();

        //then
        assertThat(recordIds).containsExactly(record1.getId(),record2.getId());
    }

    @DisplayName("소 기록을 수정한다.")
    @Test
    void update() {
        //given
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, TEMP_DATE);
        cowRepository.save(cow);
        CowRecordUpdateServiceRequest request = CowRecordUpdateServiceRequest.builder()
                .id(cow.getId())
                .record(MEMO_2)
                .codeId(COW_CODE_ID_1)
                .date(TEMP_DATE)
                .recordType(RecordType.DAILY)
                .build();


        CowRecord cowRecord = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
        cowRecordRepository.save(cowRecord);

        //when
        cowRecordService.update(request);

        //then
        assertThat(cowRecord.getRecord()).isEqualTo(MEMO_2);
    }

}