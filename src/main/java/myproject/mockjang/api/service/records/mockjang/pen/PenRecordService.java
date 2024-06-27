package myproject.mockjang.api.service.records.mockjang.pen;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.pen.request.PenRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.pen.request.PenRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.mockjang.pen.request.PenRecordUpdateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.pen.response.PenRecordResponse;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.pen.PenRecord;
import myproject.mockjang.domain.records.mockjang.pen.PenRecordQueryRepository;
import myproject.mockjang.domain.records.mockjang.pen.PenRecordRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PenRecordService {

    private final PenRecordRepository penRecordRepository;
    private final PenRecordQueryRepository penRecordQueryRepository;
    private final PenRepository penRepository;

    public Long create(PenRecordCreateServiceRequest request) {
        Pen pen = penRepository.findByCodeId(request.getPenCodeId()).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Pen.class)));
        PenRecord record = PenRecord.createRecord(pen, request.getRecordType(), request.getDate());
        record.recordMemo(request.getMemo());
        record.recordsNullCheck(record);
        return penRecordRepository.save(record).getId();
    }

    public List<PenRecordResponse> search(PenRecordSearchServiceRequest request) {
        List<PenRecord> penRecords = penRecordQueryRepository.search(request.getCodeId(), request.getRecordType(),
                request.getDate(), request.getRecord());
        return penRecords.stream().map(PenRecordResponse::of).toList();
    }

    public void update(PenRecordUpdateServiceRequest request){
        PenRecord penRecord = findById(request.getId());
        penRecord.registerDate(request.getDate());
        penRecord.recordMemo(request.getRecord());
        penRecord.registerRecordType(request.getRecordType());
    }

    public void remove(Long id) {
        PenRecord penRecord = findById(id);
        penRecord.removeMemo();
        penRecordRepository.delete(penRecord);
    }

    private PenRecord findById(Long id) {
       return penRecordRepository.findById(id).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(CowRecord.class)));
    }
}
