package myproject.mockjang.api.service.mockjang.barn;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BarnService {

  private final BarnRepository barnRepository;

  public Barn createBarn(String barnId) {
    Barn barn = Barn.createBarn(barnId);
    return barnRepository.save(barn);
  }

  public void deleteBarn(Barn barn) {
    barnRepository.delete(barn);
  }

}
