package myproject.mockjang.domain.records.mockjang.cow;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CowRecordTest extends IntegrationTestSupport {

  @DisplayName("단일 메모를 입력하면 소의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().codeId(PARSER_BARN_CODE_ID_1).build();
    Pen pen = Pen.builder().codeId(PARSER_PEN_CODE_ID_1).barn(barn).build();
    Cow cow = Cow.builder().codeId(PARSER_COW_CODE_ID_1).build();
    cow.registerUpperGroup(pen);
    cow.registerBarn(barn);

    CowRecord cowRecord1 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
    CowRecord cowRecord2 = CowRecord.createRecord(cow, RecordType.DAILY, TEMP_DATE);
    ;

    //when
    cowRecord1.recordMemo("test1");
    cowRecord2.recordMemo("test2");
    List<CowRecord> records = cow.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(cowRecord1, cowRecord2);
  }

}