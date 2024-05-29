package myproject.mockjang.domain.feedcomsumption;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeedConsumptionRepository extends JpaRepository<FeedConsumption, Long> {

  List<FeedConsumption> findAllByDate(LocalDate date);

  @Query(value = "SELECT * FROM feed_consumption WHERE deleted = true", nativeQuery = true)
  List<FeedConsumption> findAllWhereDeletedTrue();
}
