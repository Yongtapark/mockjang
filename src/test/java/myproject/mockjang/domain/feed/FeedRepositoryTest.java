package myproject.mockjang.domain.feed;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import myproject.mockjang.IntegrationTestSupport;
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
        .storeDate(purchaseDate)
        .expirationDate(expireDate)
        .build();

    //when
    Feed savedFeed = feedRepository.save(feed);

    //then
    assertThat(savedFeed).isEqualTo(feed);
  }

  @DisplayName("건초가 논리적 제거가 되는지 확인한다.")
  @Test
  void checkFeedCanDeleteLogical() {
    //given
    Feed hay = Feed.builder().codeId("건초").build();
    Feed feed = Feed.builder().codeId("사료").build();
    feedRepository.save(hay);
    feedRepository.save(feed);
    feedRepository.delete(feed);
    List<Feed> feeds = feedRepository.findAll();

    //when
    List<Feed> deletedFeeds = feedRepository.findAllWhereDeletedTrue();

    //then
    assertThat(feeds).containsOnly(hay);
    assertThat(deletedFeeds).containsOnly(feed);
  }
}