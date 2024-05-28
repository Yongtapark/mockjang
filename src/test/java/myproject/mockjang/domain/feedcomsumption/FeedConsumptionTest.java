package myproject.mockjang.domain.feedcomsumption;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
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

    Cow cow = Cow.builder().codeId("1111").build();

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

}