package myproject.mockjang.domain.records.mockjang.pen;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PenRecordRepository extends JpaRepository<PenRecord, Long> {

  List<PenRecord> findAllByPen_CodeId(String codeId);

  @Query(value = "SELECT * FROM pen_record WHERE deleted = true", nativeQuery = true)
  List<PenRecord> findAllWhereDeletedTrue();
}
