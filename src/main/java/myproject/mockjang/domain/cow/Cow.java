package myproject.mockjang.domain.cow;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cow extends AbstractAuditable<Cow, Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String cowId;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  private SlaughterStatus slaughterStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mom_id")
  private Cow mom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dad_id")
  private Cow dad;

  @OneToMany(mappedBy = "mom", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Cow> children = new ArrayList<>();

  @OneToMany(mappedBy = "cow", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<FeedConsumption> feedConsumptions = new ArrayList<>();

  @Builder
  public Cow(String cowId, Gender gender, SlaughterStatus slaughterStatus, List<FeedConsumption> feedConsumptions) {
    this.cowId = cowId;
    this.gender = gender;
    this.slaughterStatus = slaughterStatus;
    if (feedConsumptions != null) {
      this.feedConsumptions = feedConsumptions;
    }
  }

  public void registerAllChildren(List<Cow> children) {
    for (Cow child : children) {
      child.registerParent(this);
    }
  }

  public void registerParent(Cow parent) {
    if (parent.gender.equals(Gender.FEMALE)) {
      this.mom = parent;
    } else {
      this.dad = parent;
    }
    parent.children.add(this);
  }
}
