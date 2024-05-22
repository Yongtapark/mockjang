package myproject.mockjang.domain.mockjang.pen;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pen {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String penId;
  @ManyToOne(fetch = FetchType.LAZY)
  private Barn barn;
  @OneToMany(mappedBy = "pen")
  private List<Cow> cows = new ArrayList<>();

  @Builder
  public Pen(String penId, Barn barn, List<Cow> cows) {
    this.penId = penId;
    this.barn = barn;
    if(cows!=null){
      this.cows = cows;
    }
  }

  public void registerBarn(Barn barn) {
    this.barn = barn;
  }
  public void registerCow(Cow cow) {
    cow.registerPen(this);
    cows.add(cow);
  }

  public void registerCow(List<Cow> cows) {
    for (Cow cow : cows) {
      registerCow(cow);
    }
  }
}
