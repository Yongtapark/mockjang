package myproject.mockjang.api.service.cow;

import static myproject.mockjang.domain.Exceptions.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.mockjang.cow.Cow;
import myproject.mockjang.domain.mockjang.cow.CowStatus;
import myproject.mockjang.exception.CowStatusException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CowService {

  public void registerUnitPrice(Cow cow,Integer unitPrice) {
    if(!cow.getCowStatus().equals(CowStatus.SLAUGHTERED)){
      throw new CowStatusException(BUSINESS_ONLY_SLAUGHTERED_ERROR);
    }
    cow.registerUnitPrice(unitPrice);
  }
}
