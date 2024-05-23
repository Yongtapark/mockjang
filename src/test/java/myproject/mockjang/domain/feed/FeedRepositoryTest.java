package myproject.mockjang.domain.feed;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import myproject.mockjang.IntegrationTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class FeedRepositoryTest extends IntegrationTestSupport {

  @Autowired
  private FeedRepository feedRepository;

  @DisplayName("건초 저장 테스트")
  @Test
  void saveTest() {
    //given
    LocalDate purchaseDate = LocalDate.of(2024, 1, 1);
    LocalDate expireDate = LocalDate.of(2024, 5, 1);
    Feed feed = Feed.builder()
        .codeId("xxxx")
        .name("건초")
        .purchaseDate(purchaseDate)
        .expirationDate(expireDate)
        .build();

    //when
    Feed savedFeed = feedRepository.save(feed);

    //then
    assertThat(savedFeed).isEqualTo(feed);
  }
}