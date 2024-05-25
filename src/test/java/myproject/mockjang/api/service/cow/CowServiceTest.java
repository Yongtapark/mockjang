package myproject.mockjang.api.service.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.exception.cow.CowStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class CowServiceTest extends IntegrationTestSupport {

  @Autowired
  private CowRepository cowRepository;
  @Autowired
  private CowService cowService;

  @DisplayName("소의 상태를 변경한다.")
  @Test
  void changeCowStatus() {
    //given
    Cow cow = createCow("0001", Gender.FEMALE);
    cowRepository.save(cow);

    //when
    cowService.changeCowStatus(cow, CowStatus.SLAUGHTERED);

    //then
    assertThat(cow.getCowStatus()).isEqualTo(CowStatus.SLAUGHTERED);
  }

  @DisplayName("도축 상태인 소는 단가를 입력할 수 있다.")
  @Test
  void registerUnitPrice() {
    //given
    Cow cow = createCow("0001", Gender.FEMALE, CowStatus.SLAUGHTERED);
    cowRepository.save(cow);

    //when
    cowService.registerUnitPrice(cow, UNIT_PRICE_100_000_000);

    //then
    assertThat(cow.getUnitPrice()).isEqualTo(UNIT_PRICE_100_000_000);
  }

  @DisplayName("도축 상태인 소가 아니면 단가 입력시 예외를 발생시킨다.")
  @Test
  void registerUnitPriceWithNotSlaughtered() {
    //given
    Cow cow = createCow("0001", Gender.FEMALE);
    cowRepository.save(cow);

    //when //then
    assertThatThrownBy(
        () -> cowService.registerUnitPrice(cow, UNIT_PRICE_100_000_000)).isInstanceOf(
            CowStatusException.class)
        .hasMessage(BUSINESS_ONLY_SLAUGHTERED_ERROR.getMessage());
  }

}