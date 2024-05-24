package myproject.mockjang.domain.feedcomsumption;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedRepository;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class FeedConsumptionRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private FeedConsumptionRepository feedConsumptionRepository;

  @Autowired
  private FeedRepository feedRepository;

  @Autowired
  private CowRepository cowRepository;

  @DisplayName("날짜로 먹이섭취기록을 조회한다.")
  @Test
  void findAllByDate() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);
    LocalDate eatDate1 = LocalDate.of(2024, 4, 23);
    LocalDate eatDate2 = LocalDate.of(2024, 5, 23);

    Feed hay = Feed.builder().name("건초").description("마른건초").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();
    Feed corn = Feed.builder().name("옥수수").description("옥수수사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();
    Feed feed = Feed.builder().name("사료").description("고기사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).build();

    feedRepository.save(hay);
    feedRepository.save(corn);
    feedRepository.save(feed);

    Cow cow1 = Cow.builder().cowId("1111").build();
    Cow cow2 = Cow.builder().cowId("1112").build();

    cowRepository.save(cow1);
    cowRepository.save(cow2);

    FeedConsumption hayConsumption1 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow1).date(eatDate1).build();
    FeedConsumption cornConsumption1 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow1).date(eatDate1).build();
    FeedConsumption feedConsumption1 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(7000.0).cow(cow1).date(eatDate1).build();

    FeedConsumption hayConsumption2 = FeedConsumption.builder().feed(hay)
        .dailyConsumptionAmount(5000.0).cow(cow2).date(eatDate2).build();
    FeedConsumption cornConsumption2 = FeedConsumption.builder().feed(corn)
        .dailyConsumptionAmount(6000.0).cow(cow2).date(eatDate2).build();
    FeedConsumption feedConsumption2 = FeedConsumption.builder().feed(feed)
        .dailyConsumptionAmount(7000.0).cow(cow2).date(eatDate2).build();

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
    List<FeedConsumption> allByDate = feedConsumptionRepository.findAllByDate(eatDate1);

    //then
    assertThat(allByDate).hasSize(3);
    assertThat(allByDate).contains(hayConsumption1, cornConsumption1, feedConsumption1);
  }
}