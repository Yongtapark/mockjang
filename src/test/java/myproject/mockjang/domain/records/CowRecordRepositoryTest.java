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
    Barn barn = Barn.builder().codeId("1번축사").build();
    barnRepository.save(barn);
    Pen pen = Pen.builder().codeId("1-1").barn(barn).build();
    penRepository.save(pen);
    Cow cow = Cow.builder().codeId("1111").build();
    cow.registerUpperGroup(pen);
    cow.registerBarn(barn);
    cowRepository.save(cow);
    CowRecord cowRecord1 = CowRecord.createRecord(cow);
    CowRecord cowRecord2 = CowRecord.createRecord(cow);

    //when
    cowRecord1.writeNote("test1");
    cowRecord2.writeNote("test2");
    cowRecordRepository.save(cowRecord1);
    cowRecordRepository.save(cowRecord2);
    Cow findCow = cowRepository.findById(cow.getId()).orElseThrow();
    List<CowRecord> records = findCow.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(cowRecord1, cowRecord2);
  }

  @DisplayName("해당 이름을 가진 축사의 기록리스트를 반환한다.")
  @Test
  void findAllByBarn_CodeId() {
    Barn barn = Barn.builder().codeId("1번축사").build();
    barnRepository.save(barn);
    Pen pen = Pen.builder().codeId("1-1").barn(barn).build();
    penRepository.save(pen);
    Cow cow = Cow.builder().codeId("1111").build();
    cow.registerUpperGroup(pen);
    cow.registerBarn(barn);
    cowRepository.save(cow);
    CowRecord cowRecord1 = CowRecord.createRecord(cow);
    CowRecord cowRecord2 = CowRecord.createRecord(cow);
    cowRecord1.writeNote("test1");
    cowRecord2.writeNote("test2");
    cowRecordRepository.save(cowRecord1);
    cowRecordRepository.save(cowRecord2);

    //when
    List<CowRecord> allByCowCodeId = cowRecordRepository.findAllByCow_CodeId("1111");

    //then
    assertThat(allByCowCodeId).hasSize(2);
    assertThat(allByCowCodeId).contains(cowRecord1, cowRecord2);
  }

}