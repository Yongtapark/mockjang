package myproject.mockjang.api.service.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.cow.request.CowCreateServiceRequest;
import myproject.mockjang.api.service.mockjang.cow.response.CowResponse;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
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

  public CowResponse createRaisingCow(CowCreateServiceRequest request) {
    String cowId = request.getCowId();
    codeIdFilter(cowId);
    String penId = request.getPenId();
    Pen pen = penRepository.findByCodeId(penId)
        .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST.formatMessage(
            Pen.class)));
    Cow rasingCow = Cow.createCow(cowId, request.getGender(), CowStatus.RAISING,
        request.getBirthDate());
    rasingCow.registerUpperGroup(pen);
    rasingCow.registerBarn(pen.getBarn());
    Cow savedCow = cowRepository.save(rasingCow);
    return CowResponse.of(savedCow);

  }

  public void changeUpperGroup(Cow cow, Pen pen) {
    cow.changeUpperGroup(pen);
  }

  public void registerParents(Cow cow, List<Cow> parents) {
    for (Cow parent : parents) {
      cow.registerParent(parent);
    }
  }

  public void registerUnitPrice(Cow cow, Integer unitPrice) {
    if (cow.getCowStatus() == null || !cow.getCowStatus().equals(CowStatus.SLAUGHTERED)) {
      throw new CowStatusException(BUSINESS_ONLY_SLAUGHTERED_ERROR);
    }
    cow.registerUnitPrice(unitPrice);
  }

  public void changeCowStatus(Cow cow, CowStatus cowStatus) {
    cow.changeCowStatus(cowStatus);
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

  public void delete(Cow cow) {
    unlinkUpperGroup(cow);
    cowRepository.delete(cow);
  }
}
