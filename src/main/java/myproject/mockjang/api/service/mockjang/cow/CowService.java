package myproject.mockjang.api.service.mockjang.cow;

import static myproject.mockjang.exception.Exceptions.BUSINESS_ONLY_SLAUGHTERED_ERROR;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangService;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowRepository;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.domain.mockjang.cow.Gender;
import myproject.mockjang.domain.mockjang.pen.Pen;
import myproject.mockjang.exception.cow.CowStatusException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CowService extends MockjangService {

  private final CowRepository cowRepository;

  public Cow createRaisingCow(String cowId, Gender gender, Pen pen, LocalDateTime birthDate) {
    codeIdFilter(cowId);
    Cow rasingCow = Cow.createCow(cowId, gender, CowStatus.RAISING,birthDate);
    rasingCow.registerUpperGroup(pen);
    rasingCow.registerBarn(pen.getBarn());
    return cowRepository.save(rasingCow);
  }

  public void deleteCow(Cow cow) {
    unlinkUpperGroup(cow);
    cowRepository.delete(cow);
  }

  private void unlinkUpperGroup(Cow cow) {
    Pen pen = cow.getPen();
    pen.deleteCow(cow);
  }

  public void registerUnitPrice(Cow cow,Integer unitPrice) {
    if(cow.getCowStatus()==null||!cow.getCowStatus().equals(CowStatus.SLAUGHTERED)){
      throw new CowStatusException(BUSINESS_ONLY_SLAUGHTERED_ERROR);
    }
    cow.registerUnitPrice(unitPrice);
  }

  public void changeCowStatus(Cow cow, CowStatus cowStatus) {
    cow.changeCowStatus(cowStatus);
  }
}
