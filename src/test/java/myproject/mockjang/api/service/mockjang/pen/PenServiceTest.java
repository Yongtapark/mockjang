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
import myproject.mockjang.api.service.mockjang.pen.request.PenCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.pen.request.PenUpdateServiceRequest;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
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
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);
        PenCreateServiceRequest request = PenCreateServiceRequest.builder()
                .penCodeId(PEN_CODE_ID_1).barnCodeId(BARN_CODE_ID_1).build();

        //when
        Long response = penService.createPen(request);

        //then
        Pen pen = penRepository.findById(response).orElseThrow();
        assertThat(response).isEqualTo(pen.getId());
        assertThat(PEN_CODE_ID_1).isEqualTo(pen.getCodeId());
        assertThat(BARN_CODE_ID_1).isEqualTo(pen.getBarn().getCodeId());
    }

    @DisplayName("해당 축사칸을 제거하고 축사의 축사칸 리스트에서 제거한다.")
    @Test
    void delete() {
        //given
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);

        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);

        //when
        penService.delete(pen.getId());

        //then
        assertThat(barn.getPens()).isEmpty();
        assertThatThrownBy(
                () -> penRepository.findByCodeId(pen.getCodeId()).orElseThrow()).isInstanceOf(
                NoSuchElementException.class);
    }

    @DisplayName("축사칸의 축사를 변경한다.")
    @Test
    void update() {
        //given
        Barn barn1 = Barn.createBarn(BARN_CODE_ID_1);
        Barn barn2 = Barn.createBarn(PARSER_BARN_CODE_ID_2);
        barnRepository.saveAll(List.of(barn1,barn2));
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn1);
        penRepository.save(pen);

        PenUpdateServiceRequest request = PenUpdateServiceRequest.builder()
                .penId(pen.getId())
                .barnId(barn2.getId())
                .codeId(null)
                .build();

        //when
        penService.update(request);

        //then
        assertThat(barn1.getPens()).isEmpty();
        assertThat(barn2.getPens()).hasSize(1);
        assertThat(barn2.getPens()).containsOnly(pen);
    }


    @DisplayName("축사 이름에 빈 문자열이 들어올 경우 예외를 발생시킨다.")
    @Test
    void createBarnWithEmptyBarnCode() {
        //given
        PenCreateServiceRequest request = PenCreateServiceRequest.builder()
                .penCodeId(STRING_EMPTY).build();
        //when //then
        assertThatThrownBy(() -> penService.createPen(request)).isInstanceOf(
                StringException.class).hasMessage(COMMON_BLANK_STRING.getMessage());
    }

    @DisplayName("축사 이름에 공백만 들어올 경우 예외를 발생시킨다.")
    @Test
    void createBarnWithOnlySpacebarnCode() {
        //given
        PenCreateServiceRequest request = PenCreateServiceRequest.builder()
                .penCodeId(STRING_ONLY_SPACE).build();
        //when //then
        assertThatThrownBy(() -> penService.createPen(request)).isInstanceOf(
                StringException.class).hasMessage(COMMON_BLANK_STRING.getMessage());
    }

    @DisplayName("축사 이름이 10글자를 넘어가면 예외를 발생시킨다.")
    @Test
    void createBarnWithOver10Size() {
        //given
        PenCreateServiceRequest request = PenCreateServiceRequest.builder()
                .penCodeId(STRING_OVER_10).build();
        // when //then
        assertThatThrownBy(() -> penService.createPen(request)).isInstanceOf(
                StringException.class).hasMessage(COMMON_STRING_OVER_10.getMessage());
    }

    @DisplayName("축사 칸 리스트를 조회한다.")
    @Test
    void findAll() {
        //given
        Pen pen1 = Pen.createPen(PEN_CODE_ID_1);
        Pen pen2 = Pen.createPen(PEN_CODE_ID_2);
        penRepository.save(pen1);
        penRepository.save(pen2);

        //when
        List<PenResponse> responses = penService.findAll();
        List<Long> penCodeList = responses.stream().map(PenResponse::getId).toList();
        //then
        assertThat(penCodeList).containsExactly(pen1.getId(), pen2.getId());
    }

    @DisplayName("축사칸을 단일 조회한다.")
    @Test
    void findByCodeId() {
        //given
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        penRepository.save(pen);

        //when
        PenResponse response = penService.findByCodeId(PEN_CODE_ID_1);

        //then
        assertThat(response.getId()).isEqualTo(pen.getId());
    }

    @DisplayName("없는 축사칸을 단일 조회할 시 예외를 발생시킨다.")
    @Test
    void findByCodeIdWithNoData() {
        //given //when //then
        assertThatThrownBy(() -> penService.findByCodeId(PEN_CODE_ID_1)).isInstanceOf(
                NotExistException.class).hasMessage(COMMON_NOT_EXIST.formatMessage(PEN_CODE_ID_1));
    }


}