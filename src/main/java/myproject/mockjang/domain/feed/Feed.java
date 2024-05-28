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
import org.springframework.data.jpa.domain.AbstractAuditable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends AbstractAuditable<YongTaPark, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedId;

    private String name;

    private LocalDate storeDate;

    private LocalDate expirationDate;

    private Double amount;

    private LocalDate expectedDepletionDate;

    private String description;
    private Double dailyConsumption;
    private FeedUsageStatus usageStatus;

    @OneToMany(mappedBy = "feed")
    private final List<FeedConsumption> feedConsumptions = new ArrayList<>();

    @Builder
    private Feed(String feedId, String name, LocalDate storeDate, LocalDate expirationDate,
                 Double amount, String description, Double dailyConsumption,
                 List<FeedConsumption> feedConsumptions, LocalDate expectedDepletionDate, FeedUsageStatus usageStatus) {
        this.feedId = feedId;
        this.name = name;
        this.storeDate = storeDate;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.description = description;
        this.dailyConsumption = dailyConsumption != null ? dailyConsumption : 0.0;
        this.expectedDepletionDate = expectedDepletionDate;
        this.usageStatus = usageStatus;
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
        this.expectedDepletionDate=date.plusDays((long) Math.floor(leftDays));
    }
}


