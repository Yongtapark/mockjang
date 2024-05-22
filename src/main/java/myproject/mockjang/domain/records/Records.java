package myproject.mockjang.domain.records;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractAuditable;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Records extends AbstractAuditable<Records,Long> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String memo;
  protected void writeMemo(String memo) {
    this.memo=memo;
  }
}
