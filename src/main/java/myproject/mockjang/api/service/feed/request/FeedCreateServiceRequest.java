package myproject.mockjang.api.service.feed.request;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import myproject.mockjang.domain.feed.Feed;

@Getter
public class FeedCreateServiceRequest {

    private final String name;

    private final String codeId;

    private final LocalDate storeDate;

    private final LocalDate expirationDate;

    private final Integer stock;

    private final Double amountPerStock;

    private final String description;

    @Builder
    private FeedCreateServiceRequest(String name, String codeId, LocalDate storeDate,
                                     LocalDate expirationDate,
                                     Integer stock, Double amountPerStock, @Nullable String description) {
        this.name = name;
        this.codeId = codeId;
        this.storeDate = storeDate;
        this.expirationDate = expirationDate;
        this.stock = stock;
        this.amountPerStock = amountPerStock;
        this.description = description;
    }

    public Feed toEntity() {
        return Feed.createFeed(codeId, name, stock, amountPerStock, storeDate, expirationDate,
                description);
    }

}
