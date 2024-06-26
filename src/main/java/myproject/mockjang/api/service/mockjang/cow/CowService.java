package myproject.mockjang.api.service.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.cow.request.CowCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterParentsServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowRegisterUnitPriceServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowRemoveParentsServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowUpdateCowStatusServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.request.CowUpdatePenServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.domain.mockjang.pen.PenRepository;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.cow.CowStatusException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CowService extends MockjangServiceAbstract {

    private final CowRepository cowRepository;
    private final PenRepository penRepository;

    public Long createRaisingCow(CowCreateServiceRequest request) {
        String cowCode = request.getCowCode();
        codeIdFilter(cowCode);
        String penCode = request.getPenCode();
        Pen pen = penRepository.findByCodeId(penCode)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(
                        Pen.class)));
        Cow rasingCow = Cow.createCow(cowCode, request.getGender(), CowStatus.RAISING,
                request.getBirthDate());
        rasingCow.registerUpperGroup(pen);
        rasingCow.registerBarn(pen.getBarn());
        Cow savedCow = cowRepository.save(rasingCow);
        return savedCow.getId();

    }

    public void updatePen(CowUpdatePenServiceRequest request) {
        Cow cow = findCowById(request.getCowId());
        Pen pen = findPenById(request.getPenId());
        cow.changeUpperGroup(pen);
    }

    public void registerParents(CowRegisterParentsServiceRequest request) {
        Cow cow = findCowById(request.getCowId());
        for (Long parentsId : request.getParentsIds()) {
            cow.registerParent(findCowById(parentsId));
        }
    }

    public void removeParents(CowRemoveParentsServiceRequest request) {
        Cow cow = findCowById(request.getCowId());
        for (Long parentsId : request.getParentsIds()) {
            cow.removeParent(findCowById(parentsId));
        }
    }

    public void registerUnitPrice(CowRegisterUnitPriceServiceRequest request) {
        Cow cow = findCowById(request.getCowId());
        if (cow.getCowStatus() == null || !cow.getCowStatus().equals(CowStatus.SLAUGHTERED)) {
            throw new CowStatusException(BUSINESS_ONLY_SLAUGHTERED_ERROR);
        }
        cow.registerUnitPrice(request.getUnitPrice());
    }

    public void updateCowStatus(CowUpdateCowStatusServiceRequest request) {
        findCowById(request.getCowId()).changeCowStatus(request.getCowStatus());
    }

    public List<CowResponse> findAll() {
        List<Cow> cows = cowRepository.findAll();
        return cows.stream().map(CowResponse::of).toList();
    }

    public CowResponse findByCodeId(String codeId) {
        Cow cow = cowRepository.findByCodeId(codeId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, codeId));
        return CowResponse.of(cow);
    }

    public void remove(Long cowId) {
        Cow cow = findCowById(cowId);
        unlinkUpperGroup(cow);
        cowRepository.delete(cow);
    }

    private Cow findCowById(Long cowId) {
        return cowRepository.findById(cowId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(Cow.class)));
    }

    private Pen findPenById(Long penId) {
        return penRepository.findById(penId)
                .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(Pen.class)));
    }
}
