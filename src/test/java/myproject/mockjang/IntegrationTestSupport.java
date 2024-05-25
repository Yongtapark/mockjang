package myproject.mockjang;

import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {
  //COW

  //FEED
  protected static final double INITIALIZE_DAILY_CONSUMPTION_TO_ZERO = 0.0;
  protected static final double NEGATIVE_NUMBER = -5.0;

  protected static Cow createCow(String cowId, Gender gender) {
    return Cow.builder().cowId(cowId).gender(gender).build();
  }

  protected static Cow createCow(String cowId, Gender gender, CowStatus cowStatus) {
    return Cow.builder().cowId(cowId).gender(gender).cowStatus(cowStatus).build();
  }
}
