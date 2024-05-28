package myproject.mockjang.domain.mockjang.cow;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cow, Long> {

  Cow findByCodeId(String codeId);

  List<Cow> findAllByCowStatus(CowStatus cowStatus);

  @Query(value = "SELECT * FROM cow WHERE deleted = true", nativeQuery = true)
  List<Cow> findAllWhereDeletedTrue();

}
