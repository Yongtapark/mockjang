package myproject.mockjang.api.service.feed;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedRepository;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.feedcomsumption.FeedConsumptionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

  private final FeedConsumptionRepository feedConsumptionRepository;

  public void calculateMultiDailyConsumption(LocalDate eatDate,List<Feed> feeds) {
    List<FeedConsumption> dailyConsumptions = feedConsumptionRepository.findAllByDate(eatDate);
    for (Feed feed : feeds) {
      calculateOneFeedConsumption(feed, dailyConsumptions);
    }
  }

  private void calculateOneFeedConsumption(Feed feed, List<FeedConsumption> dailyConsumptions) {
    feed.resetDailyConsumption();
    for (FeedConsumption dailyConsumption : dailyConsumptions) {
      if (dailyConsumption.getFeed().equals(feed)) {
        feed.addDailyConsumptionAmount(dailyConsumption.getDailyConsumptionAmount());
      }
    }
  }
}
