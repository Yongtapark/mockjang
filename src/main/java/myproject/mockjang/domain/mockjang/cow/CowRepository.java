package myproject.mockjang.domain.mockjang.cow;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRepository extends JpaRepository<Cow, Long> {

  Cow findByCodeId(String codeId);

  List<Cow> findAllByCowStatus(CowStatus cowStatus);

  @Query(value = "select c from Cow c where c.deleted=false",nativeQuery = true)
  List<Cow> findAllContainsDeleted();

}
