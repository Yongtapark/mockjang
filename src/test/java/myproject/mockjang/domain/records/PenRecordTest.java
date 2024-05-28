package myproject.mockjang.domain.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PenRecordTest extends IntegrationTestSupport {

  @DisplayName("단일 메모를 입력하면 축사칸의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().codeId("1번축사").build();
    Pen pen = Pen.builder().codeId("1-1").barn(barn).build();
    PenRecord penRecord1 = PenRecord.createRecord(pen);
    PenRecord penRecord2 = PenRecord.createRecord(pen);

    //when
    penRecord1.writeNote("test1");
    penRecord2.writeNote("test2");
    List<PenRecord> records = pen.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(penRecord1, penRecord2);
  }

}