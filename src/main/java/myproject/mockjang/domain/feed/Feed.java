package myproject.mockjang.domain.feed;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
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
public class Feed extends AbstractAuditable<Feed,Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDate purchaseDate;

  private LocalDate ExpirationDate;

  private String description;

  private Double dailyConsumption;

  @OneToMany(mappedBy = "feed")
  private List<FeedConsumption> feedConsumptions = new ArrayList<>();

  @Builder
  public Feed(String name, LocalDate purchaseDate, LocalDate expirationDate, String description,
      Double dailyConsumption, List<FeedConsumption> feedConsumptions) {
    this.name = name;
    this.purchaseDate = purchaseDate;
    ExpirationDate = expirationDate;
    this.description = description;
    this.dailyConsumption = dailyConsumption;
    if(feedConsumptions != null) {
      this.feedConsumptions = feedConsumptions;
    }
  }
}
