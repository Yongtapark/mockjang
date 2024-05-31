package myproject.mockjang;

import java.time.LocalDateTime;

public abstract class TestConstants {

  //common
  protected final LocalDateTime TEMP_DATE = LocalDateTime.of(2024, 5, 31, 00, 00);
  protected final String STRING_ONLY_SPACE = " ";
  protected final String STRING_EMPTY = "";
  protected final String STRING_OVER_10 = "12345678910";
  //records
  protected final String MEMO_1 = "메모 1";
  protected final String MEMO_2 = "메모 2";
  //cow
  protected static final int UNIT_PRICE_100_000_000 = 100_000_000;
  //feed
  protected static final double INITIALIZE_DAILY_CONSUMPTION_TO_ZERO = 0.0;
  protected static final double NEGATIVE_NUMBER = -5.0;
  //noteParser
  protected static final String PARSER_BARN_CODE_ID_1 = "1번축사";
  protected static final String PARSER_BARN_NOTE_1 = "1번축사 입력 테스트";
  protected static final String PARSER_BARN_CODE_ID_2 = "2번축사";
  protected static final String PARSER_BARN_NOTE_2 = "2번축사 입력 테스트";
  protected static final String PARSER_PEN_CODE_ID_1 = "1-1";
  protected static final String PARSER_PEN_NOTE_1 = "1-1 축사칸 입력 테스트";
  protected static final String PARSER_PEN_CODE_ID_2 = "1-2";
  protected static final String PARSER_PEN_NOTE_2 = "1-2 축사칸 입력 테스트";
  protected static final String PARSER_COW_CODE_ID_1 = "0001";
  protected static final String PARSER_COW_NOTE_1 = "0001 소 입력 테스트";
  protected static final String PARSER_COW_CODE_ID_2 = "0002";
  protected static final String PARSER_COW_NOTE_2 = "0002 소 입력 테스트";

}
