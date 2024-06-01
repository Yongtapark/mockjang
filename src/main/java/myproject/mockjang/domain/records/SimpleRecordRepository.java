package myproject.mockjang.domain.records;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRecordRepository extends JpaRepository<SimpleRecord, Long> {
    List<SimpleRecord> findAllByCodeId(String codeId);

    @Query("select sr from SimpleRecord sr where sr.codeId like %:codeId% and sr.recordType =:recordType and sr.date =:date")
    List<SimpleRecord> search(@Param("codeId") String codeId,
                              @Param("recordType") RecordType recordType,
                              @Param("date") LocalDateTime date);
}
