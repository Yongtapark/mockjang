package myproject.mockjang.api.service.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.exception.cow.CowStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CowServiceTest extends IntegrationTestSupport {

  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private PenRepository penRepository;
  @Autowired
  private CowRepository cowRepository;
  @Autowired
  private CowService cowService;

  @DisplayName("현재 사육중인 소를 생성한다.")
  @Test
  void createRaisingCow() {
    //given
    Barn barn = Barn.createBarn("1번축사");
    barnRepository.save(barn);
    Pen pen = Pen.createPen("1-1");
    pen.registerBarn(barn);
    penRepository.save(pen);
    LocalDateTime birthDate = LocalDateTime.of(2024, 5, 25, 0, 0);

    //when
    Cow raisingCow = cowService.createRaisingCow("0001", Gender.FEMALE, pen, birthDate);
    Cow findRasisingCow = cowRepository.findById(raisingCow.getId()).orElseThrow();

    //then
    assertThat(findRasisingCow.getBarn()).isEqualTo(barn);
    assertThat(findRasisingCow.getPen()).isEqualTo(pen);

  }

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

  @DisplayName("축사칸을 변경 할 수 있다.")
  @Test
  void changePen() {
    //given
    Barn barn1 = Barn.createBarn("1번축사");
    Barn barn2 = Barn.createBarn("2번축사");

    barnRepository.save(barn1);
    barnRepository.save(barn2);

    Pen pen1 = Pen.createPen("1-1");
    Pen pen2 = Pen.createPen("1-2");
    pen1.registerBarn(barn1);
    pen2.registerBarn(barn2);
    penRepository.save(pen1);
    penRepository.save(pen2);

    Cow cow = createCow("0001", Gender.FEMALE);
    cow.registerPen(pen1);
    cowRepository.save(cow);

    //when
    cow.changePen(pen2);

    List<Cow> cows1 = penRepository.findById(pen1.getId()).orElseThrow().getCows();
    List<Cow> cows2 = penRepository.findById(pen2.getId()).orElseThrow().getCows();

    //then
    assertThat(cow.getBarn()).isEqualTo(barn2);
    assertThat(cow.getPen()).isEqualTo(pen2);
    assertThat(cows1).isEmpty();
    assertThat(cows2).containsOnly(cow);
  }

  @DisplayName("소를 제거하면 축사칸의 리스트에서도 제거된다.")
  @Test
  void deleteCow() {
    //given
    Pen pen = Pen.createPen("1번축사");
    penRepository.save(pen);
    Cow cow = createCow("1111");
    cow.registerPen(pen);
    cowRepository.save(cow);

    //when
    cowService.deleteCow(cow);

    //then
    assertThatThrownBy(() -> cowRepository.findById(cow.getId()).orElseThrow())
        .isInstanceOf(NoSuchElementException.class);
    assertThat(pen.getCows()).isEmpty();
  }

}