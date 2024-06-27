package myproject.mockjang.api.service.records.mockjang.cow;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordFindAllByCodeIdAndRecordTypeServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.request.CowRecordRemoveServiceRequest;
import myproject.mockjang.api.service.records.mockjang.cow.response.CowRecordResponse;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CowRecordService {

    private final CowRecordRepository cowRecordRepository;
    private final CowRepository cowRepository;

    public CowRecordResponse create(CowRecordCreateServiceRequest request) {
        String cowCode = request.getCowCode();
        Cow cow = cowRepository.findByCodeId(cowCode).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(Cow.class)));
        CowRecord record = CowRecord.createRecord(cow, request.getRecordType(), request.getDate());
        record.recordMemo(request.getMemo());
        record.recordsNullCheck(record);
        CowRecord savedRecord = cowRecordRepository.save(record);
        return CowRecordResponse.of(savedRecord);
    }

    public List<CowRecordResponse> findAllByCodeId(String codeId) {
        List<CowRecord> cowRecords = cowRecordRepository.findAllByCow_CodeId(codeId);
        return cowRecords.stream().map(CowRecordResponse::of).toList();
    }

    public List<CowRecordResponse> findAllByCodeIdWhereRecordType(
            CowRecordFindAllByCodeIdAndRecordTypeServiceRequest request) {
        List<CowRecord> cowRecords = cowRecordRepository.findAllByCow_CodeIdAndRecordType(
                request.getCowCode(), request.getRecordType());
        return cowRecords.stream().map(CowRecordResponse::of).toList();
    }

    public void remove(CowRecordRemoveServiceRequest request) {
        CowRecord cowRecord = cowRecordRepository.findById(request.getId()).orElseThrow(
                () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(CowRecord.class)));
        cowRecordRepository.delete(cowRecord);
    }
}
