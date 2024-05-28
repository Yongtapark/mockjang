package myproject.mockjang.domain.records;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarnRecordRepository extends JpaRepository<BarnRecord,Long> {
    List<BarnRecord> findAllByBarn_CodeId(String codeId);

}
