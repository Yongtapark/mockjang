package myproject.mockjang.domain.records.mockjang.barn;

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
    Barn barn = Barn.builder().codeId("1번축사").build();
    barnRepository.save(barn);

    BarnRecord barnRecord1 = BarnRecord.builder().barn(barn).build();
    BarnRecord barnRecord2 = BarnRecord.builder().barn(barn).build();

    //when
    barnRecord1.writeNote("test1");
    barnRecord2.writeNote("test2");
    barnRecordRepository.save(barnRecord1);
    barnRecordRepository.save(barnRecord2);
    Barn findBarn = barnRepository.findById(barn.getId()).orElseThrow();
    List<BarnRecord> records = findBarn.getRecords();

    //then
    assertThat(records).hasSize(2);
    assertThat(records).contains(barnRecord1, barnRecord2);
  }

  @DisplayName("해당 이름을 가진 축사의 기록리스트를 반환한다.")
  @Test
  void findAllByBarn_CodeId() {
    Barn barn = Barn.builder().codeId("1번축사").build();
    barnRepository.save(barn);

    BarnRecord barnRecord1 = BarnRecord.builder().barn(barn).build();
    BarnRecord barnRecord2 = BarnRecord.builder().barn(barn).build();
    barnRecord1.writeNote("test1");
    barnRecord2.writeNote("test2");
    barnRecordRepository.save(barnRecord1);
    barnRecordRepository.save(barnRecord2);

    //when
    List<BarnRecord> barnRecords = barnRecordRepository.findAllByBarn_CodeId("1번축사");

    //then
    assertThat(barnRecords).hasSize(2);
    assertThat(barnRecords).contains(barnRecord1, barnRecord2);
  }
}