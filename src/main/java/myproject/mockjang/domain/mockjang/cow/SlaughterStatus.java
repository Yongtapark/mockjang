package myproject.mockjang.domain.mockjang.cow;

import lombok.Getter;

@Getter
public enum SlaughterStatus {
    RAISING("키우는 중"),
    SLAUGHTERED("도축됨");

    private final String explanation;

    SlaughterStatus(String explanation) {
        this.explanation = explanation;
    }

}
