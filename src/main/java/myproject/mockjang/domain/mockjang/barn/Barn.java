package myproject.mockjang.domain.mockjang.barn;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.pen.Pen;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Barn {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String BarnId;

  @OneToMany(mappedBy = "barn")
  private List<Pen> pens = new ArrayList<>();

  @Builder
  public Barn(String barnId) {
    BarnId = barnId;
  }

  public void registerPen(Pen pen) {
    pen.registerBarn(this);
    pens.add(pen);
  }

  public void registerPen(List<Pen> pens) {
    for (Pen pen : pens) {
      registerPen(pen);
    }
  }
}
