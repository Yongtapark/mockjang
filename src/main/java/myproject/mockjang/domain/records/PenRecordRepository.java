package myproject.mockjang.domain.records;

import java.util.List;
import myproject.mockjang.domain.mockjang.pen.Pen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PenRecordRepository extends JpaRepository<PenRecord,Long> {

    List<PenRecord> findAllByPen_CodeId(String codeId);
}
