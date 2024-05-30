package myproject.mockjang.api.service.mockjang.barn;

import static myproject.mockjang.exception.Exceptions.COMMON_BLANK_STRING;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_STRING_OVER_10;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.mockjang.barn.request.BarnCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.barn.request.BarnFindByCodeIdServiceRequest;
import myproject.mockjang.api.service.mockjang.barn.response.BarnResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.common.StringException;
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

    BarnCreateServiceRequest request = BarnCreateServiceRequest.builder()
        .codeId(PARSER_BARN_CODE_ID_1).build();
    //when
    BarnResponse barnResponse = barnService.createBarn(request);
    Barn savedBarn = barnRepository.findById(barnResponse.getId()).orElseThrow();

    //then
    assertThat(barnResponse.getCodeId()).isEqualTo(savedBarn.getCodeId());
  }

  @DisplayName("축사를 제거한다.")
  @Test
  void delete() {
    //given
    BarnCreateServiceRequest request1 = BarnCreateServiceRequest.builder()
        .codeId(PARSER_BARN_CODE_ID_1).build();
    BarnCreateServiceRequest request2 = BarnCreateServiceRequest.builder()
        .codeId(PARSER_BARN_CODE_ID_2).build();

    BarnResponse barnResponse1 = barnService.createBarn(request1);
    BarnResponse barnResponse2 = barnService.createBarn(request2);

    Barn savedBarn1 = barnRepository.findById(barnResponse1.getId()).orElseThrow();
    Barn savedBarn2 = barnRepository.findById(barnResponse2.getId()).orElseThrow();

    //when
    barnService.delete(savedBarn1);
    List<Barn> barns = barnRepository.findAll();

    //then
    assertThat(barns).hasSize(1);
    assertThat(barns).contains(savedBarn2);
    assertThat(barns).doesNotContain(savedBarn1);
  }

  @DisplayName("축사 이름에 빈 문자열이 들어올 경우 예외를 발생시킨다.")
  @Test
  void createBarnWithEmptyBarnId() {
    //given
    BarnCreateServiceRequest request = BarnCreateServiceRequest.builder()
        .codeId(STRING_EMPTY).build();
    //when //then
    assertThatThrownBy(() -> barnService.createBarn(request)).isInstanceOf(
            StringException.class)
        .hasMessage(COMMON_BLANK_STRING.getMessage());
  }

  @DisplayName("축사 이름에 공백만 들어올 경우 예외를 발생시킨다.")
  @Test
  void createBarnWithOnlySpaceBarnId() {
    //given
    BarnCreateServiceRequest request = BarnCreateServiceRequest.builder()
        .codeId(STRING_ONLY_SPACE).build();

    //when //then
    assertThatThrownBy(() -> barnService.createBarn(request)).isInstanceOf(
            StringException.class)
        .hasMessage(COMMON_BLANK_STRING.getMessage());
  }

  @DisplayName("축사 이름이 10글자를 넘어가면 예외를 발생시킨다.")
  @Test
  void createBarnWithOver10Size() {
    //given
    BarnCreateServiceRequest request = BarnCreateServiceRequest.builder()
        .codeId(STRING_OVER_10).build();

    //when //then
    assertThatThrownBy(() -> barnService.createBarn(request)).isInstanceOf(
            StringException.class)
        .hasMessage(COMMON_STRING_OVER_10.getMessage());
  }

  @DisplayName("축사 리스트를 조회한다.")
  @Test
  void findAll() {
    //given
    Barn barn1 = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    Barn barn2 = Barn.createBarn(PARSER_BARN_CODE_ID_2);
    barnRepository.save(barn1);
    barnRepository.save(barn2);

    //when
    List<BarnResponse> response = barnService.findAll();

    //then
    List<String> codeIdList = response.stream().map(BarnResponse::getCodeId).toList();
    assertThat(codeIdList).isEqualTo(List.of(barn1.getCodeId(),barn2.getCodeId()));
  }

  @DisplayName("축사를 단일 조회한다.")
  @Test
  void findByCodeId() {
    //given
    Barn barn = Barn.createBarn(PARSER_BARN_CODE_ID_1);
    barnRepository.save(barn);

    //when
    BarnResponse response = barnService.findByCodeId(PARSER_BARN_CODE_ID_1);

    //then
    assertThat(barn.getId()).isEqualTo(response.getId());
  }

  @DisplayName("없는 축사를 단일 조회할 시 예외를 발생시킨다.")
  @Test
  void findByCodeIdWithNoData() {
    //given //when //then
    assertThatThrownBy(()->barnService.findByCodeId(PARSER_BARN_CODE_ID_1)).isInstanceOf(NotExistException.class)
        .hasMessage(COMMON_NOT_EXIST.formatMessage(PARSER_BARN_CODE_ID_1));
  }
}