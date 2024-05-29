package myproject.mockjang.domain.records;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BarnRecordRepository extends JpaRepository<BarnRecord, Long> {

  List<BarnRecord> findAllByBarn_CodeId(String codeId);

  @Query(value = "SELECT * FROM barn_record WHERE deleted = true", nativeQuery = true)
  List<BarnRecord> findAllWhereDeletedTrue();

}
