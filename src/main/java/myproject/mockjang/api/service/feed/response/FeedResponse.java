package myproject.mockjang.api.service.feed.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedUsageStatus;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;

public class FeedResponse {

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

  private List<FeedConsumption> feedConsumptions;

  @Builder
  private FeedResponse(Long id, String codeId, String name, LocalDate storeDate,
      LocalDate expirationDate, LocalDate expectedDepletionDate, Integer stock,
      Double amountPerStock,
      Double amount, String description, Double dailyConsumption, FeedUsageStatus usageStatus,
      boolean deleted, List<FeedConsumption> feedConsumptions) {
    this.id = id;
    this.codeId = codeId;
    this.name = name;
    this.storeDate = storeDate;
    this.expirationDate = expirationDate;
    this.expectedDepletionDate = expectedDepletionDate;
    this.stock = stock;
    this.amountPerStock = amountPerStock;
    this.amount = amount;
    this.description = description;
    this.dailyConsumption = dailyConsumption;
    this.usageStatus = usageStatus;
    this.deleted = deleted;
    this.feedConsumptions = feedConsumptions;
  }

  public static FeedResponse of(Feed feed) {
    return FeedResponse.builder()
        .id(feed.getId())
        .codeId(feed.getCodeId())
        .name(feed.getName())
        .storeDate(feed.getStoreDate())
        .expirationDate(feed.getExpirationDate())
        .expectedDepletionDate(feed.getExpectedDepletionDate())
        .stock(feed.getStock())
        .amountPerStock(feed.getAmountPerStock())
        .amount(feed.getAmount())
        .description(feed.getDescription())
        .dailyConsumption(feed.getDailyConsumption())
        .usageStatus(feed.getUsageStatus())
        .feedConsumptions(feed.getFeedConsumptions())
        .build();
  }
}
