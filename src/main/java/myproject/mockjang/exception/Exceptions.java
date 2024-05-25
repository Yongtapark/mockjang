package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public enum Exceptions {
  DOMAIN_NEGATIVE_ERROR("음수 입력 시 예외 발생" ),
  DOMAIN_ONLY_SLAUGHTERED_ERROR("cowStatus.slaughtered 이외 설정 시 예외"),

  BUSINESS_ONLY_SLAUGHTERED_ERROR("도축된 소만 입력이 가능합니다.");

  final String message;

  Exceptions(String message) {
    this.message = message;
  }
}
