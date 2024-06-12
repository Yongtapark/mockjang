package myproject.mockjang.api.service.note_parser.mockjang;

import static myproject.mockjang.domain.note_parser.NoteRegex.BARN;
import static myproject.mockjang.domain.note_parser.NoteRegex.COW;
import static myproject.mockjang.domain.note_parser.NoteRegex.PEN;
import static myproject.mockjang.domain.note_parser.NoteRegex.values;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.note_parser.mockjang.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.mockjang.response.NoteParserResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.mockjang.MockjangNoteContainer;
import myproject.mockjang.domain.note_parser.mockjang.RecordAndCodeId;
import myproject.mockjang.domain.note_parser.NoteRegex;
import myproject.mockjang.domain.records.RecordType;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecord;
import myproject.mockjang.domain.records.mockjang.barn.BarnRecordRepository;
import myproject.mockjang.domain.records.mockjang.cow.CowRecord;
import myproject.mockjang.domain.records.mockjang.cow.CowRecordRepository;
import myproject.mockjang.domain.records.mockjang.pen.PenRecord;
import myproject.mockjang.domain.records.mockjang.pen.PenRecordRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class NoteParserService {

  private final BarnRepository barnRepository;
  private final PenRepository penRepository;
  private final CowRepository cowRepository;

  private final BarnRecordRepository barnRecordRepository;
  private final PenRecordRepository penRecordRepository;
  private final CowRecordRepository cowRecordRepository;
  private final NoteParser<MockjangNoteContainer> noteParserV0;

  public NoteParserResponse parseNoteAndSaveRecord(NoteParserCreateServiceRequest request) {
    String context = request.getContext();
    LocalDateTime date = request.getDate();
    RecordType recordType = request.getRecordType();
    HashMap<String, Integer> names = request.getNames();
    Map<NoteRegex, List<RecordAndCodeId>> immutableMap = parseNote(context);
    NoteRegex[] regexes = values();
    for (NoteRegex regex : regexes) {
      if (regex.equals(BARN) && immutableMap.containsKey(regex)) {
        saveBarnRecord(regex, immutableMap, names, date, recordType);
      }
      if (regex.equals(PEN) && immutableMap.containsKey(regex)) {
        savePenRecord(regex, immutableMap, names, date, recordType);
      }
      if (regex.equals(COW) && immutableMap.containsKey(regex)) {
        saveCowRecord(regex, immutableMap, names, date, recordType);
      }
    }
    return NoteParserResponse.of(names);
  }

  private void saveCowRecord(NoteRegex regex, Map<NoteRegex, List<RecordAndCodeId>> immutableMap,
      HashMap<String, Integer> names, LocalDateTime date, RecordType recordType) {
    List<RecordAndCodeId> recordAndCodeIds;
    recordAndCodeIds = immutableMap.get(regex);
    for (RecordAndCodeId recordAndCodeId : recordAndCodeIds) {
      names.merge(recordAndCodeId.codeId(), 1, Integer::sum);
      Cow findCow = cowRepository.findByCodeId(recordAndCodeId.codeId())
          .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, recordAndCodeId.codeId()));
      CowRecord cowRecord = CowRecord.createRecord(findCow, recordType, date);
      cowRecord.recordMemo(recordAndCodeId.record());
      cowRecordRepository.save(cowRecord);
    }
  }

  private void savePenRecord(NoteRegex regex, Map<NoteRegex, List<RecordAndCodeId>> immutableMap,
      HashMap<String, Integer> names, LocalDateTime date, RecordType recordType) {
    List<RecordAndCodeId> recordAndCodeIds;
    recordAndCodeIds = immutableMap.get(regex);
    for (RecordAndCodeId recordAndCodeId : recordAndCodeIds) {
      names.merge(recordAndCodeId.codeId(), 1, Integer::sum);
      Pen findPen = penRepository.findByCodeId(recordAndCodeId.codeId())
          .orElseThrow(() -> new NotExistException(
              COMMON_NOT_EXIST, recordAndCodeId.codeId()));
      PenRecord penRecord = PenRecord.createRecord(findPen, recordType, date);
      penRecord.writeNote(recordAndCodeId.record());
      penRecordRepository.save(penRecord);
    }
  }

  private void saveBarnRecord(NoteRegex regex, Map<NoteRegex, List<RecordAndCodeId>> immutableMap,
      HashMap<String, Integer> names, LocalDateTime date, RecordType recordType) {
    List<RecordAndCodeId> recordAndCodeIds;
    recordAndCodeIds = immutableMap.get(regex);
    for (RecordAndCodeId recordAndCodeId : recordAndCodeIds) {
      names.merge(recordAndCodeId.codeId(), 1, Integer::sum);
      Barn findBarn = barnRepository.findByCodeId(recordAndCodeId.codeId())
          .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, recordAndCodeId.codeId()));
      BarnRecord barnRecord = BarnRecord.creatRecord(findBarn, recordType, date);
      barnRecord.writeNote(recordAndCodeId.record());
      barnRecordRepository.save(barnRecord);
    }
  }

  private Map<NoteRegex, List<RecordAndCodeId>> parseNote(String context) {
    MockjangNoteContainer mockjangNoteContainer = new MockjangNoteContainer();
    MockjangNoteContainer parsedMockjangNoteContainer = noteParserV0.extractAndSaveNotes(
        mockjangNoteContainer, context);
    return parsedMockjangNoteContainer.getImmutableMap();
  }

}

