package myproject.mockjang.domain.mockjang.barn;

import static myproject.mockjang.exception.Exceptions.COMMON_NO_UNDER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UPPER_GROUP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.AlreadyExistException;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BarnTest extends IntegrationTestSupport {

  @DisplayName("축사에서 단일 축사칸을 등록한다.")
  @Test
  void registerOnePen() {
    //given
    Barn barn = Barn.createBarn("1번축사");

    Pen pen = Pen.createPen("1-1");

    //when
    barn.addPen(pen);

    //then
    assertThat(barn.getPens()).hasSize(1);
    assertThat(barn.getPens()).contains(pen);
  }

  @DisplayName("축사에서 동일 id로 축사칸을 등록하면 예외를 발생시킨다.")
  @Test
  void registerWithDuplicatedPen() {
    //given
    Barn barn = Barn.createBarn("1번축사");

    Pen pen = Pen.createPen("1-1");

    //then //when
    barn.addPen(pen);
    assertThatThrownBy(()->barn.addPen(pen)).isInstanceOf(AlreadyExistException.class).hasMessage(
        Exceptions.COMMON_ALREADY_EXIST.formatMessage(pen.getCodeId()));

    assertThat(barn.getPens()).hasSize(1);
    assertThat(barn.getPens()).contains(pen);
  }

  @DisplayName("축사에서 여러 축사칸을 등록한다.")
  @Test
  void registerMultiPen() {
    //given
    Barn barn = Barn.createBarn("1번축사");

    Pen pen1 = Pen.createPen("1-1");
    Pen pen2 = Pen.createPen("1-2");

    //when
    barn.addPen(List.of(pen1, pen2));

    //then
    assertThat(barn.getPens()).hasSize(2);
    assertThat(barn.getPens()).contains(pen1, pen2);
  }

  @DisplayName("축사의 축사칸을 제거한다")
  @Test
  void deletePen() {
    //given
    Barn barn = Barn.createBarn("1번축사");
    Pen pen1 = Pen.createPen("1-1");
    Pen pen2 = Pen.createPen("1-2");
    pen1.registerUpperGroup(barn);
    pen2.registerUpperGroup(barn);

    //when
    barn.deletePen(pen1);

    List<Pen> pens = barn.getPens();

    //then
    assertThat(pens).hasSize(1);
    assertThat(pens).doesNotContain(pen1);
    assertThat(pens).contains(pen2);
  }

  @DisplayName("호출 시점에 상위 그룹이 존재하지 않을 시 예외를 발생시킨다.")
  @Test
  void getUpperGroupWithNoUpperGroup() {
    //given
    Barn barn = Barn.createBarn("1번축사");

    //when //then
    assertThatThrownBy(barn::getUpperGroup).isInstanceOf(ThereIsNoGroupException.class)
        .hasMessage(COMMON_NO_UPPER_GROUP.formatMessage(Barn.class));
  }

  @DisplayName("하위 그룹 리스트에 찾는 하위 그룹이 없다면 예외를 발생시킨다.")
  @Test
  void removeOneOfUnderGroupsWithNoUnderGroup() {
    //given
    Barn barn = Barn.createBarn("1번축사");
    Pen relatedPen = Pen.createPen("1-1");
    Pen noRelatedPen = Pen.createPen("1-1");

    relatedPen.registerUpperGroup(barn);

    //when //then
    assertThatThrownBy(() -> barn.removeOneOfUnderGroups(noRelatedPen)).isInstanceOf(
        ThereIsNoGroupException.class).hasMessage(COMMON_NO_UNDER_GROUP.formatMessage(Barn.class));
  }

  @DisplayName("상위 그룹을 변경을 호출 하면 예외를 발생시킨다.")
  @Test
  void changeUpperGroup() {
    //given
    Barn barn = Barn.createBarn("1번축사");
    Barn barnTemp = Barn.createBarn("2번축사");

    //when //then
    assertThatThrownBy(() -> barn.changeUpperGroup(barnTemp)).isInstanceOf(
            ThereIsNoGroupException.class)
        .hasMessage(COMMON_NO_UPPER_GROUP.formatMessage(Barn.class));
  }

}