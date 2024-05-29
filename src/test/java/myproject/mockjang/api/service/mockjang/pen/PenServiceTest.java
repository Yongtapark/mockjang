package myproject.mockjang.api.service.mockjang.pen;

import static myproject.mockjang.exception.Exceptions.COMMON_BLANK_STRING;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_STRING_OVER_10;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.common.StringException;
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
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);

    //when
    Pen pen = penService.createPen(PARSER_PEN_CODE_ID_1, barn);

    //then
    assertThat(pen.getBarn()).isEqualTo(barn);
    assertThat(pen.getCodeId()).isEqualTo(PARSER_PEN_CODE_ID_1);
  }

  @DisplayName("해당 축사칸을 제거하고 축사의 축사칸 리스트에서 제거한다.")
  @Test
  void delete() {
    //given
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);
    Pen pen = penService.createPen(PARSER_PEN_CODE_ID_1, barn);

    //when
    penService.delete(pen);

    //then
    assertThat(barn.getPens()).isEmpty();
    assertThatThrownBy(() -> penRepository.findById(pen.getId()).orElseThrow()).isInstanceOf(
        NoSuchElementException.class);
  }

  @DisplayName("축사칸의 축사를 변경한다.")
  @Test
  void changeBarnWith() {
    //given
    Barn barn1 = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    Barn barn2 = Barn.createBarn(PARSER_BARN_CODE_ID_2);
    barnRepository.save(barn1);
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    pen.registerUpperGroup(barn1);
    penRepository.save(pen);

    //when
    pen.changeUpperGroup(barn2);

    //then
    assertThat(barn1.getPens()).isEmpty();
    assertThat(barn2.getPens()).hasSize(1);
    assertThat(barn2.getPens()).containsOnly(pen);
  }


  @DisplayName("축사 이름에 빈 문자열이 들어올 경우 예외를 발생시킨다.")
  @Test
  void createBarnWithEmptyBarnId() {
    //given // when //then
    assertThatThrownBy(() -> penService.createPen(STRING_EMPTY, null)).isInstanceOf(
        StringException.class).hasMessage(COMMON_BLANK_STRING.getMessage());
  }

  @DisplayName("축사 이름에 공백만 들어올 경우 예외를 발생시킨다.")
  @Test
  void createBarnWithOnlySpaceBarnId() {
    //given // when //then
    assertThatThrownBy(() -> penService.createPen(STRING_EMPTY, null)).isInstanceOf(
        StringException.class).hasMessage(COMMON_BLANK_STRING.getMessage());
  }

  @DisplayName("축사 이름이 10글자를 넘어가면 예외를 발생시킨다.")
  @Test
  void createBarnWithOver10Size() {
    //given // when //then
    assertThatThrownBy(() -> penService.createPen(STRING_OVER_10, null)).isInstanceOf(
        StringException.class).hasMessage(COMMON_STRING_OVER_10.getMessage());
  }

  @DisplayName("축사 칸 리스트를 조회한다.")
  @Test
  void findAll() {
    //given
    Pen pen1 = Pen.createPen(PARSER_PEN_CODE_ID_1);
    Pen pen2 = Pen.createPen(PARSER_PEN_CODE_ID_2);
    penRepository.save(pen1);
    penRepository.save(pen2);

    //when
    List<Pen> pens = penService.findAll();

    //then
    assertThat(pens).containsExactly(pen1, pen2);
  }

  @DisplayName("축사칸을 단일 조회한다.")
  @Test
  void findByCodeId() {
    //given
    Pen pen = Pen.createPen(PARSER_PEN_CODE_ID_1);
    penRepository.save(pen);

    //when
    Pen findPen = penService.findByCodeId(PARSER_PEN_CODE_ID_1);

    //then
    assertThat(findPen).isEqualTo(pen);
  }

  @DisplayName("없는 축사칸을 단일 조회할 시 예외를 발생시킨다.")
  @Test
  void findByCodeIdWithNoData() {
    //given //when //then
    assertThatThrownBy(() -> penService.findByCodeId(PARSER_PEN_CODE_ID_1)).isInstanceOf(
        NotExistException.class).hasMessage(COMMON_NOT_EXIST.formatMessage(PARSER_PEN_CODE_ID_1));
  }


}