package myproject.mockjang.domain.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CowRecordRepositoryTest extends IntegrationTestSupport {
  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private PenRepository penRepository;
  @Autowired
  private CowRepository cowRepository;
  @Autowired
  private CowRecordRepository cowRecordRepository;

  @DisplayName("단일 메모를 입력하면 소의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().barnId("1번축사").build();
    barnRepository.save(barn);
    Pen pen = Pen.builder().penId("1-1").barn(barn).build();
    penRepository.save(pen);
    Cow cow = Cow.builder().cowId("1111").build();
    cow.registerPen(pen);
    cow.registerBarn(barn);
    cowRepository.save(cow);
    CowRecord cowRecord1 = CowRecord.createMemo(cow);
    CowRecord cowRecord2 = CowRecord.createMemo(cow);

    //when
    cowRecord1.writeDownMemo("test1");
    cowRecord2.writeDownMemo("test2");
    cowRecordRepository.save(cowRecord1);
    cowRecordRepository.save(cowRecord2);
    Cow findCow = cowRepository.findById(cow.getId()).orElseThrow();
    List<CowRecord> records = findCow.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(cowRecord1, cowRecord2);
  }

}