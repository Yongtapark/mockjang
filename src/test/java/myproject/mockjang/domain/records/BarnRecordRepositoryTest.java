package myproject.mockjang.domain.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BarnRecordRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private BarnRecordRepository barnRecordRepository;

  @DisplayName("단일 메모를 입력하면 축사의 메모 리스트에 저장된다.")
  @Test
  void writeOneRecord() {
    //given
    Barn barn = Barn.builder().barnId("1번축사").build();
    barnRepository.save(barn);

    BarnRecord barnRecord1 = BarnRecord.builder().barn(barn).build();
    BarnRecord barnRecord2 = BarnRecord.builder().barn(barn).build();

    //when
    barnRecord1.writeDownMemo("test1");
    barnRecord2.writeDownMemo("test2");
    barnRecordRepository.save(barnRecord1);
    barnRecordRepository.save(barnRecord2);
    Barn findBarn = barnRepository.findById(barn.getId()).orElseThrow();
    List<BarnRecord> records = findBarn.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(barnRecord1, barnRecord2);
  }
}