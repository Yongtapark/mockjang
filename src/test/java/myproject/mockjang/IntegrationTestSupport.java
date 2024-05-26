package myproject.mockjang;

import jakarta.transaction.Transactional;
import myproject.mockjang.domain.mockjang.Mockjang;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.exception.Exceptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public abstract class IntegrationTestSupport {

  //common
  protected final String STRING_ONLY_SPACE = " ";
  protected final String STRING_EMPTY = "";
  protected final String STRING_OVER_10 = "12345678910";
  //cow
  protected static final int UNIT_PRICE_100_000_000 = 100_000_000;
  //feed
  protected static final double INITIALIZE_DAILY_CONSUMPTION_TO_ZERO = 0.0;
  protected static final double NEGATIVE_NUMBER = -5.0;

  public Cow createCow(String number) {
    return Cow.builder().cowId(number).build();
  }

  protected static Cow createCow(String cowId, Gender gender) {
    return Cow.builder().cowId(cowId).gender(gender).build();
  }

  protected static Cow createCow(String cowId, Gender gender, CowStatus cowStatus) {
    return Cow.builder().cowId(cowId).gender(gender).cowStatus(cowStatus).build();
  }
}
