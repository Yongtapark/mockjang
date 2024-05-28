package myproject.mockjang.domain.mockjang.pen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PenRepository extends JpaRepository<Pen,Long> {
    Pen findByCodeId(String codeId);

}
