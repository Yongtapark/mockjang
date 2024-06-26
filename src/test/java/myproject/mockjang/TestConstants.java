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
    protected static final String BARN_CODE_ID_1 = "1번축사";
    protected static final String PARSER_BARN_NOTE_1 = "1번축사 입력 테스트";
    protected static final String PARSER_BARN_CODE_ID_2 = "2번축사";
    protected static final String PARSER_BARN_NOTE_2 = "2번축사 입력 테스트";
    protected static final String PEN_CODE_ID_1 = "1-1";
    protected static final String PARSER_PEN_NOTE_1 = "1-1 축사칸 입력 테스트";
    protected static final String PEN_CODE_ID_2 = "1-2";
    protected static final String PARSER_PEN_NOTE_2 = "1-2 축사칸 입력 테스트";
    protected static final String COW_CODE_ID_1 = "0001";
    protected static final String PARSER_COW_NOTE_1 = "0001 소 입력 테스트";
    protected static final String COW_CODE_ID_2 = "0002";
    protected static final String PARSER_COW_NOTE_2 = "0002 소 입력 테스트";
    protected static final String ANNOTATION_TEMP = "//annotation test";
    protected static final LocalDateTime READ_DATE = LocalDateTime.of(2024, 6, 12, 00, 00);
    protected static final LocalDateTime READ_DATE_SAME = READ_DATE;
    protected static final LocalDateTime READ_DATE_BEFORE = READ_DATE.minusDays(1);
    protected static final LocalDateTime READ_DATE_AFTER = READ_DATE.plusDays(1);
    protected static final LocalDateTime START_DATE_BEFORE_READ = READ_DATE.minusDays(3);
    protected static final LocalDateTime TARGET_DATE_AFTER_READ = READ_DATE.minusDays(2);
    protected static final String SCHEDULE_CONTEXT_1 = "scheduleContext1";
    protected static final String SCHEDULE_CONTEXT_2 = "scheduleContext2";


}
