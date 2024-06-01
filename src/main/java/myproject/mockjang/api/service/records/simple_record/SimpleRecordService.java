package myproject.mockjang.api.service.records.simple_record;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordCreateServiceRequest;
import myproject.mockjang.api.service.records.simple_record.request.SimpleRecordSearchServiceRequest;
import myproject.mockjang.api.service.records.simple_record.response.SimpleRecordResponse;
import myproject.mockjang.domain.records.simple_record.SimpleRecord;
import myproject.mockjang.domain.records.simple_record.SimpleRecordRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleRecordService {

    private final SimpleRecordRepository simpleRecordRepository;

    public SimpleRecordResponse create(SimpleRecordCreateServiceRequest request) {
        SimpleRecord simpleRecord = SimpleRecord.create(request.getCodeId(), request.getRecordType(), request.getDate(),
                request.getRecord());
        simpleRecordRepository.save(simpleRecord);
        return SimpleRecordResponse.of(simpleRecord);
    }

    public List<SimpleRecordResponse> search(SimpleRecordSearchServiceRequest request) {
        List<SimpleRecord> simpleRecords = simpleRecordRepository.search(request.getCodeId(), request.getRecordType(),
                request.getDate());
        return simpleRecords.stream().map(SimpleRecordResponse::of).toList();
    }

}
