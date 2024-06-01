package myproject.mockjang.api.service.records.simple_record;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordRemoveServiceRequest;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.simple_record.response.SimpleRecordResponse;
import myproject.mockjang.domain.records.simple_record.SimpleRecord;
import myproject.mockjang.domain.records.simple_record.SimpleRecordRepository;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleRecordService {

  private final SimpleRecordRepository simpleRecordRepository;

  public SimpleRecordResponse create(SimpleRecordCreateServiceRequest request) {
    SimpleRecord simpleRecord = SimpleRecord.create(request.getCodeId(), request.getRecordType(),
        request.getDate(),
        request.getRecord());
    simpleRecordRepository.save(simpleRecord);
    return SimpleRecordResponse.of(simpleRecord);
  }

  public List<SimpleRecordResponse> findAllByCodeId(String codeId) {;
    List<SimpleRecord> simpleRecords = simpleRecordRepository.findAllByCodeId(codeId);
    return simpleRecords.stream().map(SimpleRecordResponse::of).toList();
  }

  public List<SimpleRecordResponse> search(SimpleRecordSearchServiceRequest request) {
    List<SimpleRecord> simpleRecords = simpleRecordRepository.search(request.getCodeId(),
        request.getRecordType(),
        request.getDate());
    return simpleRecords.stream().map(SimpleRecordResponse::of).toList();
  }

  public void remove(SimpleRecordRemoveServiceRequest request) {
    Long id = request.getId();
    SimpleRecord simpleRecord = simpleRecordRepository.findById(id).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(SimpleRecord.class)));
    simpleRecordRepository.delete(simpleRecord);
  }

}
