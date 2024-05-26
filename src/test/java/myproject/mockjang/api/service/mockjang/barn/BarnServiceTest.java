package myproject.mockjang.api.service.mockjang.barn;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BarnServiceTest extends IntegrationTestSupport {

  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private BarnService barnService;

  @DisplayName("축사를 생성한다.")
  @Test
  void createBarn() {
    //given
    String barnId = "1번축사";

    //when
    Barn barn = barnService.createBarn(barnId);
    Barn savedBarn = barnRepository.findById(barn.getId()).orElseThrow();

    //then
    assertThat(barn).isEqualTo(savedBarn);
  }

  @DisplayName("축사를 제거한다.")
  @Test
  void test() {
    //given
    String barnId1 = "1번축사";
    String barnId2 = "2번축사";

    Barn barn1 = barnService.createBarn(barnId1);
    Barn barn2 = barnService.createBarn(barnId2);

    Barn savedBarn1 = barnRepository.findById(barn1.getId()).orElseThrow();
    Barn savedBarn2 = barnRepository.findById(barn2.getId()).orElseThrow();

    //when
    barnRepository.delete(savedBarn1);
    List<Barn> barns = barnRepository.findAll();

    //then
    assertThat(barns).hasSize(1);
    assertThat(barns).contains(savedBarn2);
    assertThat(barns).doesNotContain(savedBarn1);


  }

}