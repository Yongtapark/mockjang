package myproject.mockjang.api.service.feed;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.feed.request.FeedCreateServiceRequest;
import myproject.mockjang.api.service.feed.response.FeedResponse;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedRepository;
import myproject.mockjang.domain.feed.FeedUsageStatus;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.domain.feedcomsumption.FeedConsumptionRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedConsumptionRepository feedConsumptionRepository;

    public FeedResponse createFeed(FeedCreateServiceRequest request) {
        Feed feed = request.toEntity();
        feed.registerUsageStatus(FeedUsageStatus.USING);
        Feed savedFeed = feedRepository.save(feed);
        return FeedResponse.of(savedFeed);
    }

    public List<Feed> findAll() {
        return feedRepository.findAll();
    }

    public Feed findByCodeId(String codId) {
        return feedRepository.findByCodeId(codId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, codId));
    }

    public void delete(Feed feed) {
        feedRepository.delete(feed);
    }

    public void calculateMultiDailyConsumption(LocalDate eatDate, List<Feed> feeds) {
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

    public void calculateExpectedDepletionDate(List<Feed> feeds, LocalDate date) {
        for (Feed feed : feeds) {
            feed.calculateExpectedDepletionDate(date);
        }
    }
}
