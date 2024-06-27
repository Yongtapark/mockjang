package myproject.mockjang.docs.mockjang.barn;

import static org.mockito.Mockito.mock;

import myproject.mockjang.api.controller.mockjang.barn.BarnController;
import myproject.mockjang.api.controller.mockjang.cow.CowController;
import myproject.mockjang.api.service.mockjang.barn.BarnService;
import myproject.mockjang.api.service.mockjang.cow.CowService;
import myproject.mockjang.docs.RestDocsSupport;

public class BarnControllerDocsTest extends RestDocsSupport {

    private final BarnService service = mock(BarnService.class);

    @Override
    protected Object initController() {
        return new BarnController(service);
    }

}
