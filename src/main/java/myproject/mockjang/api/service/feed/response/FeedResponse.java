package myproject.mockjang.api.service.feed.response;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import myproject.mockjang.domain.feed.Feed;
import myproject.mockjang.domain.feed.FeedUsageStatus;
import myproject.mockjang.domain.feedcomsumption.FeedConsumption;

public class FeedResponse {

    private final Long id;

    private final String codeId;

    private final String name;

    private final LocalDate storeDate;

    private final LocalDate expirationDate;

    private final LocalDate expectedDepletionDate;

    private final Integer stock;

    private final Double amountPerStock;

    private final Double amount;

    private final String description;

    private final Double dailyConsumption;

    private final FeedUsageStatus usageStatus;

    private boolean deleted = false;

    private final List<FeedConsumption> feedConsumptions;

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
