package myproject.mockjang.domain.mockjang.cow;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.exception.cow.CowStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

class CowTest extends IntegrationTestSupport {

  @Autowired
  MessageSource messageSource;

  @DisplayName("부모가 자식을 등록 할 수 있다.")
  @Test
  void registerAllChildren() {
    //given
    Cow mom = createCow("0001", Gender.FEMALE);
    Cow child1 = createCow("0003", Gender.MALE);
    Cow child2 = createCow("0004", Gender.MALE);
    Cow child3 = createCow("0005", Gender.MALE);
    List<Cow> children = List.of(child1, child2, child3);

    //when
    mom.registerAllChildren(children);

    //then
    assertThat(children).hasSize(3);
    assertThat(mom.getChildren()).contains(child1, child2, child3);
  }

  @DisplayName("부모가 자식을 등록 할 수 있다.")
  @Test
  void registerAllChildrenF() {
    //given
    Cow dad = createCow("0001", Gender.MALE);
    Cow child1 = createCow("0003", Gender.MALE);
    Cow child2 = createCow("0004", Gender.MALE);
    Cow child3 = createCow("0005", Gender.MALE);
    List<Cow> children = List.of(child1, child2, child3);

    //when
    dad.registerAllChildren(children);

    //then
    assertThat(children).hasSize(3);
    assertThat(dad.getChildren()).contains(child1, child2, child3);
  }

  @DisplayName("자식 소가 부모 소를 등록할 수 있다.")
  @Test
  void registerParent() {
    //given
    Cow mom = createCow("0001", Gender.FEMALE);
    Cow dad = createCow("0002", Gender.MALE);

    Cow child1 = createCow("0003", Gender.MALE);
    //when
    child1.registerParent(mom);
    child1.registerParent(dad);

    //then
    assertThat(child1.getDad()).isEqualTo(dad);
    assertThat(child1.getMom()).isEqualTo(mom);
    assertThat(mom.getChildren()).hasSize(1);
  }

  @DisplayName("소의 도축상태를 변경한다.")
  @Test
  void changeSlaughterStatus() {
    //given
    Cow cow = createCow("0001", Gender.MALE);

    //when
    cow.changeSlaughterStatus(CowStatus.SLAUGHTERED);

    //then
    assertThat(cow.getCowStatus()).isEqualTo(CowStatus.SLAUGHTERED);
  }

  @DisplayName("도축 상태인 소는 단가를 입력할 수 있다.")
  @Test
  void registerUnitPrice() {
    //given
    Cow cow = createCow("0001", Gender.MALE);
    cow.changeCowStatus(CowStatus.SLAUGHTERED);

    //when
    cow.registerUnitPrice(UNIT_PRICE_100_000_000);

    //then
    assertThat(cow.getUnitPrice()).isEqualTo(UNIT_PRICE_100_000_000);
  }

  @DisplayName("비 도축 상태인 소는 단가를 입력하면 예외를 발생시킨다.")
  @Test
  void registerUnitPriceWithNotSlaughtered() {
    //given
    Cow cow = createCow("0001", Gender.MALE,CowStatus.RAISING);

    //when //then
    assertThatThrownBy(() -> cow.registerUnitPrice(UNIT_PRICE_100_000_000)).isInstanceOf(
        CowStatusException.class).hasMessage(Exceptions.DOMAIN_ONLY_SLAUGHTERED_ERROR.getMessage());
  }

  @DisplayName("cowStatus가 null인 소는 단가를 입력하면 예외를 발생시킨다.")
  @Test
  void registerUnitPriceWithNull() {
    //given
    Cow cow = createCow("0001", Gender.MALE);

    //when //then
    assertThatThrownBy(() -> cow.registerUnitPrice(UNIT_PRICE_100_000_000)).isInstanceOf(
        CowStatusException.class).hasMessage(Exceptions.DOMAIN_ONLY_SLAUGHTERED_ERROR.getMessage());
  }
}