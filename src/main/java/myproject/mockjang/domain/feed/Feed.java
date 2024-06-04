package myproject.mockjang.domain.feed;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Feed {

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Feed feed = (Feed) o;
    return deleted == feed.deleted && Objects.equals(id, feed.id)
        && Objects.equals(codeId, feed.codeId) && Objects.equals(name, feed.name)
        && Objects.equals(storeDate, feed.storeDate) && Objects.equals(
        expirationDate, feed.expirationDate) && Objects.equals(expectedDepletionDate,
        feed.expectedDepletionDate) && Objects.equals(stock, feed.stock)
        && Objects.equals(amountPerStock, feed.amountPerStock) && Objects.equals(
        amount, feed.amount) && Objects.equals(description, feed.description)
        && Objects.equals(dailyConsumption, feed.dailyConsumption)
        && usageStatus == feed.usageStatus && Objects.equals(feedConsumptions,
        feed.feedConsumptions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, codeId, name, storeDate, expirationDate, expectedDepletionDate, stock,
        amountPerStock, amount, description, dailyConsumption, usageStatus, deleted,
        feedConsumptions);
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


