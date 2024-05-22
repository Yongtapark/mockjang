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
import myproject.mockjang.domain.cow.Cow;
import myproject.mockjang.domain.feed.Feed;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedConsumption extends AbstractAuditable<FeedConsumption, Long> {

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

  private Double amount;

  @Builder
  public FeedConsumption(Cow cow, Feed feed, LocalDate date, Double amount) {
    this.cow = cow;
    this.feed = feed;
    this.date = date;
    this.amount = amount;
  }
}
