package myproject.mockjang.domain.records.simple_record;

import java.time.LocalDateTime;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRecordRepository extends JpaRepository<SimpleRecord, Long> {
    List<SimpleRecord> findAllByCodeId(String codeId);
}
