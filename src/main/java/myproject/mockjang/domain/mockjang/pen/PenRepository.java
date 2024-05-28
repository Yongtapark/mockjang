package myproject.mockjang.domain.mockjang.pen;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PenRepository extends JpaRepository<Pen,Long> {
    Pen findByCodeId(String codeId);

    List<Pen> findAll();

}
