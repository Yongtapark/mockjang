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
import myproject.mockjang.domain.creater.YongTaPark;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;
import myproject.mockjang.exception.Exceptions;
import myproject.mockjang.exception.feed.NegativeNumberException;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE feed SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Feed extends AbstractAuditable<YongTaPark, Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String codeId;

  private String name;

  private LocalDate storeDate;

  private LocalDate expirationDate;

  private LocalDate expectedDepletionDate;

  private Integer stock;

  private Double amountPerStock;

  private Double amount;

  private String description;

  private Double dailyConsumption;

  private FeedUsageStatus usageStatus;

  private boolean deleted = false;

  @OneToMany(mappedBy = "feed")
  private final List<FeedConsumption> feedConsumptions = new ArrayList<>();

  @Builder
  private Feed(String codeId, String name, LocalDate storeDate, LocalDate expirationDate,
      Integer stock,
      Double amountPerStock,
      Double amount,
      String description, Double dailyConsumption, List<FeedConsumption> feedConsumptions,
      LocalDate expectedDepletionDate, FeedUsageStatus usageStatus) {
    this.codeId = codeId;
    this.name = name;
    this.storeDate = storeDate;
    this.stock = stock;
    this.amountPerStock = amountPerStock;
    this.amount = amount;
    this.description = description;
    this.dailyConsumption = dailyConsumption != null ? dailyConsumption : 0.0;
    this.expectedDepletionDate = expectedDepletionDate;
    this.usageStatus = usageStatus;
  }

  public static Feed createFeed(String codeId, String name, Integer stock, Double amountPerStock,
      LocalDate storeDate,
      LocalDate expirationDate, String description) {
    return Feed.builder().codeId(codeId).name(name).storeDate(storeDate)
        .expirationDate(expirationDate)
        .stock(stock)
        .amountPerStock(amountPerStock)
        .amount((stock * amountPerStock))
        .description(description).build();

  }

  public void addDailyConsumptionAmount(Double dailyConsumptionAmount) {
    if (dailyConsumptionAmount < 0) {
      throw new NegativeNumberException(Exceptions.DOMAIN_NEGATIVE_ERROR);
    }
    this.dailyConsumption += dailyConsumptionAmount;
  }

  public void resetDailyConsumption() {
    this.dailyConsumption = 0.0;
  }

  public void calculateExpectedDepletionDate(LocalDate date) {
    double leftDays = (this.amount - this.dailyConsumption) / this.dailyConsumption;
    this.expectedDepletionDate = date.plusDays((long) Math.floor(leftDays));
  }

  public void registerUsageStatus(FeedUsageStatus usageStatus) {
    this.usageStatus = usageStatus;
  }

}


