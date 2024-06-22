package myproject.mockjang.api.service.note_parser.simple;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.note_parser.mockjang.response.RecordParserResponse;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserUploadTempDataServiceRequest;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.RecordAndCodeId;
import myproject.mockjang.domain.note_parser.simple.SimpleRecordContainer;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.simple.SimpleRecord;
import myproject.mockjang.domain.records.simple.SimpleRecordRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleNoteParserService {

  private final SimpleRecordRepository simpleRecordRepository;
  private final NoteParser<SimpleRecordContainer> simpleNoteParserV0;

  public RecordParserResponse parseNoteAndSaveRecord(SimpleNoteParserCreateServiceRequest request) {
    String context = request.getContext();
    LocalDateTime date = request.getDate();
    RecordType recordType = request.getRecordType();
    HashMap<String, Integer> names = request.getNames();

    List<RecordAndCodeId> recordAndCodeIds = parseNote(context);

    for (RecordAndCodeId recordAndCodeId : recordAndCodeIds) {
      names.merge(recordAndCodeId.codeId(), 1, Integer::sum);
      String codeId = recordAndCodeId.codeId();
      String note = recordAndCodeId.record();
      simpleRecordRepository.save(SimpleRecord.create(codeId, recordType, date, note));
    }

    return RecordParserResponse.of(names);
  }

  private List<RecordAndCodeId> parseNote(String context) {
    SimpleRecordContainer simpleRecordContainer = new SimpleRecordContainer();
    SimpleRecordContainer container = simpleNoteParserV0.extractAndSaveNotes(
            simpleRecordContainer, context);
    return container.getNotes();
  }

  public void uploadTempRecords(SimpleNoteParserUploadTempDataServiceRequest request) {
    SimpleRecordContainer simpleRecordContainer = request.getSimpleRecordContainer();
    List<RecordAndCodeId> immutable = simpleRecordContainer.getNotes();
    ArrayList<SimpleRecord> simpleRecords = new ArrayList<>();
    for (RecordAndCodeId recordAndCodeId : immutable) {
      String codeId = recordAndCodeId.codeId();
      String note = recordAndCodeId.record();
      SimpleRecord simpleRecord = SimpleRecord.create(codeId, simpleRecordContainer.getRecordType(),
              simpleRecordContainer.getDate(), note);
      simpleRecords.add(simpleRecord);
    }
    simpleRecordRepository.saveAll(simpleRecords);
  }

}
