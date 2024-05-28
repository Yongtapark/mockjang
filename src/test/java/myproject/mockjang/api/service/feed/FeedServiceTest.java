package myproject.mockjang.api.service.feed;

import static myproject.mockjang.domain.feed.FeedUsageStatus.USING;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedRepository;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.feedcomsumption.FeedConsumptionRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
class FeedServiceTest extends IntegrationTestSupport {

  @Autowired
  CowRepository cowRepository;
  @Autowired
  FeedConsumptionRepository feedConsumptionRepository;
  @Autowired
  FeedRepository feedRepository;
  @Autowired
  FeedService feedService;

  @DisplayName("소들의 일일 먹이 소비량을 구한다.")
  @Test
  void calculateMultiDailyConsumption() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);
    LocalDate eatDate1 = LocalDate.of(2024, 4, 23);

    Feed hay = Feed.builder().name("건초").description("마른건초").storeDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).build();
    Feed corn = Feed.builder().name("옥수수").description("옥수수사료").storeDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).build();
    Feed feed = Feed.builder().name("사료").description("고기사료").storeDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).build();

    feedRepository.save(hay);
    feedRepository.save(corn);
    feedRepository.save(feed);

    Cow cow1 = Cow.builder().codeId("1111").build();
    Cow cow2 = Cow.builder().codeId("1112").build();

    cowRepository.save(cow1);
    cowRepository.save(cow2);

    FeedConsumption hayConsumption1 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow1).date(eatDate1).build();
    FeedConsumption cornConsumption1 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow1).date(eatDate1).build();
    FeedConsumption feedConsumption1 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(0.0).cow(cow1).date(eatDate1).build();

    FeedConsumption hayConsumption2 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow2).date(eatDate1).build();
    FeedConsumption cornConsumption2 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow2).date(eatDate1).build();
    FeedConsumption feedConsumption2 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(0.0).cow(cow2).date(eatDate1).build();

    hayConsumption1.registerDailyCowConsumption();
    cornConsumption1.registerDailyCowConsumption();
    feedConsumption1.registerDailyCowConsumption();

    hayConsumption2.registerDailyCowConsumption();
    cornConsumption2.registerDailyCowConsumption();
    feedConsumption2.registerDailyCowConsumption();

    feedConsumptionRepository.save(hayConsumption1);
    feedConsumptionRepository.save(hayConsumption2);
    feedConsumptionRepository.save(cornConsumption1);
    feedConsumptionRepository.save(cornConsumption2);
    feedConsumptionRepository.save(feedConsumption1);
    feedConsumptionRepository.save(feedConsumption2);

    //when
    feedService.calculateMultiDailyConsumption(eatDate1, List.of(hay,corn,feed));

    //then
    Feed findHay = feedRepository.findById(hay.getId()).orElseThrow();
    Feed findCorn = feedRepository.findById(corn.getId()).orElseThrow();
    Feed findFeed = feedRepository.findById(feed.getId()).orElseThrow();

    assertThat(findHay.getDailyConsumption()).isEqualTo(
        hayConsumption1.getDailyConsumptionAmount() + hayConsumption2.getDailyConsumptionAmount());
    assertThat(findCorn.getDailyConsumption()).isEqualTo(cornConsumption1.getDailyConsumptionAmount() + cornConsumption2.getDailyConsumptionAmount());
    assertThat(findFeed.getDailyConsumption()).isEqualTo(feedConsumption1.getDailyConsumptionAmount() + feedConsumption2.getDailyConsumptionAmount());
  }

  @DisplayName("일일 먹이 소비량으로 예상 소비날짜를 구한다.")
  @Test
  void calculateExpectedDepletionDate() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);
    LocalDate eatDate = LocalDate.of(2024, 4, 23);
    LocalDate expectedDepletionDate = LocalDate.of(2024, 4, 23).plusDays(6);

    Feed hay = Feed.builder().name("건초").description("마른건초").storeDate(purchaseDate)
            .expirationDate(expireDate).usageStatus(USING).amount(70000.0).dailyConsumption(10000.0).build();
    feedRepository.save(hay);

    //when
    feedService.calculateLeftStockDay(hay,eatDate);

    //then
    Feed findHay = feedRepository.findById(hay.getId()).orElseThrow();
    assertThat(findHay.getExpectedDepletionDate()).isEqualTo(expectedDepletionDate);
  }

}