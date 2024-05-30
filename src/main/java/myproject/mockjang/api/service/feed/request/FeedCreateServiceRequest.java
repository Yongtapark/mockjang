package myproject.mockjang.api.service.feed.request;

import jakarta.annotation.Nullable;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myproject.mockjang.domain.feed.Feed;
@Getter
public class FeedCreateServiceRequest {

  private String name;

  private String codeId;

  private LocalDate storeDate;

  private LocalDate expirationDate;

  private Integer stock;

  private Double amountPerStock;

  private String description;

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
