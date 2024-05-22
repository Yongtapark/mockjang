package myproject.mockjang.domain.cow;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("수"),
    FEMALE("암");
    private final String explanation;

    Gender(String explanation) {
        this.explanation = explanation;
    }

}
