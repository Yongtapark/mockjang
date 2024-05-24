package myproject.mockjang.domain.mockjang.cow;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cow, Long> {

  Cow findByCowId(String cowId);
  List<Cow> findAllByCowStatus(CowStatus cowStatus);

}
