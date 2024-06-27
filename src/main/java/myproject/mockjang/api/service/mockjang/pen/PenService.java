package myproject.mockjang.api.service.mockjang.pen;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.pen.request.PenCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.pen.request.PenUpdateServiceRequest;
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

    public Long createPen(PenCreateServiceRequest request) {
        String penCodeId = request.getPenCodeId();
        String barnCodeId = request.getBarnCodeId();
        codeIdFilter(penCodeId);

        Pen pen = Pen.createPen(penCodeId);
        Barn barn = barnRepository.findByCodeId(barnCodeId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(Barn.class)));
        pen.registerUpperGroup(barn);
        return penRepository.save(pen).getId();
    }

    public List<PenResponse> findAll() {
        List<Pen> pens = penRepository.findAll();
        return pens.stream().map(PenResponse::of).toList();
    }

    public PenResponse findByCodeId(String codeId) {
        return PenResponse.of(penRepository.findByCodeId(codeId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, codeId)));
    }

    public void update(PenUpdateServiceRequest request){
        Pen pen = findPenById(request.getPenId());
        if(request.getCodeId()!=null){
            pen.updateCodeId(request.getCodeId());
        }
        if(request.getBarnId()!=null){
            Barn barn = findBarnById(request.getBarnId());
            pen.changeUpperGroup(barn);
        }
    }

    public void delete(Long penId) {
        Pen pen = findPenById(penId);
        unlinkUpperGroup(pen);
        penRepository.delete(pen);
    }

    private Pen findPenById(Long id){
        return penRepository.findById(id).orElseThrow(()->new NotExistException(COMMON_NOT_EXIST.formatMessage(Pen.class)));
    }

    private Barn findBarnById(Long id){
        return barnRepository.findById(id).orElseThrow(()->new NotExistException(COMMON_NOT_EXIST.formatMessage(Pen.class)));
    }

}
