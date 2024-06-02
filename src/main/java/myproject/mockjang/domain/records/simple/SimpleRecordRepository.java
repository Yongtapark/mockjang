package myproject.mockjang.domain.records.simple;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRecordRepository extends JpaRepository<SimpleRecord, Long> {

  List<SimpleRecord> findAllByCodeId(String codeId);
}
