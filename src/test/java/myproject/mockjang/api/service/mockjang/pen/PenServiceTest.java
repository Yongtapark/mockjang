package myproject.mockjang.api.service.mockjang.pen;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class PenServiceTest extends IntegrationTestSupport {

  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private PenRepository penRepository;

  @Autowired
  private PenService penService;

  @DisplayName("축사칸을 생성한다")
  @Test
  void createPen() {
    //given
    String penId = "1-1";
    Barn barn = Barn.createBarn("1번축사");
    barnRepository.save(barn);

    //when
    Pen pen = penService.createPen(penId, barn);

    //then
    assertThat(pen.getBarn()).isEqualTo(barn);
    assertThat(pen.getPenId()).isEqualTo(penId);
  }

  @DisplayName("해당 축사칸을 제거하고 축사의 축사칸 리스트에서 제거한다.")
  @Test
  void deletePen() {
    //given
    String penId = "1-1";
    Barn barn = Barn.createBarn("1번축사");
    barnRepository.save(barn);
    Pen pen = penService.createPen(penId, barn);

    //when
    penService.deletePen(pen);

    //then
    assertThat(barn.getPens()).isEmpty();
    assertThatThrownBy(() -> penRepository.findById(pen.getId()).orElseThrow()).isInstanceOf(
        NoSuchElementException.class);
  }

  @DisplayName("축사칸의 축사를 변경한다.")
  @Test
  void changeBarnWith() {
    //given
    Barn barn1 = Barn.createBarn("1번축사");
    Barn barn2 = Barn.createBarn("2번축사");
    barnRepository.save(barn1);
    String penId = "1-1";
    Pen pen = Pen.createPen(penId);
    pen.registerBarn(barn1);
    penRepository.save(pen);

    //when
    pen.changeBarnTo(barn2);

    //then
    assertThat(barn1.getPens()).isEmpty();
    assertThat(barn2.getPens()).hasSize(1);
    assertThat(barn2.getPens()).containsOnly(pen);
  }


}