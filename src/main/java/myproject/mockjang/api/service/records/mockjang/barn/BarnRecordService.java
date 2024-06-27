package myproject.mockjang.api.service.records.mockjang.barn;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.barn.request.BarnRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.barn.request.BarnRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.mockjang.barn.request.BarnRecordUpdateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.barn.response.BarnRecordResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecord;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecordQueryRepository;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecordRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarnRecordService {

    private final BarnRecordRepository barnRecordRepository;
    private final BarnRecordQueryRepository barnRecordQueryRepository;
    private final BarnRepository barnRepository;

    public Long create(BarnRecordCreateServiceRequest request) {
        Barn barn = barnRepository.findByCodeId(request.getBarnCodeId()).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Barn.class)));
        BarnRecord record = BarnRecord.creatRecord(barn, request.getRecordType(), request.getDate());
        record.recordMemo(request.getMemo());
        record.recordsNullCheck(record);
        return barnRecordRepository.save(record).getId();
    }

    public List<BarnRecordResponse> search(BarnRecordSearchServiceRequest request) {
        List<BarnRecord> barnRecords = barnRecordQueryRepository.search(request.getCodeId(),request.getRecordType(),request.getDate(),request.getRecord());
        return barnRecords.stream().map(BarnRecordResponse::of).toList();
    }

    public void update(BarnRecordUpdateServiceRequest request){
        BarnRecord barnRecord = findById(request.getId());
        barnRecord.registerDate(request.getDate());
        barnRecord.recordMemo(request.getRecord());
        barnRecord.registerRecordType(request.getRecordType());
    }

    public void remove(Long id) {
        BarnRecord barnRecord = findById(id);
        barnRecord.removeMemo();
        barnRecordRepository.delete(barnRecord);
    }

    private BarnRecord findById(Long id) {
        return barnRecordRepository.findById(id).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(CowRecord.class)));
    }
}
