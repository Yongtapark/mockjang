package myproject.mockjang.api.service.mockjang.pen;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.pen.request.PenCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.pen.response.PenResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PenService extends MockjangServiceAbstract {

    private final PenRepository penRepository;
    private final BarnRepository barnRepository;

    public PenResponse createPen(PenCreateServiceRequest request) {
        String penCodeId = request.getPenCodeId();
        String barnCodeId = request.getBarnCodeId();
        codeIdFilter(penCodeId);

        Pen pen = Pen.createPen(penCodeId);
        Barn barn = barnRepository.findByCodeId(barnCodeId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(Barn.class)));
        pen.registerUpperGroup(barn);
        return PenResponse.of(penRepository.save(pen));
    }

    public void changeUpperGroup(Pen pen, Barn barn) {
        pen.changeUpperGroup(barn);
    }

    public List<PenResponse> findAll() {
        List<Pen> pens = penRepository.findAll();
        return pens.stream().map(PenResponse::of).toList();
    }

    public PenResponse findByCodeId(String codeId) {
        return PenResponse.of(penRepository.findByCodeId(codeId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, codeId)));
    }

    public void delete(Pen pen) {
        unlinkUpperGroup(pen);
        penRepository.delete(pen);
    }

}
