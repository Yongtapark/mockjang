package myproject.mockjang.domain.mockjang.barn;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarnRepository extends JpaRepository<Barn,Long> {
    Barn findByCodeId(String codeId);
}
