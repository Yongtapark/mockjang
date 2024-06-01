package myproject.mockjang.api.service.records;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.records.SimpleRecord;
import myproject.mockjang.domain.records.SimpleRecordRepository;
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

    public List<SimpleRecordResponse> search(SimpleRecordSearchServiceRequest request){
        List<SimpleRecord> simpleRecords = simpleRecordRepository.search(request.getCodeId(), request.getRecordType(),
                request.getDate(),
                request.getRecord());
        return simpleRecords.stream().map(SimpleRecordResponse::of).toList();
    }

}
