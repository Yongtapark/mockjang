package myproject.mockjang.domain.feedcomsumption;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.mockjang.cow.Cow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeedConsumptionTest extends IntegrationTestSupport {

  @DisplayName("feedConsumption 을 등록하면 cow의 feedConsumptions에 등록된다.")
  @Test
  void registerDailyCowConsumption() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);
    LocalDate eatDate = LocalDate.of(2024, 4, 23);

    Feed hay = Feed.builder().name("건초").description("마른건초").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();
    Feed corn = Feed.builder().name("옥수수").description("옥수수사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();
    Feed feed = Feed.builder().name("사료").description("고기사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();

    Cow cow = Cow.builder().cowId("1111").build();

    FeedConsumption consumption1 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow).date(eatDate).build();
    FeedConsumption consumption2 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow).date(eatDate).build();
    FeedConsumption consumption3 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(7000.0).cow(cow).date(eatDate).build();

    //when
    consumption1.registerDailyCowConsumption();
    consumption2.registerDailyCowConsumption();
    consumption3.registerDailyCowConsumption();
    List<FeedConsumption> feedConsumptions = cow.getFeedConsumptions();

    //then
    assertThat(feedConsumptions).hasSize(3);
    assertThat(feedConsumptions).contains(consumption1, consumption2, consumption3);
  }

  //TODO: 서비스에서 구현 목표
  /*@DisplayName("전체 소의 전체 먹이 소비량을 가져와서 먹이당 일일 소비량을 상태에 저장한다.")
  @Test
  void test2() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);
    LocalDate eatDate = LocalDate.of(2024, 4, 23);

    Feed hay = Feed.builder().name("건초").description("마른건초").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();
    Feed corn = Feed.builder().name("옥수수").description("옥수수사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();
    Feed feed = Feed.builder().name("사료").description("고기사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();

    Cow cow1 = Cow.builder().cowId("1111").build();
    Cow cow2 = Cow.builder().cowId("1112").build();

    FeedConsumption hayConsumption1 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow1).date(eatDate).build();
    FeedConsumption cornConsumption1 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow1).date(eatDate).build();
    FeedConsumption feedConsumption1 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(7000.0).cow(cow1).date(eatDate).build();

    FeedConsumption hayConsumption2 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow2).date(eatDate).build();
    FeedConsumption cornConsumption2 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow2).date(eatDate).build();
    FeedConsumption feedConsumption2 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(7000.0).cow(cow2).date(eatDate).build();

    hayConsumption1.registerDailyCowConsumption();
    cornConsumption1.registerDailyCowConsumption();
    feedConsumption1.registerDailyCowConsumption();

    hayConsumption2.registerDailyCowConsumption();
    cornConsumption2.registerDailyCowConsumption();
    feedConsumption2.registerDailyCowConsumption();
    //when
    HashMap<String, Double> dailyConsumption1 = cow1.calculateDailyConsumption();
    HashMap<String, Double> dailyConsumption2 = cow2.calculateDailyConsumption();

    //얘네들 전부 더해야하는건 아는데..
    //애초에 그럼 전부 받아와야하는가
    Double hayConsumption = hay.calculateDailyConsumption();
    Double cornConsumption = corn.calculateDailyConsumption();
    Double feedConsumption = feed.calculateDailyConsumption();

    //then
    assertThat(hayConsumption).isEqualTo(hayConsumption1.getDailyConsumptionAmount() + hayConsumption2.getDailyConsumptionAmount());
    assertThat(cornConsumption).isEqualTo(cornConsumption1.getDailyConsumptionAmount() + cornConsumption2.getDailyConsumptionAmount());
    assertThat(feedConsumption).isEqualTo(feedConsumption1.getDailyConsumptionAmount() + feedConsumption2.getDailyConsumptionAmount());
  }*/


}