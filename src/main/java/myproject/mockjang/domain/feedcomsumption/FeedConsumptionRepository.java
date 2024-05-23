package myproject.mockjang.domain.feedcomsumption;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedConsumptionRepository extends JpaRepository<FeedConsumption,Long> {
  List<FeedConsumption> findAllByDate(LocalDate date);
}
