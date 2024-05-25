package myproject.mockjang.api.service.cow;

import static myproject.mockjang.exception.Exceptions.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.exception.cow.CowStatusException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CowService {

  public void changeCowStatus(Cow cow, CowStatus cowStatus) {
    cow.changeCowStatus(cowStatus);
  }

  public void registerUnitPrice(Cow cow,Integer unitPrice) {
    if(cow.getCowStatus()==null||!cow.getCowStatus().equals(CowStatus.SLAUGHTERED)){
      throw new CowStatusException(BUSINESS_ONLY_SLAUGHTERED_ERROR);
    }
    cow.registerUnitPrice(unitPrice);
  }
}
