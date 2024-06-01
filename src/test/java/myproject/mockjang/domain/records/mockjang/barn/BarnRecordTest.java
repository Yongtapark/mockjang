package myproject.mockjang.domain.records.mockjang.barn;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.records.RecordType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BarnRecordTest extends IntegrationTestSupport {

  @DisplayName("단일 메모를 입력하면 축사의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().codeId(PARSER_BARN_CODE_ID_1).build();
    BarnRecord barnRecord1 = BarnRecord.creatRecord(barn, RecordType.DAILY,TEMP_DATE);
    BarnRecord barnRecord2 = BarnRecord.creatRecord(barn,RecordType.DAILY,TEMP_DATE);

    //when
    barnRecord1.writeNote("test1");
    barnRecord2.writeNote("test2");
    List<BarnRecord> records = barn.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(barnRecord1, barnRecord2);
  }

}