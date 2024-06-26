package myproject.mockjang.domain.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.COMMON_ALREADY_EXIST;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UNDER_GROUP;
import static myproject.mockjang.exception.Exceptions.COMMON_NO_UPPER_GROUP;
import static myproject.mockjang.exception.Exceptions.DOMAIN_ONLY_SLAUGHTERED_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.common.ThereIsNoGroupException;
import myproject.mockjang.exception.common.UpperGroupAlreadyExistException;
import myproject.mockjang.exception.cow.CowParentsException;
import myproject.mockjang.exception.cow.CowStatusException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

class CowTest extends IntegrationTestSupport {

    @Autowired
    MessageSource messageSource;

    @DisplayName("소를 등록하면 축사칸에 등록할 수 있다.")
    @Test
    void registerUpperGroup() {
        //given
        Pen pen = Pen.createPen("1번축사");
        Cow mom = createCow("0001", Gender.FEMALE);
        Cow child1 = createCow("0003", Gender.MALE);
        Cow child2 = createCow("0004", Gender.MALE);
        Cow child3 = createCow("0005", Gender.MALE);

        //when
        mom.registerUpperGroup(pen);
        child1.registerUpperGroup(pen);
        child2.registerUpperGroup(pen);
        child3.registerUpperGroup(pen);
        List<Cow> cows = pen.getCows();

        //then
        assertThat(cows).contains(mom, child1, child2, child3);
        assertThat(cows).hasSize(4);
    }

    @DisplayName("축사칸을 변경 할 수 있다.")
    @Test
    void changeUpperGroup() {
        //given
        Barn barn1 = Barn.createBarn("1번축사");
        Barn barn2 = Barn.createBarn("2번축사");

        Pen pen1 = Pen.createPen("1-1");
        Pen pen2 = Pen.createPen("1-2");
        pen1.registerUpperGroup(barn1);
        pen2.registerUpperGroup(barn2);
        Cow cow = createCow("0001", Gender.FEMALE);
        cow.registerUpperGroup(pen1);

        //when
        cow.changeUpperGroup(pen2);
        List<Cow> cows1 = pen1.getCows();
        List<Cow> cows2 = pen2.getCows();

        //then
        assertThat(cow.getBarn()).isEqualTo(barn2);
        assertThat(cow.getPen()).isEqualTo(pen2);
        assertThat(cows1).isEmpty();
        assertThat(cows2).containsOnly(cow);
    }

    @DisplayName("암컷 부모가 자식을 등록 할 수 있다.")
    @Test
    void registerAllChildrenWithMom() {
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

    @DisplayName("수컷 부모가 자식을 등록 할 수 있다.")
    @Test
    void registerAllChildrenWithDad() {
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

    @DisplayName("자식 소에서 부모 소의 관계를 제거한다.")
    @Test
    void removeParent() {
        //given
        Cow mom = createCow("0001", Gender.FEMALE);
        Cow dad = createCow("0002", Gender.MALE);
        Cow child1 = createCow("0003", Gender.MALE);

        child1.registerParent(mom);
        child1.registerParent(dad);

        //when
        child1.removeParent(mom);
        child1.removeParent(dad);

        //then
        assertThat(child1.getDad()).isNull();
        assertThat(child1.getMom()).isNull();
        assertThat(mom.getChildren()).doesNotContain(child1);
        assertThat(dad.getChildren()).doesNotContain(child1);
    }

    @DisplayName("자식 소에서 부모 소의 관계를 제거할 때, 어미의 정보가 다르면 예외를 발생 시킨다.")
    @Test
    void removeParentWithFakeMom() {
        //given
        Cow mom = createCow("0001", Gender.FEMALE);
        Cow fakeMom = createCow("0005", Gender.FEMALE);
        Cow dad = createCow("0002", Gender.MALE);
        Cow child1 = createCow("0003", Gender.MALE);

        child1.registerParent(mom);
        child1.registerParent(dad);

        //when //then
        assertThatThrownBy(() -> child1.removeParent(fakeMom)).isInstanceOf(CowParentsException.class).hasMessage(
                Exceptions.DOMAIN_PARENTS_ERROR.formatMessage(fakeMom.getCodeId(), mom.getCodeId()));
    }

    @DisplayName("자식 소에서 부모 소의 관계를 제거할 때, 아비의 정보가 다르면 예외를 발생 시킨다.")
    @Test
    void removeParentWithFakeDad() {
        //given
        Cow mom = createCow("0001", Gender.FEMALE);
        Cow dad = createCow("0002", Gender.MALE);
        Cow fakeDad = createCow("0005", Gender.FEMALE);
        Cow child1 = createCow("0003", Gender.MALE);

        child1.registerParent(mom);
        child1.registerParent(dad);

        //when //then
        assertThatThrownBy(() -> child1.removeParent(fakeDad)).isInstanceOf(CowParentsException.class).hasMessage(
                Exceptions.DOMAIN_PARENTS_ERROR.formatMessage(fakeDad.getCodeId(), mom.getCodeId()));
    }

    @DisplayName("자식 소에서 부모 소의 관계를 제거할 때, 제거 하려는 부모 소를 null로 입력 하면 예외를 발생시킨다.")
    @Test
    void removeParentWithNull() {
        //given
        Cow mom = createCow("0001", Gender.FEMALE);
        Cow dad = createCow("0002", Gender.MALE);
        Cow child1 = createCow("0003", Gender.MALE);

        child1.registerParent(mom);
        child1.registerParent(dad);

        //when //then
        assertThatThrownBy(() -> child1.removeParent(null)).isInstanceOf(NullPointerException.class)
                .hasMessage("parent is null");
    }

    @DisplayName("소의 도축상태를 변경한다.")
    @Test
    void changeCowStatus() {
        //given
        Cow cow = createCow("0001", Gender.MALE);

        //when
        cow.changeCowStatus(CowStatus.SLAUGHTERED);

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
        Cow cow = createCow("0001", Gender.MALE, CowStatus.RAISING);

        //when //then
        assertThatThrownBy(() -> cow.registerUnitPrice(UNIT_PRICE_100_000_000)).isInstanceOf(
                CowStatusException.class).hasMessage(DOMAIN_ONLY_SLAUGHTERED_ERROR.getMessage());
    }

    @DisplayName("cowStatus가 null인 소는 단가를 입력하면 예외를 발생시킨다.")
    @Test
    void registerUnitPriceWithNull() {
        //given
        Cow cow = createCow("0001", Gender.MALE);

        //when //then
        assertThatThrownBy(() -> cow.registerUnitPrice(UNIT_PRICE_100_000_000)).isInstanceOf(
                CowStatusException.class).hasMessage(DOMAIN_ONLY_SLAUGHTERED_ERROR.getMessage());
    }

    @DisplayName("이미 축사가 등록된 소에게 축사를 등록하면 예외를 발생시킨다.")
    @Test
    void whenMultiRegisterBarn() {
        //given
        Barn barn1 = Barn.createBarn("1번축사");
        Barn barn2 = Barn.createBarn("2번축사");
        Cow cow = createCow("0001", Gender.MALE);
        cow.registerBarn(barn1);

        //when //then
        assertThatThrownBy(() -> cow.registerBarn(barn2)).isInstanceOf(
                        UpperGroupAlreadyExistException.class)
                .hasMessage(COMMON_ALREADY_EXIST.formatMessage(Barn.class));
    }

    @DisplayName("호출 시점에 상위 그룹이 존재하지 않을 시 예외를 발생시킨다.")
    @Test
    void getUpperGroupWithNoUpperGroup() {
        //given
        Cow cow = createCow("0001", Gender.MALE);

        //when //then
        assertThatThrownBy(cow::getUpperGroup).isInstanceOf(ThereIsNoGroupException.class)
                .hasMessage(COMMON_NO_UPPER_GROUP.formatMessage(Cow.class));
    }

    @DisplayName("호출 시점에 하위 그룹 리스트가 비어있다면 예외를 발생시킨다.")
    @Test
    void removeOneOfUnderGroupsWithNoUnderGroup() {
        //given
        Cow cow = createCow("0001", Gender.MALE);

        //when //then
        assertThatThrownBy(() -> cow.removeOneOfUnderGroups(null)).isInstanceOf(
                ThereIsNoGroupException.class).hasMessage(COMMON_NO_UNDER_GROUP.formatMessage(Cow.class));
    }
}