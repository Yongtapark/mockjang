package myproject.mockjang.api.service.records;

import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.request.CowRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.request.CowRecordFindAllByCodeIdAndRecordTypeServiceRequest;
import myproject.mockjang.api.service.records.request.CowRecordRemoveServiceRequest;
import myproject.mockjang.api.service.records.response.CowRecordResponse;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.records.CowRecord;
import myproject.mockjang.domain.records.CowRecordRepository;
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
    CowRecord savedRecord = cowRecordRepository.save(record);
    return CowRecordResponse.of(savedRecord);
  }

  public List<CowRecordResponse> findAllByCodeId(String codeId) {
    List<CowRecord> cowRecords = cowRecordRepository.findAllByCow_CodeId(codeId);
    return cowRecords.stream().map(CowRecordResponse::of).toList();
  }

  public List<CowRecordResponse> findAllByCodeIdWhereRecordType(
      CowRecordFindAllByCodeIdAndRecordTypeServiceRequest request) {
    List<CowRecord> cowRecords = cowRecordRepository.findAllByCow_CodeIdAndRecordType(request.getCowCode(),request.getRecordType());
    return cowRecords.stream().map(CowRecordResponse::of).toList();
  }

  public CowRecordResponse remove(CowRecordRemoveServiceRequest request) {
    CowRecord cowRecord = cowRecordRepository.findById(request.getId()).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(CowRecord.class)));
    cowRecordRepository.delete(cowRecord);
    return CowRecordResponse.withMemo(cowRecord);
  }
}