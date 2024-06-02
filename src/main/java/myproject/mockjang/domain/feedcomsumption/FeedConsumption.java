package myproject.mockjang.domain.feedcomsumption;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.mockjang.cow.Cow;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE feed_consumption SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class FeedConsumption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cow_id")
  private Cow cow;

  @ManyToOne
  @JoinColumn(name = "feed_id")
  private Feed feed;

  private LocalDate date;

  private Double dailyConsumptionAmount;

  private boolean deleted = false;

  @Builder
  private FeedConsumption(Cow cow, Feed feed, LocalDate date, Double dailyConsumptionAmount) {
    this.cow = cow;
    this.feed = feed;
    this.date = date;
    this.dailyConsumptionAmount = dailyConsumptionAmount;
  }

  public void registerDailyCowConsumption() {
    cow.registerFeedConsumptions(this);
  }

}
