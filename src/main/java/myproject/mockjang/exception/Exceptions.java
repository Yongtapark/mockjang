package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public enum Exceptions {
  //common
  DOMAIN_BARN_ALREADY_EXIST("축사가 이미 존재함"),
  DOMAIN_PEN_ALREADY_EXIST("축사칸이 이미 존재함"),
  //cow,
  DOMAIN_ONLY_SLAUGHTERED_ERROR("cowStatus.slaughtered 이외 설정 시 예외"),
  //feed
  DOMAIN_NEGATIVE_ERROR("음수 입력 시 예외 발생" ),
  //record
  DOMAIN_NO_COW_OR_PEN_OR_BARN("축사 || 축사칸 || 소 미입력"),


  BUSINESS_ONLY_SLAUGHTERED_ERROR("도축된 소만 입력이 가능합니다.");

  final String message;

  Exceptions(String message) {
    this.message = message;
  }
}
