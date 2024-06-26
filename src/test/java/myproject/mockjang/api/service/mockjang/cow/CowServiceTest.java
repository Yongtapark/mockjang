package myproject.mockjang.api.service.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;
import static myproject.mockjang.exception.Exceptions.COMMON_BLANK_STRING;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_STRING_OVER_10;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.api.service.mockjang.cow.request.CowCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterParentsServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterUnitPriceServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowRemoveParentsServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowUpdateCowStatusServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowUpdatePenServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.common.StringException;
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
        Barn barn = Barn.createBarn(BARN_CODE_ID_1);
        barnRepository.save(barn);
        Pen pen = Pen.createPen(PEN_CODE_ID_1);
        pen.registerUpperGroup(barn);
        penRepository.save(pen);
        LocalDateTime birthDate = LocalDateTime.of(2024, 5, 25, 0, 0);
        CowCreateServiceRequest request = CowCreateServiceRequest.builder()
                .cowCode(COW_CODE_ID_1)
                .gender(Gender.FEMALE)
                .penCode(pen.getCodeId())
                .birthDate(birthDate)
                .build();

        //when
        Cow findRasisingCow = cowRepository.findById(cowService.createRaisingCow(request)).orElseThrow();

        //then
        assertThat(findRasisingCow.getBarn()).isEqualTo(barn);
        assertThat(findRasisingCow.getPen()).isEqualTo(pen);

    }

    @DisplayName("소의 상태를 변경한다.")
    @Test
    void updateCowStatus() {
        //given
        Cow cow = createCow(COW_CODE_ID_1, Gender.FEMALE);
        cowRepository.save(cow);

        CowUpdateCowStatusServiceRequest request = CowUpdateCowStatusServiceRequest.builder()
                .cowId(cow.getId())
                .cowStatus(CowStatus.SLAUGHTERED)
                .build();

        //when
        cowService.updateCowStatus(request);

        //then
        assertThat(cow.getCowStatus()).isEqualTo(CowStatus.SLAUGHTERED);
    }

    @DisplayName("도축 상태인 소는 단가를 입력할 수 있다.")
    @Test
    void registerUnitPrice() {
        //given
        Cow cow = createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.SLAUGHTERED);
        cowRepository.save(cow);

        CowRegisterUnitPriceServiceRequest request = CowRegisterUnitPriceServiceRequest.builder()
                .cowId(cow.getId())
                .unitPrice(UNIT_PRICE_100_000_000)
                .build();

        //when
        cowService.registerUnitPrice(request);

        //then
        assertThat(cow.getUnitPrice()).isEqualTo(UNIT_PRICE_100_000_000);
    }

    @DisplayName("도축 상태인 소가 아니면 단가 입력시 예외를 발생시킨다.")
    @Test
    void registerUnitPriceWithNotSlaughtered() {
        //given
        Cow cow = createCow(COW_CODE_ID_1, Gender.FEMALE);
        cowRepository.save(cow);

        CowRegisterUnitPriceServiceRequest request = CowRegisterUnitPriceServiceRequest.builder()
                .cowId(cow.getId())
                .unitPrice(UNIT_PRICE_100_000_000)
                .build();

        //when //then
        assertThatThrownBy(
                () -> cowService.registerUnitPrice(request)).isInstanceOf(
                        CowStatusException.class)
                .hasMessage(BUSINESS_ONLY_SLAUGHTERED_ERROR.getMessage());
    }

    @DisplayName("축사칸을 변경 할 수 있다.")
    @Test
    void updatePen() {
        //given
        Barn barn1 = Barn.createBarn(BARN_CODE_ID_1);
        Barn barn2 = Barn.createBarn(PARSER_BARN_CODE_ID_2);

        barnRepository.save(barn1);
        barnRepository.save(barn2);

        Pen pen1 = Pen.createPen(PEN_CODE_ID_1);
        Pen pen2 = Pen.createPen(PEN_CODE_ID_2);
        pen1.registerUpperGroup(barn1);
        pen2.registerUpperGroup(barn2);
        penRepository.save(pen1);
        penRepository.save(pen2);

        Cow cow = createCow(COW_CODE_ID_1, Gender.FEMALE);
        cow.registerUpperGroup(pen1);
        cowRepository.save(cow);

        CowUpdatePenServiceRequest request = CowUpdatePenServiceRequest.builder()
                .cowId(cow.getId())
                .penId(pen2.getId()).build();

        //when
        cowService.updatePen(request);

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
    void remove() {
        //given
        Pen pen = Pen.createPen(BARN_CODE_ID_1);
        penRepository.save(pen);
        Cow cow = createCow(COW_CODE_ID_1);
        cow.registerUpperGroup(pen);
        cowRepository.save(cow);

        //when
        cowService.remove(cow.getId());

        //then
        assertThatThrownBy(() -> cowRepository.findById(cow.getId()).orElseThrow())
                .isInstanceOf(NoSuchElementException.class);
        assertThat(pen.getCows()).isEmpty();
    }

    @DisplayName("축사 이름에 빈 문자열이 들어올 경우 예외를 발생시킨다.")
    @Test
    void createCowWithEmptybarnCode() {
        //given
        CowCreateServiceRequest request = CowCreateServiceRequest.builder()
                .cowCode(STRING_EMPTY)
                .gender(Gender.FEMALE)
                .build();
        //when //then
        assertThatThrownBy(
                () -> cowService.createRaisingCow(request)).isInstanceOf(
                        StringException.class)
                .hasMessage(COMMON_BLANK_STRING.getMessage());
    }

    @DisplayName("축사 이름에 공백만 들어올 경우 예외를 발생시킨다.")
    @Test
    void createCowWithOnlySpacebarnCode() {
        //given
        CowCreateServiceRequest request = CowCreateServiceRequest.builder()
                .cowCode(STRING_ONLY_SPACE)
                .gender(Gender.FEMALE)
                .build();
        // when //then
        assertThatThrownBy(() -> cowService.createRaisingCow(request)).isInstanceOf(
                        StringException.class)
                .hasMessage(COMMON_BLANK_STRING.getMessage());
    }

    @DisplayName("축사 이름이 10글자를 넘어가면 예외를 발생시킨다.")
    @Test
    void createCowWithOver10Size() {
        //given
        CowCreateServiceRequest request = CowCreateServiceRequest.builder()
                .cowCode(STRING_OVER_10)
                .gender(Gender.FEMALE)
                .build();
        //when //then
        assertThatThrownBy(
                () -> cowService.createRaisingCow(request)).isInstanceOf(
                        StringException.class)
                .hasMessage(COMMON_STRING_OVER_10.getMessage());
    }

    @DisplayName("소의 부모를 등록하면, 소의 엄마, 아빠 필드에 부모가, 부모의 자식 필드에 자식들이 등록된다.")
    @Test
    void registerParents() {
        //given
        LocalDateTime birthDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, birthDate);
        Cow mom = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, birthDate);
        Cow dad = Cow.createCow("0003", Gender.MALE, CowStatus.RAISING, birthDate);
        cowRepository.save(cow);
        cowRepository.save(mom);
        cowRepository.save(dad);

        CowRegisterParentsServiceRequest request = CowRegisterParentsServiceRequest.builder()
                .cowId(cow.getId())
                .parentsIds(List.of(mom.getId(), dad.getId()))
                .build();

        //when
        cowService.registerParents(request);

        //then
        Cow savedCow = cowRepository.findById(cow.getId()).orElseThrow();
        Cow savedMom = cowRepository.findById(mom.getId()).orElseThrow();
        Cow savedDad = cowRepository.findById(dad.getId()).orElseThrow();
        assertThat(savedCow.getMom()).isEqualTo(mom);
        assertThat(savedCow.getDad()).isEqualTo(dad);
        assertThat(savedMom.getChildren()).containsOnly(cow);
        assertThat(savedDad.getChildren()).containsOnly(cow);
    }

    @DisplayName("소의 부모 자식 관계를 제거 한다.")
    @Test
    void removeParents() {
        //given
        LocalDateTime birthDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, birthDate);
        Cow mom = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, birthDate);
        Cow dad = Cow.createCow("0003", Gender.MALE, CowStatus.RAISING, birthDate);

        for (Cow parent : List.of(mom, dad)) {
            cow.registerParent(parent);
        }

        cowRepository.save(cow);
        cowRepository.save(mom);
        cowRepository.save(dad);

        //when
        CowRemoveParentsServiceRequest request = CowRemoveParentsServiceRequest.builder()
                .cowId(cow.getId())
                .parentsIds(List.of(mom.getId(), dad.getId()))
                .build();

        cowService.removeParents(request);

        //then
        Cow savedCow = cowRepository.findById(cow.getId()).orElseThrow();
        Cow savedMom = cowRepository.findById(mom.getId()).orElseThrow();
        Cow savedDad = cowRepository.findById(dad.getId()).orElseThrow();
        assertThat(savedCow.getMom()).isNull();
        assertThat(savedCow.getDad()).isNull();
        assertThat(savedMom.getChildren()).doesNotContain(cow);
        assertThat(savedDad.getChildren()).doesNotContain(cow);
    }

    @DisplayName("소 리스트 조회")
    @Test
    void findAll() {
        //given
        LocalDateTime birthDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        Cow cow1 = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, birthDate);
        Cow cow2 = Cow.createCow(COW_CODE_ID_2, Gender.FEMALE, CowStatus.RAISING, birthDate);
        cowRepository.save(cow1);
        cowRepository.save(cow2);

        //when
        List<CowResponse> cows = cowService.findAll();
        Stream<Long> ids = cows.stream().map(CowResponse::getId);

        //then
        assertThat(ids).containsExactly(cow1.getId(), cow2.getId());
    }

    @DisplayName("소를 단일 조회한다.")
    @Test
    void findByCodeId() {
        //given
        LocalDateTime birthDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        Cow cow = Cow.createCow(COW_CODE_ID_1, Gender.FEMALE, CowStatus.RAISING, birthDate);
        cowRepository.save(cow);

        //when
        CowResponse response = cowService.findByCodeId(COW_CODE_ID_1);

        //then
        assertThat(cow.getId()).isEqualTo(response.getId());
    }

    @DisplayName("없는 소를 단일 조회할 시 예외를 발생시킨다.")
    @Test
    void findByCodeIdWithNoData() {
        //given //when //then
        assertThatThrownBy(() -> cowService.findByCodeId(COW_CODE_ID_1)).isInstanceOf(
                        NotExistException.class)
                .hasMessage(COMMON_NOT_EXIST.formatMessage(COW_CODE_ID_1));
    }

}