package myproject.mockjang.domain.feed;

import static myproject.mockjang.domain.feed.FeedUsageStatus.USING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import myproject.mockjang.IntegrationTestSupport;
import myproject.mockjang.exception.NegativeNumberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeedTest extends IntegrationTestSupport {

  @DisplayName("일일소비량에 원하는 수량을 더한다.")
  @Test
  void addDailyConsumptionAmount() {
    final double ADD_FEED_AMOUNT = 5.0;
    final int TOTAL_FEED_AMOUNT = 10;
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);

    Feed hay = Feed.builder().name("건초").description("마른건초").purchaseDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).dailyConsumption(0.0).build();

    //when
    hay.addDailyConsumptionAmount(ADD_FEED_AMOUNT);
    hay.addDailyConsumptionAmount(ADD_FEED_AMOUNT);

    //then
    assertThat(hay.getDailyConsumption()).isEqualTo(TOTAL_FEED_AMOUNT);
  }

  @DisplayName("일일소비량에 음수를 더하면 예외를 발생시킨다.")
  @Test
  void addDailyConsumptionAmountWithMinus() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);

    Feed hay = Feed.builder().name("건초").description("마른건초").purchaseDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING)
        .dailyConsumption(INITIALIZE_DAILY_CONSUMPTION_TO_ZERO).build();

    //when //then
    assertThatThrownBy(() -> hay.addDailyConsumptionAmount(NEGATIVE_NUMBER)).isInstanceOf(
        NegativeNumberException.class).hasMessage(DOMAIN_NEGATIVE_NUMBER_ERROR);
  }

  @DisplayName("건초의 일일소비량을 0으로 초기화한다.")
  @Test
  void resetDailyConsumption() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);

    Feed hay = Feed.builder().name("건초").description("마른건초").purchaseDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).build();
    Feed corn = Feed.builder().name("옥수수").description("옥수수사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).build();
    Feed feed = Feed.builder().name("사료").description("고기사료").purchaseDate(purchaseDate)
        .expirationDate(expireDate).usageStatus(USING).build();

    //when
    hay.resetDailyConsumption();
    corn.resetDailyConsumption();
    feed.resetDailyConsumption();

    //then
    assertThat(hay.getDailyConsumption()).isEqualTo(0);
    assertThat(corn.getDailyConsumption()).isEqualTo(0);
    assertThat(feed.getDailyConsumption()).isEqualTo(0);
  }

}