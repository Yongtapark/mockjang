package myproject.mockjang.domain.schedule;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findAllByStartDateGreaterThanEqual(LocalDateTime startDate);
}
