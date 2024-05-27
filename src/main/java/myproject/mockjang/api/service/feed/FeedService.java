package myproject.mockjang.api.service.feed;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedRepository;
import myproject.mockjang.domain.feed.FeedUsageStatus;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.feedcomsumption.FeedConsumptionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;
  private final FeedConsumptionRepository feedConsumptionRepository;

  public List<Feed> findUsingFeeds() {
    return feedRepository.findAllByUsageStatus(FeedUsageStatus.USING);
  }

  public List<Feed> findAll() {
    return feedRepository.findAll();
  }

  public void calculateMultiDailyConsumption(LocalDate eatDate,List<Feed> feeds) {
    List<FeedConsumption> dailyConsumptions = feedConsumptionRepository.findAllByDate(eatDate);
    for (Feed feed : feeds) {
      feed.resetDailyConsumption();
      for (FeedConsumption dailyConsumption : dailyConsumptions) {
        calculateOneFeedConsumption(feed, dailyConsumption);
      }
    }
  }

  private void calculateOneFeedConsumption(Feed feed, FeedConsumption dailyConsumption) {
    if (dailyConsumption.getFeed().equals(feed)) {
      feed.addDailyConsumptionAmount(dailyConsumption.getDailyConsumptionAmount());
    }
  }

}
