package myproject.mockjang.domain.feed;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed,Long> {

  Feed findByName(String name);
  List<Feed> findAllByUsageStatus(FeedUsageStatus usageStatus);

}
