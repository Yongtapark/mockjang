package myproject.mockjang.domain.mockjang.cow;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CowRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private BarnRepository barnRepository;
  @Autowired
  private PenRepository penRepository;
  @Autowired
  private CowRepository cowRepository;

  @DisplayName("cowId로 소를 검색")
  @Test
  void findByCodeId() {
    //given
    Cow cow = createCow("0001", Gender.FEMALE);
    //when
    Cow savedCow = cowRepository.save(cow);
    Cow findCow = cowRepository.findByCodeId(cow.getCodeId());

    //then
    assertThat(savedCow).isEqualTo(findCow);
  }

  @DisplayName("자식 소가 부모 소들 과의 관계를 지정 할 수 있다.")
  @Test
  void childCowMakesRelationWithParents() {
    //given
    Cow mom = createCow("0001", Gender.FEMALE);
    Cow dad = createCow("0002", Gender.MALE);
    Cow child = createCow("0003", Gender.MALE);
    child.registerParent(mom);
    child.registerParent(dad);
    cowRepository.save(mom);
    cowRepository.save(dad);
    cowRepository.save(child);
    cowRepository.flush();

    //when
    Cow findCow = cowRepository.findByCodeId(child.getCodeId());

    //then
    assertThat(findCow).isEqualTo(child);
    assertThat(findCow.getMom()).isEqualTo(mom);
    assertThat(findCow.getDad()).isEqualTo(dad);

    assertThat(findCow.getMom().getChildren()).contains(findCow);
    assertThat(findCow.getDad().getChildren()).contains(findCow);


  }

  @DisplayName("어미 혹은 아비 소가 자식 소들을 등록 할 수 있다.")
  @Test
  void momCanMakesRelationWithChildren() {
    //given
    Cow mom = createCow("0001", Gender.FEMALE);
    Cow child1 = createCow("0003", Gender.MALE);
    Cow child2 = createCow("0004", Gender.MALE);
    Cow child3 = createCow("0005", Gender.MALE);

    Cow savedChild1 = cowRepository.save(child1);
    Cow savedChild2 = cowRepository.save(child2);
    Cow savedChild3 = cowRepository.save(child3);

    mom.registerAllChildren(List.of(savedChild1, savedChild2, savedChild3));

    cowRepository.save(mom);
    //when
    Cow findMomCow = cowRepository.findByCodeId(mom.getCodeId());

    //then
    assertThat(findMomCow).isEqualTo(mom);
    assertThat(findMomCow.getChildren()).contains(savedChild1, savedChild2, savedChild3);
  }

  @DisplayName("도축,비도축 상태로 소들을 조회")
  @Test
  void findAllByCowStatus() {
    //given
    Cow cow1 = createCow("0001", Gender.FEMALE, CowStatus.SLAUGHTERED);
    Cow cow2 = createCow("0002", Gender.MALE, CowStatus.RAISING);
    Cow cow3 = createCow("0003", Gender.FEMALE, CowStatus.RAISING);
    Cow cow4 = createCow("0004", Gender.MALE, CowStatus.RAISING);
    cowRepository.save(cow1);
    cowRepository.save(cow2);
    cowRepository.save(cow3);
    cowRepository.save(cow4);

    //when
    List<Cow> raisingCows = cowRepository.findAllByCowStatus(
        CowStatus.RAISING);

    //then
    assertThat(raisingCows).hasSize(3);
    assertThat(raisingCows).contains(cow2, cow3, cow4);
  }

  @DisplayName("축사칸을 변경 할 수 있다.")
  @Test
  void changePen() {
    //given
    Barn barn1 = Barn.createBarn("1번축사");
    Barn barn2 = Barn.createBarn("2번축사");

    barnRepository.save(barn1);
    barnRepository.save(barn2);

    Pen pen1 = Pen.createPen("1-1");
    Pen pen2 = Pen.createPen("1-2");
    pen1.registerUpperGroup(barn1);
    pen2.registerUpperGroup(barn2);
    penRepository.save(pen1);
    penRepository.save(pen2);

    Cow cow = createCow("0001", Gender.FEMALE);
    cow.registerUpperGroup(pen1);
    cowRepository.save(cow);

    //when
    cow.changePen(pen2);

    List<Cow> cows1 = penRepository.findById(pen1.getId()).orElseThrow().getCows();
    List<Cow> cows2 = penRepository.findById(pen2.getId()).orElseThrow().getCows();

    //then
    assertThat(cow.getBarn()).isEqualTo(barn2);
    assertThat(cow.getPen()).isEqualTo(pen2);
    assertThat(cows1).isEmpty();
    assertThat(cows2).containsOnly(cow);
  }

  @DisplayName("소가 논리적 제거가 되는지 확인한다.")
  @Test
  void checkCowCanDeleteLogical() {
    //given
    Cow cow1 = createCow("0001", Gender.FEMALE);
    Cow cow2 = createCow("0002", Gender.FEMALE);
    cowRepository.save(cow1);
    cowRepository.save(cow2);

    //when
    cowRepository.delete(cow2);

    //then
    List<Cow> deletedCow = cowRepository.findAllWhereDeletedTrue();
    assertThat(deletedCow).hasSize(1);
    assertThat(deletedCow).containsOnly(cow2);
  }
}