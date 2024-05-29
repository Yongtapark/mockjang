package myproject.mockjang.domain.mockjang.cow;

import lombok.Getter;

@Getter
public enum CowStatus {
  RAISING("사육"),
  SLAUGHTERED("도축"),
  DISPOSE("폐기");

  private final String explanation;

  CowStatus(String explanation) {
    this.explanation = explanation;
  }

}
