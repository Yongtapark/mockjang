package myproject.mockjang.domain.records;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CowRecordRepository extends JpaRepository<CowRecord, Long> {

  List<CowRecord> findAllByCow_CodeId(String codeId);

  @Query(value = "SELECT * FROM cow_record WHERE deleted = true", nativeQuery = true)
  List<CowRecord> findAllWhereDeletedTrue();

}
