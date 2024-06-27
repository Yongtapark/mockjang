package myproject.mockjang.docs.mockjang.pen;

import static org.mockito.Mockito.mock;

import myproject.mockjang.api.controller.mockjang.pen.PenController;
import myproject.mockjang.api.service.mockjang.pen.PenService;
import myproject.mockjang.docs.RestDocsSupport;

public class PenControllerDocsTest extends RestDocsSupport {

    private final PenService service = mock(PenService.class);

    @Override
    protected Object initController() {
        return new PenController(service);
    }

}
