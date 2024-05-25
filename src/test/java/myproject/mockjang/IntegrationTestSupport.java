package myproject.mockjang;

import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public abstract class IntegrationTestSupport {

  //ERROR
  protected static final String DOMAIN_ONLY_SLAUGHTERED_ERROR = "error.domain.cowStatus.onlySlaughtered";
  protected static final String DOMAIN_NEGATIVE_NUMBER_ERROR = "error.domain.negativeNumber";

  protected static final String BUSINESS_ONLY_SLAUGHTERED_ERROR = "error.business.cowStatus.onlySlaughtered";

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
