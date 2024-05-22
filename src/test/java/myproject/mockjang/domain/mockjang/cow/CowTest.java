package myproject.mockjang.domain.mockjang.cow;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.records.CowRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CowTest extends IntegrationTestSupport {

  @DisplayName("부모가 자식을 등록 할 수 있다.")
  @Test
  void registerAllChildren() {
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

  @DisplayName("부모가 자식을 등록 할 수 있다.")
  @Test
  void registerAllChildrenF() {
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

  @DisplayName("소는 ")
  @Test
  void test() {
      //given

      //when

      //then
  }
}