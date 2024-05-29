package myproject.mockjang.domain.feed;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedRepository extends JpaRepository<Feed, Long> {

  Feed findByName(String name);

  List<Feed> findAllByUsageStatus(FeedUsageStatus usageStatus);

  @Query(value = "SELECT * FROM feed WHERE deleted = true", nativeQuery = true)
  List<Feed> findAllWhereDeletedTrue();

}
