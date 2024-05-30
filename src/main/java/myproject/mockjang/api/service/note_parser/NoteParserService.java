package myproject.mockjang.api.service.note_parser;

import static myproject.mockjang.domain.note_parser.NoteRegex.BARN;
import static myproject.mockjang.domain.note_parser.NoteRegex.COW;
import static myproject.mockjang.domain.note_parser.NoteRegex.PEN;
import static myproject.mockjang.domain.note_parser.NoteRegex.values;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.note_parser.request.NoteParserCreateServiceRequest;
import myproject.mockjang.api.service.note_parser.response.NoteParserResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.note_parser.NoteAndCodeId;
import myproject.mockjang.domain.note_parser.NoteContainer;
import myproject.mockjang.domain.note_parser.NoteParser;
import myproject.mockjang.domain.note_parser.NoteParserV0;
import myproject.mockjang.domain.note_parser.NoteRegex;
import myproject.mockjang.domain.records.BarnRecord;
import myproject.mockjang.domain.records.BarnRecordRepository;
import myproject.mockjang.domain.records.CowRecord;
import myproject.mockjang.domain.records.CowRecordRepository;
import myproject.mockjang.domain.records.PenRecord;
import myproject.mockjang.domain.records.PenRecordRepository;
import myproject.mockjang.exception.Exceptions;
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
  private final NoteParser noteParserV0;

  public NoteParserResponse parseNoteAndSaveRecord(NoteParserCreateServiceRequest request) {
    String context = request.getContext();
    HashMap<String,Integer> names = request.getNames();
    Map<NoteRegex, List<NoteAndCodeId>> immutableMap = parseNote(context);
    NoteRegex[] regexes = values();
    for (NoteRegex regex : regexes) {
      if (regex.equals(BARN) && immutableMap.containsKey(regex)) {
        saveBarnRecord(regex, immutableMap,names);
      }
      if (regex.equals(PEN) && immutableMap.containsKey(regex)) {
        savePenRecord(regex, immutableMap,names);
      }
      if (regex.equals(COW) && immutableMap.containsKey(regex)) {
        saveCowRecord(regex, immutableMap,names);
      }
    }
    return NoteParserResponse.of(names);
  }

  private void saveCowRecord(NoteRegex regex, Map<NoteRegex, List<NoteAndCodeId>> immutableMap, HashMap<String,Integer> names) {
    List<NoteAndCodeId> noteAndCodeIds;
    noteAndCodeIds = immutableMap.get(regex);
    for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
      names.merge(noteAndCodeId.codeId(),1,Integer::sum);
      Cow findCow = cowRepository.findByCodeId(noteAndCodeId.codeId())
          .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, noteAndCodeId.codeId()));
      CowRecord cowRecord = CowRecord.createRecord(findCow);
      cowRecord.writeNote(noteAndCodeId.note());
      cowRecordRepository.save(cowRecord);
    }
  }

  private void savePenRecord(NoteRegex regex, Map<NoteRegex, List<NoteAndCodeId>> immutableMap, HashMap<String,Integer> names) {
    List<NoteAndCodeId> noteAndCodeIds;
    noteAndCodeIds = immutableMap.get(regex);
    for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
      names.merge(noteAndCodeId.codeId(),1,Integer::sum);
      Pen findPen = penRepository.findByCodeId(noteAndCodeId.codeId())
          .orElseThrow(() -> new NotExistException(
              COMMON_NOT_EXIST, noteAndCodeId.codeId()));
      PenRecord penRecord = PenRecord.createRecord(findPen);
      penRecord.writeNote(noteAndCodeId.note());
      penRecordRepository.save(penRecord);
    }
  }

  private void saveBarnRecord(NoteRegex regex, Map<NoteRegex, List<NoteAndCodeId>> immutableMap, HashMap<String,Integer> names) {
    List<NoteAndCodeId> noteAndCodeIds;
    noteAndCodeIds = immutableMap.get(regex);
    for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
      names.merge(noteAndCodeId.codeId(),1,Integer::sum);
      Barn findBarn = barnRepository.findByCodeId(noteAndCodeId.codeId())
          .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, noteAndCodeId.codeId()));
      BarnRecord barnRecord = BarnRecord.creatRecord(findBarn);
      barnRecord.writeNote(noteAndCodeId.note());
      barnRecordRepository.save(barnRecord);
    }
  }

  private Map<NoteRegex, List<NoteAndCodeId>> parseNote(String context) {
    NoteContainer noteContainer = new NoteContainer();
    NoteContainer parsedNoteContainer = noteParserV0.extractAndSaveNotes(noteContainer, context);
    return parsedNoteContainer.getImmutableMap();
  }

}

