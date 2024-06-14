package myproject.mockjang.api.service.records.simple;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.simple.request.SimpleRecordUpdateServiceRequest;
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

  public SimpleRecordResponse findSimpleRecordById(Long id) {
    return SimpleRecordResponse.of(findById(id));
  }

  public void update(SimpleRecordUpdateServiceRequest request){
    SimpleRecord targetSimpleRecord = findById(request.getId());
    targetSimpleRecord.update(request.getCodeId(),request.getRecordType(),request.getDate(),request.getRecord());
    simpleRecordRepository.save(targetSimpleRecord);
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

  public void remove(Long id) {
    SimpleRecord simpleRecord = findById(id);
    simpleRecordRepository.delete(simpleRecord);
  }

  private SimpleRecord findById(Long id) {
    return simpleRecordRepository.findById(id).orElseThrow(
        () -> new NotExistException(Exceptions.COMMON_NOT_EXIST.formatMessage(SimpleRecord.class)));
  }

}
