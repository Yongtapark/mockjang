package myproject.mockjang.api.service.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;
import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.MockjangServiceInterface;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.exception.common.NotExistException;
import myproject.mockjang.exception.cow.CowStatusException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CowService extends MockjangServiceAbstract implements MockjangServiceInterface<Cow> {

  private final CowRepository cowRepository;

  public Cow createRaisingCow(String cowId, Gender gender, Pen pen, LocalDateTime birthDate) {
    codeIdFilter(cowId);
    Cow rasingCow = Cow.createCow(cowId, gender, CowStatus.RAISING, birthDate);
    rasingCow.registerUpperGroup(pen);
    rasingCow.registerBarn(pen.getBarn());
    return cowRepository.save(rasingCow);
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

  @Override
  public List<Cow> findAll() {
    return cowRepository.findAll();
  }

  @Override
  public Cow findByCodeId(String codeId) {
    return cowRepository.findByCodeId(codeId)
        .orElseThrow(() -> new NotExistException(COMMON_NOT_EXIST, codeId));
  }

  @Override
  public void delete(Cow cow) {
    unlinkUpperGroup(cow);
    cowRepository.delete(cow);
  }
}
