package myproject.mockjang.api.service.mockjang.note_parser;

import static myproject.mockjang.domain.note_parser.NoteRegex.*;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.note_parser.NoteAndCodeId;
import myproject.mockjang.domain.note_parser.NoteContainer;
import myproject.mockjang.domain.note_parser.NoteParserV0;
import myproject.mockjang.domain.note_parser.NoteRegex;
import myproject.mockjang.domain.records.BarnRecord;
import myproject.mockjang.domain.records.BarnRecordRepository;
import myproject.mockjang.domain.records.CowRecord;
import myproject.mockjang.domain.records.CowRecordRepository;
import myproject.mockjang.domain.records.PenRecord;
import myproject.mockjang.domain.records.PenRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class NoteParserService {
    private final BarnRepository barnRepository;
    private final PenRepository penRepository;
    private final CowRepository cowRepository;

    private final BarnRecordRepository barnRecordRepository;
    private final PenRecordRepository penRecordRepository;
    private final CowRecordRepository cowRecordRepository;
    private final NoteParserV0 noteParserV0;

    public void parseNoteAndSaveRecord(String context) {
        Map<NoteRegex, List<NoteAndCodeId>> immutableMap = parseNote(context);
        NoteRegex[] regexes = values();
        for (NoteRegex regex : regexes) {
            if(regex.equals(BARN)&&immutableMap.containsKey(regex)){
                saveBarnRecord(regex, immutableMap);
            }
            if(regex.equals(PEN)&&immutableMap.containsKey(regex)){
                savePenRecord(regex, immutableMap);
            }
            if(regex.equals(COW)&&immutableMap.containsKey(regex)){
                saveCowRecord(regex, immutableMap);
            }
        }
    }

    private void saveCowRecord(NoteRegex regex, Map<NoteRegex, List<NoteAndCodeId>> immutableMap) {
        List<NoteAndCodeId> noteAndCodeIds;
        noteAndCodeIds = immutableMap.get(regex);
        for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
            Cow findCow = cowRepository.findByCodeId(noteAndCodeId.codeId());
            CowRecord cowRecord = CowRecord.createRecord(findCow);
            cowRecord.writeNote(noteAndCodeId.note());
            cowRecordRepository.save(cowRecord);
        }
    }

    private void savePenRecord(NoteRegex regex, Map<NoteRegex, List<NoteAndCodeId>> immutableMap) {
        List<NoteAndCodeId> noteAndCodeIds;
        noteAndCodeIds = immutableMap.get(regex);
        for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
            Pen findPen = penRepository.findByCodeId(noteAndCodeId.codeId());
            PenRecord penRecord = PenRecord.createRecord(findPen);
            penRecord.writeNote(noteAndCodeId.note());
            penRecordRepository.save(penRecord);
        }
    }

    private void saveBarnRecord(NoteRegex regex, Map<NoteRegex, List<NoteAndCodeId>> immutableMap) {
        List<NoteAndCodeId> noteAndCodeIds;
        noteAndCodeIds = immutableMap.get(regex);
        for (NoteAndCodeId noteAndCodeId : noteAndCodeIds) {
            Barn findBarn = barnRepository.findByCodeId(noteAndCodeId.codeId());
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
