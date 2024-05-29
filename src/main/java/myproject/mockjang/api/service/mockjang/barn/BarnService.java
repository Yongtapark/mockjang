package myproject.mockjang.api.service.mockjang.barn;

import static myproject.mockjang.exception.Exceptions.COMMON_NOT_EXIST;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import myproject.mockjang.api.service.mockjang.MockjangServiceAbstract;
import myproject.mockjang.api.service.mockjang.MockjangServiceInterface;
import myproject.mockjang.domain.mockjang.barn.Barn;
import myproject.mockjang.domain.mockjang.barn.BarnRepository;
import myproject.mockjang.exception.common.NotExistException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BarnService extends MockjangServiceAbstract implements MockjangServiceInterface<Barn> {

  private final BarnRepository barnRepository;

  public Barn createBarn(String barnId) {
    codeIdFilter(barnId);
    Barn barn = Barn.createBarn(barnId);
    return barnRepository.save(barn);
  }

  @Override
  public List<Barn> findAll() {
    return barnRepository.findAll();
  }

  @Override
  public Barn findByCodeId(String codId) {
      return barnRepository.findByCodeId(codId).orElseThrow(()-> new NotExistException(COMMON_NOT_EXIST, codId));
  }

  @Override
  public void delete(Barn barn) {
    barnRepository.delete(barn);
  }

}
