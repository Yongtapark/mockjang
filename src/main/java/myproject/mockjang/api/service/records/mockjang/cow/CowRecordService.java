package myproject.mockjang.api.service.records.mockjang.cow;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordUpdateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordQueryRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CowRecordService {

    private final CowRecordRepository cowRecordRepository;
    private final CowRecordQueryRepository cowRecordQueryRepository;
    private final CowRepository cowRepository;

    public Long create(CowRecordCreateServiceRequest request) {
        Cow cow = cowRepository.findByCodeId(request.getCowCodeId()).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Cow.class)));
        CowRecord record = CowRecord.createRecord(cow, request.getRecordType(), request.getDate());
        record.recordMemo(request.getMemo());
        record.recordsNullCheck(record);
        CowRecord savedRecord = cowRecordRepository.save(record);
        return savedRecord.getId();
    }

    public List<CowRecordResponse> search(CowRecordSearchServiceRequest request) {
        List<CowRecord> cowRecords = cowRecordQueryRepository.search(request.getCodeId(), request.getRecordType(),
                request.getDate(), request.getRecord());
        return cowRecords.stream().map(CowRecordResponse::of).toList();
    }

    public void update(CowRecordUpdateServiceRequest request){
        CowRecord cowRecord = findById(request.getId());
        cowRecord.registerDate(request.getDate());
        cowRecord.recordMemo(request.getRecord());
        cowRecord.registerRecordType(request.getRecordType());
    }

    public void remove(Long id) {
        CowRecord cowRecord = findById(id);
        cowRecord.removeMemo();
        cowRecordRepository.delete(cowRecord);
    }

    private CowRecord findById(Long id) {
        return cowRecordRepository.findById(id).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Cow.class)));
    }
}
