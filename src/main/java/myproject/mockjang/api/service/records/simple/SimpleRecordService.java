package myproject.mockjang.api.service.records.simple;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordRemoveServiceRequest;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.simple.response.SimpleRecordResponse;
import myproject.mockjang.domain.records.simple.SimpleRecord;
import myproject.mockjang.domain.records.simple.SimpleRecordQueryRepository;
import myproject.mockjang.domain.records.simple.SimpleRecordRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleRecordService {

  private final SimpleRecordRepository simpleRecordRepository;
  private final SimpleRecordQueryRepository simpleRecordQueryRepository;

  public SimpleRecordResponse create(SimpleRecordCreateServiceRequest request) {
    SimpleRecord simpleRecord = SimpleRecord.create(request.getCodeId(), request.getRecordType(),
        request.getDate(),
        request.getRecord());
    simpleRecordRepository.save(simpleRecord);
    return SimpleRecordResponse.of(simpleRecord);
  }

  public List<SimpleRecordResponse> findAllByCodeId(String codeId) {
    ;
    List<SimpleRecord> simpleRecords = simpleRecordRepository.findAllByCodeId(codeId);
    return simpleRecords.stream().map(SimpleRecordResponse::of).toList();
  }

  public List<String> findAllCodeIdWithDistinct() {
   return simpleRecordQueryRepository.distinctCodeIds();
  }

  public List<SimpleRecordResponse> search(SimpleRecordSearchServiceRequest request) {
    List<SimpleRecord> simpleRecords = simpleRecordQueryRepository.search(request.getCodeId(),
        request.getRecordType(),
        request.getDate());
    return simpleRecords.stream().map(SimpleRecordResponse::of).toList();
  }

  public void remove(Long codeId) {
    SimpleRecord simpleRecord = simpleRecordRepository.findById(codeId).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(SimpleRecord.class)));
    simpleRecordRepository.delete(simpleRecord);
  }

}
