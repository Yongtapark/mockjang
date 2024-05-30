package myproject.mockjang;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.mockjang.api.controller.mockjang.barn.BarnController;
import myproject.mockjang.api.controller.note_parser.NoteParserController;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.api.service.note_parser.NoteParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = {
        NoteParserController.class,
        BarnController.class
    })
public abstract class ControllerTestSupport {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @MockBean
  protected NoteParserService noteParserService;

  @MockBean
  protected BarnService barnService;

  //common
  protected final String STRING_ONLY_SPACE = " ";
  protected final String STRING_EMPTY = "";
  protected final String STRING_OVER_10 = "12345678910";
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
