package myproject.mockjang.domain.mockjang.pen;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PenRepository extends JpaRepository<Pen, Long> {

    Optional<Pen> findByCodeId(String codeId);

    List<Pen> findAll();

    @Query(value = "SELECT * FROM pen WHERE deleted = true", nativeQuery = true)
    List<Pen> findAllWhereDeletedTrue();

}
