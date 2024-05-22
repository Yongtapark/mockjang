package myproject.mockjang.domain.cow;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Transactional
class CowRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private CowRepository cowRepository;

  @DisplayName("cowId로 소를 검색")
  @Test
  void findByCowId() {
    //given
    Cow cow = createCow("0001", Gender.FEMALE);
    //when
    Cow savedCow = cowRepository.save(cow);
    Cow findCow = cowRepository.findByCowId(cow.getCowId());

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
    cowRepository.save(mom);
    cowRepository.save(dad);
    child.registerParent(mom);
    child.registerParent(dad);
    cowRepository.save(child);

    //when
    Cow findCow = cowRepository.findByCowId(child.getCowId());

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
    Cow findMomCow = cowRepository.findByCowId(mom.getCowId());

    //then
    assertThat(findMomCow).isEqualTo(mom);
    assertThat(findMomCow.getChildren()).contains(savedChild1, savedChild2, savedChild3);
  }

  private static Cow createCow(String cowId, Gender gender) {
    return Cow.builder().cowId(cowId).gender(gender).build();
  }

}