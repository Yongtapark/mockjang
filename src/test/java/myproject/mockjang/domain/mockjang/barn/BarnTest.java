package myproject.mockjang.domain.mockjang.barn;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BarnTest extends IntegrationTestSupport {

  @DisplayName("축사에서 단일 축사칸을 등록한다.")
  @Test
  void registerOnePen() {
    //given
    Barn barn = Barn.builder()
        .barnId("1번축사")
        .build();

    Pen pen = Pen.builder()
        .penId("1-1")
        .barn(barn)
        .build();

    //when
    barn.registerPen(pen);

    //then
    assertThat(barn.getPens()).hasSize(1);
    assertThat(barn.getPens()).contains(pen);
  }

  @DisplayName("축사에서 여러 축사칸을 등록한다.")
  @Test
  void registerMultiPen() {
    //given
    Barn barn = Barn.builder()
        .barnId("1번축사")
        .build();

    Pen pen1 = Pen.builder()
        .penId("1-1")
        .barn(barn)
        .build();
    Pen pen2 = Pen.builder()
        .penId("1-2")
        .barn(barn)
        .build();

    //when
    barn.registerPen(List.of(pen1,pen2));

    //then
    assertThat(barn.getPens()).hasSize(2);
    assertThat(barn.getPens()).contains(pen1,pen2);
  }

}