package myproject.mockjang.domain.records;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CowRecordTest extends IntegrationTestSupport {

  @DisplayName("단일 메모를 입력하면 소의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().barnId("1번축사").build();
    Pen pen = Pen.builder().penId("1-1").barn(barn).build();
    Cow cow = Cow.builder().cowId("1111").build();
    CowRecord cowRecord1 = CowRecord.builder().barn(barn).pen(pen).cow(cow).build();
    CowRecord cowRecord2 = CowRecord.builder().barn(barn).pen(pen).cow(cow).build();

    //when
    cowRecord1.writeRecord("test1");
    cowRecord2.writeRecord("test2");
    List<CowRecord> records = cow.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(cowRecord1, cowRecord2);
  }

}