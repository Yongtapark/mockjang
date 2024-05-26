package myproject.mockjang.domain.mockjang.barn;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.pen.Pen;
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
    pen1.registerBarn(barn);
    pen2.registerBarn(barn);

    //when
    barn.deletePen(pen1);

    List<Pen> pens = barn.getPens();

    //then
    assertThat(pens).hasSize(1);
    assertThat(pens).doesNotContain(pen1);
    assertThat(pens).contains(pen2);
  }
}