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
import myproject.mockjang.exception.NegativeNumberException;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends AbstractAuditable<Feed, Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codeId;

  private String name;

  private LocalDate purchaseDate;

  private LocalDate expirationDate;

  private Double amount;

  private String description;
  private Double dailyConsumption;
  private FeedUsageStatus usageStatus;

  @OneToMany(mappedBy = "feed")
  private List<FeedConsumption> feedConsumptions = new ArrayList<>();

  @Builder
  public Feed(String codeId, String name, LocalDate purchaseDate, LocalDate expirationDate,
      Double amount, String description, Double dailyConsumption,
      List<FeedConsumption> feedConsumptions, FeedUsageStatus usageStatus) {
    this.codeId = codeId;
    this.name = name;
    this.purchaseDate = purchaseDate;
    this.expirationDate = expirationDate;
    this.amount = amount;
    this.description = description;
    this.dailyConsumption = dailyConsumption != null ? dailyConsumption : 0.0;
    this.usageStatus = usageStatus;
  }
  public void addDailyConsumptionAmount(Double dailyConsumptionAmount){
    if(dailyConsumptionAmount<0){
      throw new NegativeNumberException("error.validation.negativeNumber");
    }
    this.dailyConsumption+=dailyConsumptionAmount;
  }

  public void resetDailyConsumption() {
    this.dailyConsumption=0.0;
  }
}


