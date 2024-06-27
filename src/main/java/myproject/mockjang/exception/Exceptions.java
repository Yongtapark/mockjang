package myproject.mockjang.exception;

import lombok.Getter;

@Getter
public enum Exceptions {
    //common
    COMMON_NO_UPPER_GROUP("상위 그룹이 존재하지 않음. 현재 그룹 : %s"),
    COMMON_NO_UNDER_GROUP("하위 그룹이 존재하지 않음. 현재 그룹 : %s"),
    COMMON_BLANK_STRING("빈 문자열은 입력 불가"),
    COMMON_STRING_OVER_10("문자열은 10자리를 초과할 수 없음"),
    COMMON_NOT_EXIST("%s(을)를 찾을 수 없음"),
    COMMON_EMPTY_LIST("조회 가능한 데이터 없음"),
    COMMON_ALREADY_EXIST("%s(이)가 이미 존재함"),
    //cow,
    DOMAIN_ONLY_SLAUGHTERED_ERROR("cowStatus.slaughtered 이외 설정 시 예외"),
    DOMAIN_PARENTS_ERROR("부모 제거에 실패. expect codeId : %s, actual codeId : %s"),
    //feed
    DOMAIN_NEGATIVE_ERROR("음수 입력 시 예외 발생"),
    //record
    DOMAIN_NO_COW_OR_PEN_OR_BARN("축사 || 축사칸 || 소 미입력"),
    //noteParser
    DOMAIN_NOTE_FORMAT("유효하지 않은 노트 형식 : %s"),
    //schedule
    DOMAIN_SCHEDULE_FORM("시작 날짜보다 목표 날짜가 시간상 뒤에 위치할 수 없습니다."),

    BUSINESS_ONLY_SLAUGHTERED_ERROR("도축된 소만 입력 가능");


    final String message;

    Exceptions(String message) {
        this.message = message;
    }

    public String formatMessage(Class<?> clazz) {
        return String.format(getMessage(), clazz.getSimpleName());
    }

    public String formatMessage(String content) {
        return String.format(getMessage(), content);
    }

    public String formatMessage(String content1, String content2) {
        return String.format(getMessage(), content1, content2);
    }
}
