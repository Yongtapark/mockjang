package myproject.mockjang.api.service.feed;

import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.feed.FeedRepository;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;
  private final CowRepository cowRepository;

}
