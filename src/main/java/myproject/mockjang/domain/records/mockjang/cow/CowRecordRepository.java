package myproject.mockjang.domain.records.mockjang.cow;

import jakarta.transaction.Transactional;
import java.util.List;
import myproject.mockjang.domain.records.RecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CowRecordRepository extends JpaRepository<CowRecord, Long> {

    List<CowRecord> findAllByCow_CodeId(String codeId);

    @Query(value = "SELECT * FROM cow_record WHERE deleted = true", nativeQuery = true)
    List<CowRecord> findAllWhereDeletedTrue();

    @Query("SELECT cr FROM CowRecord cr WHERE cr.cow.codeId = :codeId AND cr.recordType = :recordType")
    List<CowRecord> findAllByCow_CodeIdAndRecordType(@Param("codeId") String codeId,
                                                     @Param("recordType") RecordType recordType);


}
