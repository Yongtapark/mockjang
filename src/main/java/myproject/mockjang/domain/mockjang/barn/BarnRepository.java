package myproject.mockjang.domain.mockjang.barn;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BarnRepository extends JpaRepository<Barn, Long> {

    Optional<Barn> findByCodeId(String codeId);

    @Query(value = "SELECT * FROM barn WHERE deleted = true", nativeQuery = true)
    List<Barn> findAllWhereDeletedTrue();
}
