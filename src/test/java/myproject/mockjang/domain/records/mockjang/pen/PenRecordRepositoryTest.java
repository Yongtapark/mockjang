package myproject.mockjang.domain.records.mockjang.pen;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PenRecordRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private PenRecordRepository penRecordRepository;

  @Autowired
  private PenRepository penRepository;

  @DisplayName("단일 메모를 입력하면 축사칸의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    LocalDateTime date = LocalDateTime.of(2024, 5, 31, 00, 00);

    Barn barn = Barn.builder().codeId(PARSER_BARN_CODE_ID_1).build();
    barnRepository.save(barn);

    Pen pen = Pen.builder()
        .codeId(PARSER_PEN_CODE_ID_1)
        .build();

    pen.registerUpperGroup(barn);
    penRepository.save(pen);

    PenRecord penRecord1 = PenRecord.createRecord(pen, RecordType.DAILY, date);
    PenRecord penRecord2 = PenRecord.createRecord(pen, RecordType.DAILY, date);

    //when
    penRecord1.writeNote("test1");
    penRecord2.writeNote("test2");
    penRecordRepository.saveAll(List.of(penRecord1, penRecord2));
    Pen findPen = penRepository.findById(pen.getId()).orElseThrow();
    List<PenRecord> records = findPen.getRecords();

    //then
    assertThat(findPen.getBarn()).isEqualTo(barn);
    assertThat(records).hasSize(2);
    assertThat(records).contains(penRecord1, penRecord2);
  }


}