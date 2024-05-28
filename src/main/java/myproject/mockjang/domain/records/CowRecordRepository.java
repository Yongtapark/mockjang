package myproject.mockjang.domain.records;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CowRecordRepository extends JpaRepository<CowRecord,Long> {
    List<CowRecord> findAllByCow_CodeId(String codeId);

}
