package myproject.mockjang.domain.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BarnRecordTest extends IntegrationTestSupport {

  @DisplayName("단일 메모를 입력하면 축사의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().barnId("1번축사").build();
    BarnRecord barnRecord1 = BarnRecord.builder().barn(barn).build();
    BarnRecord barnRecord2 = BarnRecord.builder().barn(barn).build();

    //when
    barnRecord1.writeRecord("test1");
    barnRecord2.writeRecord("test2");
    List<BarnRecord> records = barn.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(barnRecord1, barnRecord2);
  }

}