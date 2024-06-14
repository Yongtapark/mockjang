package myproject.mockjang;

import com.fasterxml.jackson.databind.ObjectMapper;
import myproject.mockjang.api.controller.mockjang.barn.BarnController;
import myproject.mockjang.api.controller.mockjang.cow.CowController;
import myproject.mockjang.api.controller.mockjang.pen.PenController;
import myproject.mockjang.api.controller.note_parser.mockjang.NoteParserController;
import myproject.mockjang.api.controller.note_parser.simple.SimpleNoteParserController;
import myproject.mockjang.api.controller.records.mockjang.cow.CowRecordController;
import myproject.mockjang.api.controller.records.simple.SimpleRecordController;
import myproject.mockjang.api.controller.schedule.ScheduleController;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.api.service.mockjang.cow.CowService;
import myproject.mockjang.api.service.mockjang.pen.PenService;
import myproject.mockjang.api.service.note_parser.mockjang.NoteParserService;
import myproject.mockjang.api.service.note_parser.simple.SimpleNoteParserService;
import myproject.mockjang.api.service.records.mockjang.cow.CowRecordService;
import myproject.mockjang.api.service.records.simple.SimpleRecordService;
import myproject.mockjang.api.service.schedule.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.data.jpa.util.JpaMetamodel;
import org.springframework.test.web.servlet.MockMvc;



@WebMvcTest(controllers = {
    NoteParserController.class,
    BarnController.class,
    PenController.class,
    CowController.class,
    CowRecordController.class,
    SimpleRecordController.class,
    SimpleNoteParserController.class,
    ScheduleController.class
})
@MockBean(JpaMetamodelMappingContext.class)
public abstract class ControllerTestSupport extends TestConstants {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @MockBean
  protected NoteParserService noteParserService;

  @MockBean
  protected BarnService barnService;

  @MockBean
  protected PenService penService;

  @MockBean
  protected CowService cowService;

  @MockBean
  protected CowRecordService cowRecordService;

  @MockBean
  protected SimpleRecordService simpleRecordService;

  @MockBean
  protected SimpleNoteParserService simpleNoteParserService;

  @MockBean
  protected ScheduleService scheduleService;
}
