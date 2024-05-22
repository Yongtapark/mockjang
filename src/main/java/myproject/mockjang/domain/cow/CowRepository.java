package myproject.mockjang.domain.cow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cow, Long> {

  Cow findByCowId(String cowId);

}
