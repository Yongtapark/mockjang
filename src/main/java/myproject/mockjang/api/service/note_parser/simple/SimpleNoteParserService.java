package myproject.mockjang.api.service.note_parser.simple;

import static myproject.mockjang.domain.note_parser.mockjang.NoteRegex.BARN;
import static myproject.mockjang.domain.note_parser.mockjang.NoteRegex.COW;
import static myproject.mockjang.domain.note_parser.mockjang.NoteRegex.PEN;
import static myproject.mockjang.domain.note_parser.mockjang.NoteRegex.values;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.note_parser.mockjang.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.NoteParserResponse;
import myproject.mockjang.api.service.note_parser.simple.request.SimpleNoteParserCreateServiceRequest;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.MockjangNoteContainer;
import myproject.mockjang.domain.note_parser.mockjang.NoteAndCodeId;
import myproject.mockjang.domain.note_parser.mockjang.NoteRegex;
import myproject.mockjang.domain.note_parser.simple.SimpleNoteContainer;
import myproject.mockjang.domain.note_parser.simple.SimpleNoteParserV0;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.simple.SimpleRecord;
import myproject.mockjang.domain.records.simple.SimpleRecordQueryRepository;
import myproject.mockjang.domain.records.simple.SimpleRecordRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SimpleNoteParserService {

  private final SimpleRecordRepository simpleRecordRepository;
  private final NoteParser<SimpleNoteContainer> simpleNoteParserV0;

  public NoteParserResponse parseNoteAndSaveRecord(SimpleNoteParserCreateServiceRequest request) {
    String context = request.getContext();
    LocalDateTime date = request.getDate();
    RecordType recordType = request.getRecordType();
    HashMap<String, Integer> names = request.getNames();

    List<NoteAndCodeId> noteAndCodeIds = parseNote(context);

    for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
      names.merge(noteAndCodeId.codeId(),1,Integer::sum);
      String codeId = noteAndCodeId.codeId();
      String note = noteAndCodeId.note();
      simpleRecordRepository.save(SimpleRecord.create(codeId, recordType, date, note));
    }

    return NoteParserResponse.of(names);
  }

  private List<NoteAndCodeId> parseNote(String context) {
    SimpleNoteContainer simpleNoteContainer = new SimpleNoteContainer();
    SimpleNoteContainer container = simpleNoteParserV0.extractAndSaveNotes(
        simpleNoteContainer, context);
    return container.getImmutable();
  }

}
