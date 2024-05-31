package myproject.mockjang;

import jakarta.transaction.Transactional;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport extends TestConstants {


  public Cow createCow(String number) {
    return Cow.builder().codeId(number).build();
  }

  protected static Cow createCow(String cowCode, Gender gender) {
    return Cow.builder().codeId(cowCode).gender(gender).build();
  }

  protected static Cow createCow(String cowCode, Gender gender, CowStatus cowStatus) {
    return Cow.builder().codeId(cowCode).gender(gender).cowStatus(cowStatus).build();
  }
}
